package org.oosd.teamorange_milestoneone.application.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.scene.canvas.Canvas;

public class GameRender {
    // createGameCanvas using game state constants
    public static Canvas createGameCanvas(GameController gameController) {
        return new Canvas(gameController.getWidth(), gameController.getHeight());
    }

    public char[][] board;

    public void newBoard() {
        board = new char[WIDTH][HEIGHT];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                board[j][i] = ' ';
            }
        }
    }

    public void renderGame(){ 
        // Render board
        renderBoard();
        // draw game over
        drawGameOver();
        // render next piece
        renderNextPiece();
        // render score
        renderScore();
    }

    // Draws the game board and filled cells
    public void renderBoard(GraphicsContext gc) {
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

    public void renderGameOver() {
        // Implementation for rendering game over state
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

    public void renderPausedState(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(40));
        gc.fillText("Paused", WIDTH * BLOCK_SIZE / 2 - 50, HEIGHT * BLOCK_SIZE / 2);
    }

    // Draws current speed text
    private void drawSpeed(GraphicsContext gc, int fallInterval) {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(16));
        double speedValue = Math.round((double) baseFallInterval / fallInterval * 10) / 10.0;
        gc.fillText("Speed: " + speedValue, 10, 50);
    }

   // Draws the current active tetromino
    private void drawPiece(GraphicsContext gc, int tetromino, int rot, int posX, int posY) {
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                char cell = TETROMINOS[tetromino][rotate(px, py, rot)];
                if (cell != ' ') drawBlock(gc, cell, (posX + px) * BLOCK_SIZE, (posY + py) * BLOCK_SIZE);
            }
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

}

    // // Draws current speed text
    // private void drawSpeed(GraphicsContext gc, int fallInterval) {
    //     gc.setFill(Color.WHITE);
    //     gc.setFont(Font.font(16));
    //     double speedValue = Math.round((double) baseFallInterval / fallInterval * 10) / 10.0;
    //     gc.fillText("Speed: " + speedValue, 10, 50);
    // }

    // // Draws a single tetromino block
    // private void drawBlock(GraphicsContext gc, char kind, int x, int y) {
    //     drawBlock(gc, kind, x, y, BLOCK_SIZE);
    // }


    // // Overloaded block drawer with size parameter
    // private void drawBlock(GraphicsContext gc, char kind, int x, int y, int size) {
    //     Color color = switch (kind) {
    //         case 'I' -> Color.CYAN;
    //         case 'O' -> Color.YELLOW;
    //         case 'T' -> Color.MEDIUMPURPLE;
    //         case 'S' -> Color.LIMEGREEN;
    //         case 'Z' -> Color.RED;
    //         case 'J' -> Color.DODGERBLUE;
    //         case 'L' -> Color.ORANGE;
    //         default -> Color.GRAY;
    //     };
    //     gc.setFill(color);
    //     gc.fillRect(x, y, size, size);
    //     gc.setStroke(Color.BLACK);
    //     gc.strokeRect(x, y, size, size);
    // }

// // Builds the UI, initializes board and starts game loop
    // public Parent createContent() {
    //     gameCanvas = new Canvas(WIDTH * BLOCK_SIZE, HEIGHT * BLOCK_SIZE);
    //     gc = gameCanvas.getGraphicsContext2D();
    //     gameCanvas.setFocusTraversable(true);
    //     gameCanvas.setOnKeyPressed(input);
    //     gameCanvas.setOnKeyReleased(input);
    //     nextCanvas = new Canvas(6 * BLOCK_SIZE, 6 * BLOCK_SIZE);

    //     nextGc = nextCanvas.getGraphicsContext2D();

    //     Button backButton = new Button("Back");

    //     backButton.setOnAction(e -> {

    //         int pauseCondition = 0;
    //         if (!paused) { // If not paused, pause the game
    //             paused = !paused;
    //             pauseCondition++;
    //         }

    //         Alert a = new Alert(Alert.AlertType.CONFIRMATION,
    //                 "Your current progress will be lost if you return to the main menu. Are you sure?",
    //                 ButtonType.YES, ButtonType.NO);

    //         Optional<ButtonType> res = a.showAndWait();

    //         if (res.isPresent() && res.get() == ButtonType.YES) {
    //             if (onBack != null) onBack.run();

    //         } else {
    //             if (pauseCondition > 0) paused = !paused; // Unpause if it was paused by the back button
    //         }
    //     });

    //     backButton.setFocusTraversable(false);
    //     HBox root = new HBox(20, gameCanvas, nextCanvas);
    //     root.setAlignment(Pos.CENTER);

    //     // UI components for game board and next piece preview
    //     StackPane rootWrapper = new StackPane(root, backButton);
    //     StackPane.setAlignment(backButton, Pos.TOP_LEFT);

    //     backButton.setTranslateX(10);
    //     backButton.setTranslateY(10);

    //     rootWrapper.setPrefSize(WIDTH * BLOCK_SIZE + (int) nextCanvas.getWidth() + 60,
    //             HEIGHT * BLOCK_SIZE + 40);

    //     rootWrapper.setAlignment(Pos.CENTER);

    //     board = new char[WIDTH][HEIGHT];

    //     for (int bx = 0; bx < WIDTH; bx++)
    //         for (int by = 0; by < HEIGHT; by++)
    //             board[bx][by] = ' ';

    //     nameField = new TextField();
    //     nameField.setPromptText("Enter name");
    //     nameField.setMaxWidth(120);

    //     Button submitHS = new Button("Submit");
    //     submitHS.setOnAction(e -> {
    //         String name = nameField.getText().trim();
    //         if (!name.isEmpty()) {
    //             System.out.println("Highscore submit: " + name + " Score: " + score);
    //             highScorePane.setVisible(false);
    //             highScoreSubmitted = true; // <-- mark as submitted
    //         }
    //     });

    //     highScorePane = new VBox(10, nameField, submitHS);
    //     highScorePane.setAlignment(Pos.CENTER);
    //     highScorePane.setMaxWidth(200);
    //     highScorePane.setVisible(false);
    //     rootWrapper.getChildren().add(highScorePane);

    //     spawnNewGamePieces();

    //     rootWrapper.sceneProperty().addListener((obs, oldS, newS) -> {
    //         if (newS != null) gameCanvas.requestFocus();
    //     });


    //     startGameLoop();
    //     return rootWrapper;
    // }

    //     // References to main menu buttons from FXML
    // @FXML
    // private Button playButton, configurationButton, highScoreButton;

    // @FXML
    // protected void onPlayTetris() {
    //     // Create a new Tetris game instance and its root node
    //     TetrisGame game = new TetrisGame();
    //     Parent tetrisRoot = game.createContent();

    //     // Get reference to the current stage (main window)
    //     Stage stage = (Stage) playButton.getScene().getWindow();

    //     // Allow returning to the main menu via game’s back action
    //     game.setOnBack(() -> goBackToMenu(stage));

    //     // Set Tetris scene in the stage
    //     stage.setScene(new Scene(tetrisRoot, 800, 600));
    //     stage.show();
    // }