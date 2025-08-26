package org.oosd.teamorange_milestoneone.application.game;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;

public class GameController {

    public GameState gameState;
    public GameRender gameRender;
    KeyInputHandler input = new KeyInputHandler();

    public GameController() {
        gameState = new GameState();
        gameRender = new GameRender();
    }

    public static Canvas createGameCanvas(GameController gameController) {
        return new Canvas(getWidth(), getHeight());
    }

    public void pause() {
        gameState.setPaused(true);
    }

    public void unpause() {
        gameState.setPaused(false);
    }

    public int getWidth() {
        return gameState.getWidth() * gameState.getBlockSize();
    }

    public int getHeight() {
        return gameState.getHeight() * gameState.getBlockSize();
    }

    public void newBoard() {
        gameRender.newBoard();
    }

    public void startGameLoop(ApplicationController appController, GraphicsContext graphicsContext) {
        AnimationTimer loop = new AnimationTimer() {
            private long lastFrame = 0;
            private int fallCounter = 0;
            private long startTime = -1;

            @Override
            public void handle(long now) {

                // Checks
                if (startTime < 0) startTime = now;
                if (now - lastFrame < 50_000_000) return;
                lastFrame = now;
                double elapsedSec = (now - startTime) / 1_000_000_000.0;
                int decrease = (int) (elapsedSec / 3.0);
                int currentFallInterval = Math.max(gameState.minFallInterval, gameState.baseFallInterval - decrease);

                if (gameState.isActive()) {
                    gameRender.renderGame();

                } else if (gameState.isPaused()) {
                    gameRender.renderPausedState();

                } else if (gameState.isGameOver()) {
                    gameRender.renderGameOver();

                    // if (!gameState.isHighScoreSubmitted()) {
                    //     appController.showHighScorePane();
                    // }
                    gameRender.drawSpeed(graphicsContext, currentFallInterval);
                    if (input.consumeRelease(KeyCode.ENTER)) {
                        gameController.resetGame();
                        startTime = now;
                    }
                    return;
                }

                // Control movement
                if (input.hasValue()) {
                    gameState.tryMove(input);
                }


                if (input.consumeRelease(KeyCode.UP) || input.consumeRelease(KeyCode.SPACE)) {
                    gameState.tryRotate();
                }

                fallCounter++;

                if (fallCounter >= currentFallInterval) {
                    fallCounter = 0;
                    if (gameState.canMove()) y++;
                    else gameState.lockAndSpawn();
                }
            }
        };
        loop.start();
    }

    public void setHighScoreSubmitted(boolean submitted) {
        gameState.setHighScoreSubmitted(submitted);
    }

    // // Game speed controls (Easy to implement from configuration)
    // private final int baseFallInterval = 30;
    // private final int minFallInterval = 5;

    // // Spawns a new piece and selects next piece
    // private void spawnNewGamePieces() {
    //     currentPiece = rand.nextInt(TETROMINOS.length);
    //     nextPiece = rand.nextInt(TETROMINOS.length);
    //     rotation = 0;
    //     x = WIDTH / 2 - 2;
    //     y = 0;
    // }

    // // Starts the main game loop, handles input and timing
    // private void startGameLoop() {
    //     // Game loop and board state
    //     AnimationTimer loop = new AnimationTimer() {
    //         private long lastFrame = 0;
    //         private int fallCounter = 0;
    //         private long startTime = -1;

    //         @Override
    //         public void handle(long now) {
    //             if (startTime < 0) startTime = now;
    //             if (now - lastFrame < 50_000_000) return;
    //             lastFrame = now;
    //             if (input.consumeRelease(KeyCode.ESCAPE)) paused = !paused; // Toggle pause on ESC
    //             if (input.consumeRelease(KeyCode.P)) paused = !paused; // Toggle pause on P
    //             double elapsedSec = (now - startTime) / 1_000_000_000.0;
    //             int decrease = (int) (elapsedSec / 3.0);
    //             int currentFallInterval = Math.max(minFallInterval, baseFallInterval - decrease);
    //             if (paused && !gameOver) {
    //                 renderBoard(gc);
    //                 drawPiece(gc, currentPiece, rotation, x, y);
    //                 renderNextPiece();
    //                 renderScore(gc);
    //                 drawPause(gc);
    //                 return;
    //             }
    //             if (gameOver) {
    //                 renderBoard(gc);
    //                 drawGameOver(gc);
    //                 renderNextPiece();
    //                 renderScore(gc);
    //                 if (!highScoreSubmitted) {
    //                     highScorePane.setVisible(true);
    //                     nameField.requestFocus();
    //                 }
    //                 drawSpeed(gc, currentFallInterval);
    //                 if (input.consumeRelease(KeyCode.R) || input.consumeRelease(KeyCode.ENTER)) {
    //                     resetGame();
    //                     startTime = now;
    //                 }
    //                 return;
    //             }
    //             if (input.isPressed(KeyCode.LEFT) && canMove(currentPiece, rotation, x - 1, y)) x--;
    //             if (input.isPressed(KeyCode.RIGHT) && canMove(currentPiece, rotation, x + 1, y)) x++;
    //             if (input.isPressed(KeyCode.DOWN)) {
    //                 if (canMove(currentPiece, rotation, x, y + 1)) y++;
    //                 else lockAndSpawn();
    //             }
    //             if (input.consumeRelease(KeyCode.UP) || input.consumeRelease(KeyCode.SPACE)) {
    //                 int newRot = (rotation + 1) % 4;
    //                 if (tryRotate(currentPiece, newRot)) rotation = newRot;
    //             }
    //             fallCounter++;
    //             if (fallCounter >= currentFallInterval) {
    //                 fallCounter = 0;
    //                 if (canMove(currentPiece, rotation, x, y + 1)) y++;
    //                 else lockAndSpawn();
    //             }
    //             renderBoard(gc);
    //             drawPiece(gc, currentPiece, rotation, x, y);
    //             renderNextPiece();
    //             renderScore(gc);
    //             drawSpeed(gc, currentFallInterval);
    //         }
    //     };
    //     loop.start();
    // }

