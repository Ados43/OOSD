package org.oosd.teamorange_finalsubmission;

/**
 * Rules
 * -----
 * Pure logic helpers expressed with simple int[][] arrays so they are easy to test.
 * - rotateIndex: same math used by the 4x4 tetromino rotation in TetrisGame.
 * - clearFullLines: removes full rows from a [H][W] 0/1 board and returns how many were cleared.
 * - canPlace: collision/bounds check for a trimmed 0/1 shape at (col,row) on a [H][W] board.
 */
public final class Rules {
    private Rules() {
    }

    /**
     * 4x4 index mapping used by the game’s rotation math.
     */
    public static int rotateIndex(int px, int py, int r) {
        return switch (r & 3) {
            case 0 -> py * 4 + px;
            case 1 -> 12 + py - (px * 4);
            case 2 -> 15 - (py * 4) - px;
            default -> 3 - py + (px * 4);
        };
    }

    /**
     * Clears full rows in-place on a [H][W] board of 0/1 and returns the number of rows cleared.
     */
    public static int clearFullLines(int[][] board) {
        final int H = board.length, W = board[0].length;
        int dst = H - 1;
        int cleared = 0;

        for (int y = H - 1; y >= 0; y--) {
            boolean full = true;
            for (int x = 0; x < W; x++) {
                if (board[y][x] == 0) {
                    full = false;
                    break;
                }
            }
            if (!full) {
                if (dst != y) {
                    for (int x = 0; x < W; x++) board[dst][x] = board[y][x];
                }
                dst--;
            } else {
                cleared++;
            }
        }
        // fill remaining with empty
        for (int y = dst; y >= 0; y--)
            for (int x = 0; x < W; x++)
                board[y][x] = 0;

        return cleared;
    }

    /**
     * True if trimmed 0/1 shape can be placed at (col,row) on board [H][W].
     */
    public static boolean canPlace(int[][] board, int[][] shape, int col, int row) {
        final int H = board.length, W = board[0].length;
        final int sh = shape.length, sw = shape[0].length;

        for (int y = 0; y < sh; y++)
            for (int x = 0; x < sw; x++) {
                if (shape[y][x] == 0) continue;
                int bx = col + x, by = row + y;
                if (bx < 0 || bx >= W || by < 0 || by >= H) return false;
                if (board[by][bx] != 0) return false;
            }
        return true;
    }
}
