package org.oosd.teamorange_finalsubmission;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RulesCanPlaceTest {

    @Test
    void rejectsOutOfBoundsAndCollisions() {
        int[][] board = new int[4][5]; // H=4,W=5 -> [y][x], zeroed

        // occupy a cell at (2,2)
        board[2][2] = 1;

        int[][] shape = {
                {1, 1},
                {1, 0}
        }; // a small L

        assertTrue(Rules.canPlace(board, shape, 0, 0));
        assertFalse(Rules.canPlace(board, shape, -1, 0)); // left oob
        assertFalse(Rules.canPlace(board, shape, 4, 0));  // right oob
        assertFalse(Rules.canPlace(board, shape, 2, 2));  // collision
    }
}