    // // Checks if piece can move to new position
    // private boolean canMove(int tetromino, int rot, int posX, int posY) {
    //     for (int px = 0; px < 4; px++)
    //         for (int py = 0; py < 4; py++) {
    //             int idx = rotate(px, py, rot);
    //             char cell = TETROMINOS[tetromino][idx];
    //             if (cell == ' ') continue;
    //             int bx = posX + px, by = posY + py;
    //             if (bx < 0 || bx >= WIDTH || by < 0 || by >= HEIGHT) return false;
    //             if (board[bx][by] != ' ') return false;
    //         }
    //     return true;
    // }

    // // Locks current piece into board and spawns new piece
    // private void lockAndSpawn() {
    //     placePiece(currentPiece, rotation, x, y);
    //     currentPiece = nextPiece;
    //     nextPiece = rand.nextInt(TETROMINOS.length);
    //     rotation = 0;
    //     x = WIDTH / 2 - 2;
    //     y = 0;
    //     if (!canMove(currentPiece, rotation, x, y)) {
    //         gameOver = true;
    //         highScorePane.setVisible(true);
    //     }
    // }

    // // Places tetromino blocks into board
    // private void placePiece(int tetromino, int rot, int posX, int posY) {
    //     for (int px = 0; px < 4; px++)
    //         for (int py = 0; py < 4; py++) {
    //             int idx = rotate(px, py, rot);
    //             char cell = TETROMINOS[tetromino][idx];
    //             if (cell == ' ') continue;
    //             int bx = posX + px, by = posY + py;
    //             if (bx >= 0 && bx < WIDTH && by >= 0 && by < HEIGHT) board[bx][by] = cell;
    //         }
    //     clearLines();
    // }

    // // Clears completed lines and updates score
    // private void clearLines() {
    //     int linesCleared = 0;
    //     for (int by = 0; by < HEIGHT; by++) {
    //         boolean full = true;
    //         for (int bx = 0; bx < WIDTH; bx++) {
    //             if (board[bx][by] == ' ') {
    //                 full = false;
    //                 break;
    //             }
    //         }
    //         if (full) {
    //             linesCleared++;
    //             for (int ty = by; ty > 0; ty--)
    //                 for (int bx = 0; bx < WIDTH; bx++)
    //                     board[bx][ty] = board[bx][ty - 1];
    //             for (int bx = 0; bx < WIDTH; bx++) board[bx][0] = ' ';
    //         }
    //     }
    //     switch (linesCleared) {
    //         case 1 -> score += 100;
    //         case 2 -> score += 300;
    //         case 3 -> score += 500;
    //         case 4 -> score += 800;
    //     }
    // }

    // // Calculates rotated index of a tetromino cell
    // private int rotate(int px, int py, int r) {
    //     return switch (r & 3) {
    //         case 0 -> py * 4 + px;
    //         case 1 -> 12 + py - (px * 4);
    //         case 2 -> 15 - (py * 4) - px;
    //         case 3 -> 3 - py + (px * 4);
    //         default -> 0;
    //     };
    // }

    // // Attempts to rotate piece with wall kick logic
    // private boolean tryRotate(int tetromino, int newRot) {
    //     if (canMove(tetromino, newRot, x, y)) return true;
    //     if (canMove(tetromino, newRot, x - 1, y)) {
    //         x--;
    //         return true;
    //     }
    //     if (canMove(tetromino, newRot, x + 1, y)) {
    //         x++;
    //         return true;
    //     }
    //     if (canMove(tetromino, newRot, x - 2, y)) {
    //         x -= 2;
    //         return true;
    //     }
    //     if (canMove(tetromino, newRot, x + 2, y)) {
    //         x += 2;
    //         return true;
    //     }
    //     return false;
    // }

    // // Resets board, score, and state for new game
    // private void resetGame() {
    //     // Clear the board
    //     for (int bx = 0; bx < WIDTH; bx++)
    //         for (int by = 0; by < HEIGHT; by++)
    //             board[bx][by] = ' ';
    //     score = 0;
    //     gameOver = false;
    //     paused = false;
    //     highScoreSubmitted = false;
    //     highScorePane.setVisible(false);
    //     nameField.setText("");
    //     spawnNewGamePieces();
    //     gameCanvas.requestFocus();
    // }

}
