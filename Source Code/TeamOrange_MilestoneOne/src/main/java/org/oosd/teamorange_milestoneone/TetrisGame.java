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

// Main Tetris game class handling gameplay, rendering, and input
public class TetrisGame {
    // Definition of all tetromino shapes
    private static final char[][] TETROMINOS = {
            {' ', ' ', ' ', ' ',
                    'I', 'I', 'I', 'I',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '},
            {' ', 'O', 'O', ' ',
                    ' ', 'O', 'O', ' ',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '},
            {'J', ' ', ' ', ' ',
                    'J', 'J', 'J', ' ',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '},
            {' ', ' ', 'L', ' ',
                    'L', 'L', 'L', ' ',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '},
            {' ', 'S', 'S', ' ',
                    'S', 'S', ' ', ' ',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '},
            {' ', 'T', ' ', ' ',
                    'T', 'T', 'T', ' ',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '},
            {'Z', 'Z', ' ', ' ',
                    ' ', 'Z', 'Z', ' ',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '}
    };

    // Handles keyboard input using ConcurrentHashMap
    static class KeyInputHandler implements EventHandler<KeyEvent> {
        private final ConcurrentHashMap<KeyCode, Boolean> keys = new ConcurrentHashMap<>();

        @Override
        public void handle(KeyEvent e) {
            if (e.getEventType() == KeyEvent.KEY_PRESSED) keys.put(e.getCode(), true);
            else if (e.getEventType() == KeyEvent.KEY_RELEASED) keys.put(e.getCode(), false);
        }

        boolean isPressed(KeyCode code) {
            return Boolean.TRUE.equals(keys.get(code));
        }

        boolean consumeRelease(KeyCode code) {
            Boolean state = keys.get(code);
            if (state != null && !state) {
                keys.remove(code);
                return true;
            }
            return false;
        }
    }

    // Board size and block size constants (easy to implement from configuration page)
    private static final int WIDTH = 12;
    private static final int HEIGHT = 18;
    private static final int BLOCK_SIZE = 30;

    // Random generator and input handler
    private final Random rand = new Random();
    private final KeyInputHandler input = new KeyInputHandler();

    private Canvas gameCanvas, nextCanvas;
    private GraphicsContext gc, nextGc;

    private char[][] board;

    // Current piece state
    private int currentPiece, rotation, x, y;
    private int nextPiece;
    private int score = 0;
    private boolean gameOver = false;
    private boolean paused = false;
    private boolean highScoreSubmitted = false;

    // Back navigation callback
    private Runnable onBack;

    // High score UI
    private VBox highScorePane;
    private TextField nameField;

    // Game speed controls (Easy to implement from configuration)
    private final int baseFallInterval = 30;
    private final int minFallInterval = 5;

    // Sets callback for returning to main menu
    public void setOnBack(Runnable onBack) {
        this.onBack = onBack;
    }

    // Builds the UI, initializes board and starts game loop
    public Parent createContent() {
        gameCanvas = new Canvas(WIDTH * BLOCK_SIZE, HEIGHT * BLOCK_SIZE);
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
        HBox root = new HBox(20, gameCanvas, nextCanvas);
        root.setAlignment(Pos.CENTER);
        // UI components for game board and next piece preview
        StackPane rootWrapper = new StackPane(root, backButton);
        StackPane.setAlignment(backButton, Pos.TOP_LEFT);
        backButton.setTranslateX(10);
        backButton.setTranslateY(10);
        rootWrapper.setPrefSize(WIDTH * BLOCK_SIZE + (int) nextCanvas.getWidth() + 60,
                HEIGHT * BLOCK_SIZE + 40);
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
                System.out.println("Highscore submit: " + name + " Score: " + score);
                highScorePane.setVisible(false);
                highScoreSubmitted = true; // <-- mark as submitted
            }
        });
        highScorePane = new VBox(10, nameField, submitHS);
        highScorePane.setAlignment(Pos.CENTER);
        highScorePane.setMaxWidth(200);
        highScorePane.setVisible(false);
        rootWrapper.getChildren().add(highScorePane);
        spawnNewGamePieces();
        rootWrapper.sceneProperty().addListener((obs, oldS, newS) -> {
            if (newS != null) gameCanvas.requestFocus();
        });
        startGameLoop();
        return rootWrapper;
    }

    // Spawns a new piece and selects next piece
    private void spawnNewGamePieces() {
        currentPiece = rand.nextInt(TETROMINOS.length);
        nextPiece = rand.nextInt(TETROMINOS.length);
        rotation = 0;
        x = WIDTH / 2 - 2;
        y = 0;
    }

    // Starts the main game loop, handles input and timing
    private void startGameLoop() {
        // Game loop and board state
        AnimationTimer loop = new AnimationTimer() {
            private long lastFrame = 0;
            private int fallCounter = 0;
            private long startTime = -1;

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
                if (gameOver) {
                    renderBoard(gc);
                    drawGameOver(gc);
                    renderNextPiece();
                    renderScore(gc);
                    if (!highScoreSubmitted) {
                        highScorePane.setVisible(true);
                        nameField.requestFocus();
                    }
                    drawSpeed(gc, currentFallInterval);
                    if (input.consumeRelease(KeyCode.R) || input.consumeRelease(KeyCode.ENTER)) {
                        resetGame();
                        startTime = now;
                    }
                    return;
                }
                if (input.isPressed(KeyCode.LEFT) && canMove(currentPiece, rotation, x - 1, y)) x--;
                if (input.isPressed(KeyCode.RIGHT) && canMove(currentPiece, rotation, x + 1, y)) x++;
                if (input.isPressed(KeyCode.DOWN)) {
                    if (canMove(currentPiece, rotation, x, y + 1)) y++;
                    else lockAndSpawn();
                }
                if (input.consumeRelease(KeyCode.UP) || input.consumeRelease(KeyCode.SPACE)) {
                    int newRot = (rotation + 1) % 4;
                    if (tryRotate(currentPiece, newRot)) rotation = newRot;
                }
                fallCounter++;
                if (fallCounter >= currentFallInterval) {
                    fallCounter = 0;
                    if (canMove(currentPiece, rotation, x, y + 1)) y++;
                    else lockAndSpawn();
                }
                renderBoard(gc);
                drawPiece(gc, currentPiece, rotation, x, y);
                renderNextPiece();
                renderScore(gc);
                drawSpeed(gc, currentFallInterval);
            }
        };
        loop.start();
    }

    // Checks if piece can move to new position
    private boolean canMove(int tetromino, int rot, int posX, int posY) {
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                int idx = rotate(px, py, rot);
                char cell = TETROMINOS[tetromino][idx];
                if (cell == ' ') continue;
                int bx = posX + px, by = posY + py;
                if (bx < 0 || bx >= WIDTH || by < 0 || by >= HEIGHT) return false;
                if (board[bx][by] != ' ') return false;
            }
        return true;
    }

    // Locks current piece into board and spawns new piece
    private void lockAndSpawn() {
        placePiece(currentPiece, rotation, x, y);
        currentPiece = nextPiece;
        nextPiece = rand.nextInt(TETROMINOS.length);
        rotation = 0;
        x = WIDTH / 2 - 2;
        y = 0;
        if (!canMove(currentPiece, rotation, x, y)) {
            gameOver = true;
            highScorePane.setVisible(true);
        }
    }

    // Places tetromino blocks into board
    private void placePiece(int tetromino, int rot, int posX, int posY) {
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                int idx = rotate(px, py, rot);
                char cell = TETROMINOS[tetromino][idx];
                if (cell == ' ') continue;
                int bx = posX + px, by = posY + py;
                if (bx >= 0 && bx < WIDTH && by >= 0 && by < HEIGHT) board[bx][by] = cell;
            }
        clearLines();
    }

    // Clears completed lines and updates score
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
        switch (linesCleared) {
            case 1 -> score += 100;
            case 2 -> score += 300;
            case 3 -> score += 500;
            case 4 -> score += 800;
        }
    }

