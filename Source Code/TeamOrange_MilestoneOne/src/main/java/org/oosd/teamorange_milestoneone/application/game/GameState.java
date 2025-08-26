package org.oosd.teamorange_milestoneone.application.game;

import java.util.Random;

public class GameState {

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

    // Board size and block size constants (easy to implement from configuration page)
    private static final int WIDTH = 12;
    private static final int HEIGHT = 18;
    private static final int BLOCK_SIZE = 30;

    // Current piece state
    private int currentPiece, rotation, x, y;
    private int nextPiece;
    private int score = 0;

    private boolean gameOver = false;
    private boolean paused = false;
    private boolean highScoreSubmitted = false;

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getBlockSize() {
        return BLOCK_SIZE;
    }

    public void init() {
        currentPiece = rand.nextInt(TETROMINOS.length);
        rotation = 0;
        x = WIDTH / 2 - 2;
        y = 0;
        nextPiece = rand.nextInt(TETROMINOS.length);
    }

    public boolean canMove() {
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                int idx = rotate(px, py, rotation);
                char cell = TETROMINOS[currentPiece][idx];
                if (cell == ' ') continue;
                int bx = x + px, by = y + py;
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
    private void tryRotate() {
        int newRot = (rotation + 1) % 4;
        boolean canRotate = false;
        if (canMove(currentPiece, newRot, x, y)) {
            canRotate = true;
        }
        if (canMove(currentPiece, newRot, x - 1, y)) {
            x--;
            canRotate = true;
        }
        if (canMove(currentPiece, newRot, x + 1, y)) {
            x++;
            canRotate = true;
        }
        if (canMove(currentPiece, newRot, x - 2, y)) {
            x -= 2;
            canRotate = true;
        }
        if (canMove(currentPiece, newRot, x + 2, y)) {
            x += 2;
            canRotate = true;
        }
        if (canRotate) {
            rotation = newRot;
        }
    }

    public void resetGame() {

    }

    public int minFallInterval = 5;
    public int baseFallInterval = 30;

    public void tryMove(KeyInputHandler input){

        if (input.isPressed(KeyCode.LEFT) && canMove(currentPiece, rotation, x - 1, y)) x--;

        if (input.isPressed(KeyCode.RIGHT) && canMove(currentPiece, rotation, x + 1, y)) x++;

        if (input.isPressed(KeyCode.DOWN)) {
                if (canMove(currentPiece, rotation, x, y + 1)) y++;
                else lockAndSpawn();
        }
    }
    
    public void setHighScoreSubmitted(boolean submitted) {
        highScoreSubmitted = submitted;
    }
}



// Resets board, score, and state for new game
//     private void resetGame() {
//         // Clear the board
//         for (int bx = 0; bx < WIDTH; bx++)
//             for (int by = 0; by < HEIGHT; by++)
//                 board[bx][by] = ' ';
//         score = 0;
//         gameOver = false;
//         paused = false;
//         highScoreSubmitted = false;
//         highScorePane.setVisible(false);
//         nameField.setText("");
//         spawnNewGamePieces();
//         gameCanvas.requestFocus();
//     }