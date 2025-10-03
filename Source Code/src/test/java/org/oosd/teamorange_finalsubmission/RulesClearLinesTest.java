package org.oosd.teamorange_finalsubmission;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RulesClearLinesTest {

    @Test
    void clearsFullRowsAndCompactsBoard() {
        int[][] b = {
                {0, 0, 0, 0},  // y=0 (top)
                {1, 1, 1, 1},  // y=1 full
                {0, 1, 0, 0},  // y=2
                {1, 1, 1, 1}   // y=3 full (bottom)
        };
        // transpose to [H][W]
        int[][] board = new int[][]{
                b[0], b[1], b[2], b[3]
        };

        int cleared = Rules.clearFullLines(board);
        assertEquals(2, cleared, "should clear 2 rows");

        // After compaction, bottom row becomes former y=2,
        // and above are zeros.
        assertArrayEquals(new int[]{0, 1, 0, 0}, board[3]);
        assertArrayEquals(new int[]{0, 0, 0, 0}, board[2]);
        assertArrayEquals(new int[]{0, 0, 0, 0}, board[1]);
        assertArrayEquals(new int[]{0, 0, 0, 0}, board[0]);
    }
}