    // Calculates rotated index of a tetromino cell
    private int rotate(int px, int py, int r) {
        return switch (r & 3) {
            case 0 -> py * 4 + px;
            case 1 -> 12 + py - (px * 4);
            case 2 -> 15 - (py * 4) - px;
            case 3 -> 3 - py + (px * 4);
            default -> 0;
        };
    }

    // Attempts to rotate piece with wall kick logic
    private boolean tryRotate(int tetromino, int newRot) {
        if (canMove(tetromino, newRot, x, y)) return true;
        if (canMove(tetromino, newRot, x - 1, y)) {
            x--;
            return true;
        }
        if (canMove(tetromino, newRot, x + 1, y)) {
            x++;
            return true;
        }
        if (canMove(tetromino, newRot, x - 2, y)) {
            x -= 2;
            return true;
        }
        if (canMove(tetromino, newRot, x + 2, y)) {
            x += 2;
            return true;
        }
        return false;
    }

    // Draws the game board and filled cells
    private void renderBoard(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH * BLOCK_SIZE, HEIGHT * BLOCK_SIZE);
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(0.5);
        for (int bx = 0; bx <= WIDTH; bx++)
            gc.strokeLine(bx * BLOCK_SIZE, 0, bx * BLOCK_SIZE, HEIGHT * BLOCK_SIZE);
        for (int by = 0; by <= HEIGHT; by++)
            gc.strokeLine(0, by * BLOCK_SIZE, WIDTH * BLOCK_SIZE, by * BLOCK_SIZE);
        for (int bx = 0; bx < WIDTH; bx++)
            for (int by = 0; by < HEIGHT; by++)
                if (board[bx][by] != ' ')
                    drawBlock(gc, board[bx][by], bx * BLOCK_SIZE, by * BLOCK_SIZE);
    }

    // Draws the current active tetromino
    private void drawPiece(GraphicsContext gc, int tetromino, int rot, int posX, int posY) {
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                char cell = TETROMINOS[tetromino][rotate(px, py, rot)];
                if (cell != ' ') drawBlock(gc, cell, (posX + px) * BLOCK_SIZE, (posY + py) * BLOCK_SIZE);
            }
    }

    // Renders the preview of the next piece
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
        double scale = 0.85 * Math.min(nextCanvas.getWidth() / (pieceW * BLOCK_SIZE * 1.2), nextCanvas.getHeight() / (pieceH * BLOCK_SIZE * 1.2));
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

    // Draws score text
    private void renderScore(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(20));
        gc.fillText("Score: " + score, 10, 25);
    }

    // Draws current speed text
    private void drawSpeed(GraphicsContext gc, int fallInterval) {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(16));
        double speedValue = Math.round((double) baseFallInterval / fallInterval * 10) / 10.0;
        gc.fillText("Speed: " + speedValue, 10, 50);
    }

    // Draws a single tetromino block
    private void drawBlock(GraphicsContext gc, char kind, int x, int y) {
        drawBlock(gc, kind, x, y, BLOCK_SIZE);
    }

    // Draws the game over screen overlay
    private void drawGameOver(GraphicsContext gc) {
        gc.setFill(Color.color(0, 0, 0, 0.6));
        gc.fillRect(0, 0, WIDTH * BLOCK_SIZE, HEIGHT * BLOCK_SIZE);
        gc.setFill(Color.RED);
        gc.setFont(Font.font(40));
        gc.fillText("GAME OVER", 40, (HEIGHT * BLOCK_SIZE) / 2.0);
        gc.setFont(Font.font(25));
        gc.setFill(Color.WHITE);
        gc.fillText("Final Score: " + score, 60, (HEIGHT * BLOCK_SIZE) / 2.0 + 40);
        gc.setFont(Font.font(18));
        gc.fillText("Press R or Enter to restart", 60, (HEIGHT * BLOCK_SIZE) / 2.0 + 80);
    }

    // Draws the pause overlay and text
    private void drawPause(GraphicsContext gc) {
        gc.setFill(Color.color(0, 0, 0, 0.5));
        gc.fillRect(0, 0, WIDTH * BLOCK_SIZE, HEIGHT * BLOCK_SIZE);
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font(40));
        String pauseText = "PAUSED";
        Text textNode = new Text(pauseText);
        textNode.setFont(gc.getFont());
        double textWidth = textNode.getLayoutBounds().getWidth();
        double textHeight = textNode.getLayoutBounds().getHeight();
        double textX = (WIDTH * BLOCK_SIZE - textWidth) / 2;
        double textY = (HEIGHT * BLOCK_SIZE + textHeight) / 2; // vertical center
        gc.fillText(pauseText, textX, textY);
        gc.setFill(Color.LIGHTGRAY);
        gc.setFont(Font.font(20));
        gc.fillText("Score: " + score, 10, 25);
        double speedValue = Math.round((double) baseFallInterval / Math.max(minFallInterval, baseFallInterval) * 10) / 10.0;
        gc.setFont(Font.font(16));
        gc.fillText("Speed: " + speedValue, 10, 50);
    }

    // Resets board, score, and state for new game
    private void resetGame() {
        // Clear the board
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

    // Overloaded block drawer with size parameter
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
}