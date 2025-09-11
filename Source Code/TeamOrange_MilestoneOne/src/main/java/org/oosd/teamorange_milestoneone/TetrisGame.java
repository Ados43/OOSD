package org.oosd.teamorange_milestoneone;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class TetrisGame {

    // Tetromino shapes (all 4x4, row-major, length 16)
    private static final char[][] TETROMINOS = {
            { // I
                    ' ', ' ', ' ', ' ',
                    'I', 'I', 'I', 'I',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '
            },
            { // O
                    ' ', ' ', ' ', ' ',
                    ' ', 'O', 'O', ' ',
                    ' ', 'O', 'O', ' ',
                    ' ', ' ', ' ', ' '
            },
            { // J
                    'J', ' ', ' ', ' ',
                    'J', 'J', 'J', ' ',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '
            },
            { // L
                    ' ', ' ', 'L', ' ',
                    'L', 'L', 'L', ' ',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '
            },
            { // S
                    ' ', 'S', 'S', ' ',
                    'S', 'S', ' ', ' ',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '
            },
            { // T
                    ' ', 'T', ' ', ' ',
                    'T', 'T', 'T', ' ',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '
            },
            { // Z
                    'Z', 'Z', ' ', ' ',
                    ' ', 'Z', 'Z', ' ',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '
            }
    };

    private HighScoreStore highScoreStore;

    public void setHighScoreStore(HighScoreStore store) {
        this.highScoreStore = store;
    }

    private final ConcurrentHashMap<KeyCode, Boolean> keys = new ConcurrentHashMap<>();
    private static final int WIDTH = 12;
    private static final int HEIGHT = 18;
    private static final int BLOCK_SIZE = 30;
    // === Responsive sizing ===
    private double blockSize = BLOCK_SIZE;         // current pixel size of one board cell
    private HBox rootRow;                          // container that holds the board and side panel
    private StackPane rootWrapper;                 // top-level container for this screen

    private int cell() {
        return (int) Math.max(1, Math.floor(blockSize));
    }

    private double boardPixelW() {
        return WIDTH * cell();
    }

    private double boardPixelH() {
        return HEIGHT * cell();
    }

    private final Random rand = new Random();
    private final KeyInputHandler input = new KeyInputHandler();

    private Canvas gameCanvas, nextCanvas;
    private GraphicsContext gc, nextGc;

    private char[][] board;
    private int currentPiece, rotation, x, y;
    private int nextPiece;

    private int score = 0;
    private boolean gameOver = false;
    private boolean paused = false;
    private boolean highScoreSubmitted = false;

    private Runnable onBack;

    private VBox highScorePane;
    private TextField nameField;
    private final int baseFallInterval = 30;
    private final int minFallInterval = 5;
    private int fallCounter = 0;
    private long startTime = -1;

    public void setOnBack(Runnable onBack) {
        this.onBack = onBack;
    }

    // Builds the UI, initializes board and starts game loop
    public Parent createContent() {
        gameCanvas = new Canvas(boardPixelW(), boardPixelH());
        gc = gameCanvas.getGraphicsContext2D();
        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(input);
        gameCanvas.setOnKeyReleased(input);

        nextCanvas = new Canvas(6 * BLOCK_SIZE, 6 * BLOCK_SIZE);
        nextGc = nextCanvas.getGraphicsContext2D();

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                    "Your current progress will be lost if you return to the main menu. Are you sure?",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> res = a.showAndWait();
            if (res.isPresent() && res.get() == ButtonType.YES) {
                if (onBack != null) onBack.run();
            }
        });
        backButton.setFocusTraversable(false);

        rootRow = new HBox(20, gameCanvas, nextCanvas);
        rootRow.setAlignment(Pos.CENTER);

        // UI components for game board and next piece preview
        rootWrapper = new StackPane(rootRow, backButton);
        StackPane.setAlignment(backButton, Pos.TOP_LEFT);
        backButton.setTranslateX(10);
        backButton.setTranslateY(10);
        // Pref size will be computed responsively; see resizeToAvailable(...)

        rootWrapper.setAlignment(Pos.CENTER);

        board = new char[WIDTH][HEIGHT];
        for (int bx = 0; bx < WIDTH; bx++)
            for (int by = 0; by < HEIGHT; by++)
                board[bx][by] = ' ';

        nameField = new TextField();
        nameField.setPromptText("Enter name");
        nameField.setMaxWidth(120);
        Button submitHS = new Button("Submit");
        submitHS.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                if (highScoreStore != null) {
                    highScoreStore.add(name, score);
                    // show Top 10 quickly, then hide the panel
                    String msg = highScoreStore.formatTop10();
                    new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION, msg).showAndWait();
                }
                highScorePane.setVisible(false);
                highScoreSubmitted = true;
            }
        });

        highScorePane = new VBox(10, nameField, submitHS);
        highScorePane.setAlignment(Pos.CENTER);
        highScorePane.setMaxWidth(200);
        highScorePane.setVisible(false);
        rootWrapper.getChildren().add(highScorePane);

        spawnNewGamePieces();

        // Focus & responsive sizing hooks once scene is attached
        rootWrapper.sceneProperty().addListener((obs, oldS, newS) -> {
            if (newS != null) {
                gameCanvas.requestFocus();
                // Bind resize events to recompute cell size and canvas bounds
                resizeToAvailable(newS.getWidth(), newS.getHeight());
                newS.widthProperty().addListener((o, ov, nv) -> resizeToAvailable(newS.getWidth(), newS.getHeight()));
                newS.heightProperty().addListener((o, ov, nv) -> resizeToAvailable(newS.getWidth(), newS.getHeight()));
            }
        });

        startGameLoop();
        return rootWrapper;
    }

    // Spawns a new piece and selects next piece
    // Compute block size and canvas sizes from current scene size
    private void resizeToAvailable(double sceneW, double sceneH) {
        double spacing = 20; // HBox spacing between board and side panel
        double sideW = Math.max(120, Math.min(sceneW * 0.28, 260)); // side panel width (next piece/labels)
        double availW = Math.max(100, sceneW - sideW - spacing - 20); // allow a small margin
        double availH = Math.max(100, sceneH - 20);

        int newCell = (int) Math.max(10, Math.floor(Math.min(availW / WIDTH, availH / HEIGHT)));
        if (newCell <= 0) newCell = BLOCK_SIZE; // fallback
        blockSize = newCell;

        // Resize canvases
        gameCanvas.setWidth(boardPixelW());
        gameCanvas.setHeight(boardPixelH());
        nextCanvas.setWidth(sideW);
        nextCanvas.setHeight(sideW);

        if (rootRow != null) {
            rootRow.setSpacing(spacing);
            rootRow.setAlignment(Pos.CENTER);
        }

        // Redraw static elements immediately so the board doesn't look stretched during resize
        renderBoard(gc);
        drawPiece(gc, currentPiece, rotation, x, y);
        renderNextPiece();
        renderScore(gc);
    }

    private void spawnNewGamePieces() {
        currentPiece = rand.nextInt(TETROMINOS.length);
        rotation = 0;
        x = WIDTH / 2 - 2;
        y = 0;
        nextPiece = rand.nextInt(TETROMINOS.length);
    }

    private void startGameLoop() {
        // Game loop and board update
        new AnimationTimer() {
            private long lastFrame = 0;

            @Override
            public void handle(long now) {
                if (startTime < 0) startTime = now;
                if (now - lastFrame < 50_000_000) return;
                lastFrame = now;

                if (input.consumeRelease(KeyCode.ESCAPE)) paused = !paused;

                double elapsedSec = (now - startTime) / 1_000_000_000.0;
                int decrease = (int) (elapsedSec / 3.0);
                int currentFallInterval = Math.max(minFallInterval, baseFallInterval - decrease);

                if (paused && !gameOver) {
                    renderBoard(gc);
                    drawPiece(gc, currentPiece, rotation, x, y);
                    renderNextPiece();
                    renderScore(gc);
                    drawPause(gc);
                    return;
                }

                if (!paused && !gameOver) {
                    // Movement & rotation
                    if (input.consumePress(KeyCode.LEFT) && canMove(currentPiece, rotation, x - 1, y)) x--;
                    if (input.consumePress(KeyCode.RIGHT) && canMove(currentPiece, rotation, x + 1, y)) x++;
                    if (input.consumePress(KeyCode.DOWN)) {
                        if (canMove(currentPiece, rotation, x, y + 1)) y++;
                        score += 1;
                    }
                    if (input.consumePress(KeyCode.UP)) {
                        int newRot = rotation + 1;
                        if (canMove(currentPiece, newRot, x, y)) rotation = newRot;
                    }
                    if (input.consumePress(KeyCode.SPACE)) {
                        while (canMove(currentPiece, rotation, x, y + 1)) {
                            y++;
                            score += 2;
                        }
                        lockAndSpawn();
                    }

                    fallCounter++;
                    if (fallCounter >= currentFallInterval) {
                        fallCounter = 0;
                        if (canMove(currentPiece, rotation, x, y + 1)) y++;
                        else lockAndSpawn();
                    }
                }

                // Draw
                renderBoard(gc);
                drawPiece(gc, currentPiece, rotation, x, y);
                renderNextPiece();
                renderScore(gc);
                drawSpeed(gc, currentFallInterval);

                // Restart handling & game over
                if (gameOver) {
                    drawGameOver(gc);
                    if (!highScoreSubmitted) highScorePane.setVisible(true);
                    if (input.consumePress(KeyCode.R) || input.consumePress(KeyCode.ENTER)) resetGame();
                }
            }
        }.start();
    }

    private boolean canMove(int tetromino, int rot, int posX, int posY) {
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                char cell = TETROMINOS[tetromino][rotate(px, py, rot)];
                if (cell == ' ') continue;
                int nx = posX + px;
                int ny = posY + py;
                if (nx < 0 || nx >= WIDTH || ny < 0 || ny >= HEIGHT) return false;
                if (board[nx][ny] != ' ') return false;
            }
        return true;
    }

    private void lockAndSpawn() {
        placePiece(currentPiece, rotation, x, y);
        clearLines();
        currentPiece = nextPiece;
        rotation = 0;
        x = WIDTH / 2 - 2;
        y = 0;
        nextPiece = rand.nextInt(TETROMINOS.length);
        if (!canMove(currentPiece, rotation, x, y)) gameOver = true;
    }

    private void placePiece(int tetromino, int rot, int posX, int posY) {
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                char cell = TETROMINOS[tetromino][rotate(px, py, rot)];
                if (cell == ' ') continue;
                int nx = posX + px;
                int ny = posY + py;
                if (nx >= 0 && nx < WIDTH && ny >= 0 && ny < HEIGHT)
                    board[nx][ny] = cell;
            }
    }

    private void clearLines() {
        int linesCleared = 0;
        for (int by = 0; by < HEIGHT; by++) {
            boolean full = true;
            for (int bx = 0; bx < WIDTH; bx++) {
                if (board[bx][by] == ' ') {
                    full = false;
                    break;
                }
            }
            if (full) {
                linesCleared++;
                for (int ty = by; ty > 0; ty--)
                    for (int bx = 0; bx < WIDTH; bx++)
                        board[bx][ty] = board[bx][ty - 1];
                for (int bx = 0; bx < WIDTH; bx++) board[bx][0] = ' ';
            }
        }
        if (linesCleared == 1) score += 100;
        else if (linesCleared == 2) score += 300;
        else if (linesCleared == 3) score += 600;
        else if (linesCleared >= 4) score += 1000;
    }

    private int rotate(int px, int py, int r) {
        return switch (r & 3) {
            case 0 -> py * 4 + px;
            case 1 -> 12 + py - (px * 4);
            case 2 -> 15 - (py * 4) - px;
            default -> 3 - py + (px * 4);
        };
    }

    private void renderBoard(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, boardPixelW(), boardPixelH());
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(0.5);
        for (int bx = 0; bx <= WIDTH; bx++)
            gc.strokeLine(bx * cell(), 0, bx * cell(), boardPixelH());
        for (int by = 0; by <= HEIGHT; by++)
            gc.strokeLine(0, by * cell(), boardPixelW(), by * cell());
        for (int bx = 0; bx < WIDTH; bx++)
            for (int by = 0; by < HEIGHT; by++)
                if (board[bx][by] != ' ')
                    drawBlock(gc, board[bx][by], bx * cell(), by * cell());
    }

    private void drawPiece(GraphicsContext gc, int tetromino, int rot, int posX, int posY) {
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                char cell = TETROMINOS[tetromino][rotate(px, py, rot)];
                if (cell != ' ') drawBlock(gc, cell, (posX + px) * cell(), (posY + py) * cell());
            }
    }

    private void renderNextPiece() {
        nextGc.setFill(Color.DARKGRAY);
        nextGc.fillRect(0, 0, nextCanvas.getWidth(), nextCanvas.getHeight());
        nextGc.setFill(Color.WHITE);
        nextGc.setFont(Font.font(18));
        nextGc.fillText("Next:", 10, 20);

        int minX = 4, maxX = -1, minY = 4, maxY = -1;
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                if (TETROMINOS[nextPiece][rotate(px, py, 0)] != ' ') {
                    if (px < minX) minX = px;
                    if (px > maxX) maxX = px;
                    if (py < minY) minY = py;
                    if (py > maxY) maxY = py;
                }
            }
        int pieceW = (maxX - minX + 1);
        int pieceH = (maxY - minY + 1);
        if (pieceW <= 0) pieceW = 1;
        if (pieceH <= 0) pieceH = 1;

        double scale = 0.85 * Math.min(
                nextCanvas.getWidth() / (pieceW * BLOCK_SIZE * 1.2),
                nextCanvas.getHeight() / (pieceH * BLOCK_SIZE * 1.2)
        );
        double offsetX = (nextCanvas.getWidth() - pieceW * BLOCK_SIZE * scale) / 2;
        double offsetY = (nextCanvas.getHeight() - pieceH * BLOCK_SIZE * scale) / 2 + 10;

        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                char cell = TETROMINOS[nextPiece][rotate(px, py, 0)];
                if (cell != ' ') {
                    double drawX = offsetX + (px - minX) * BLOCK_SIZE * scale;
                    double drawY = offsetY + (py - minY) * BLOCK_SIZE * scale;
                    drawBlock(nextGc, cell, (int) drawX, (int) drawY, (int) (BLOCK_SIZE * scale));
                }
            }
    }

    private void renderScore(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(20));
        gc.fillText("Score: " + score, 10, 25);
    }

    private void drawSpeed(GraphicsContext gc, int fallInterval) {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(16));
        double speedValue = Math.round((double) baseFallInterval / fallInterval * 10) / 10.0;
        gc.fillText("Speed: " + speedValue, 10, 50);
    }

    private void drawBlock(GraphicsContext gc, char kind, int x, int y) {
        drawBlock(gc, kind, x, y, cell());
    }

    private void drawGameOver(GraphicsContext gc) {
        gc.setFill(Color.color(0, 0, 0, 0.6));
        gc.fillRect(0, 0, boardPixelW(), boardPixelH());
        gc.setFill(Color.RED);
        gc.setFont(Font.font(40));
        gc.fillText("GAME OVER", 40, (boardPixelH()) / 2.0);
        gc.setFont(Font.font(25));
        gc.setFill(Color.WHITE);
        gc.fillText("Final Score: " + score, 60, (boardPixelH()) / 2.0 + 40);
        gc.setFont(Font.font(18));
        gc.fillText("Press R or Enter to restart", 60, (boardPixelH()) / 2.0 + 80);
    }

    private void drawPause(GraphicsContext gc) {
        gc.setFill(Color.color(0, 0, 0, 0.5));
        gc.fillRect(0, 0, boardPixelW(), boardPixelH());
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font(40));
        String pauseText = "PAUSED";
        Text textNode = new Text(pauseText);
        textNode.setFont(gc.getFont());
        double textWidth = textNode.getLayoutBounds().getWidth();
        double textHeight = textNode.getLayoutBounds().getHeight();
        double textX = (boardPixelW() - textWidth) / 2;
        double textY = (boardPixelH() + textHeight) / 2;
        gc.fillText(pauseText, textX, textY);
        gc.setFill(Color.LIGHTGRAY);
        gc.setFont(Font.font(20));
        gc.fillText("Score: " + score, 10, 25);
        double speedValue = Math.round((double) baseFallInterval / Math.max(minFallInterval, baseFallInterval) * 10) / 10.0;
        gc.setFont(Font.font(16));
        gc.fillText("Speed: " + speedValue, 10, 50);
    }

    private void resetGame() {
        for (int bx = 0; bx < WIDTH; bx++)
            for (int by = 0; by < HEIGHT; by++)
                board[bx][by] = ' ';
        score = 0;
        gameOver = false;
        paused = false;
        highScoreSubmitted = false;
        highScorePane.setVisible(false);
        nameField.setText("");
        spawnNewGamePieces();
        gameCanvas.requestFocus();
    }

    private void drawBlock(GraphicsContext gc, char kind, int x, int y, int size) {
        Color color = switch (kind) {
            case 'I' -> Color.CYAN;
            case 'O' -> Color.YELLOW;
            case 'T' -> Color.MEDIUMPURPLE;
            case 'S' -> Color.LIMEGREEN;
            case 'Z' -> Color.RED;
            case 'J' -> Color.DODGERBLUE;
            case 'L' -> Color.ORANGE;
            default -> Color.GRAY;
        };
        gc.setFill(color);
        gc.fillRect(x, y, size, size);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, y, size, size);
    }

    // Simple input helper with press/consume semantics
    private class KeyInputHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent e) {
            if (e.getEventType() == KeyEvent.KEY_PRESSED) keys.put(e.getCode(), true);
            else if (e.getEventType() == KeyEvent.KEY_RELEASED) keys.put(e.getCode(), false);
        }

        boolean consumePress(KeyCode code) {
            if (Boolean.TRUE.equals(keys.get(code))) {
                keys.put(code, false);
                return true;
            }
            return false;
        }

        boolean consumeRelease(KeyCode code) {
            if (Boolean.FALSE.equals(keys.get(code))) {
                keys.remove(code);
                return true;
            }
            return false;
        }
    }
}
