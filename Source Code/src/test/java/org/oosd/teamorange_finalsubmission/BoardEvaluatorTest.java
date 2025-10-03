package org.oosd.teamorange_finalsubmission;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Compares two boards: one with a clean full line, another with a hole and
 * higher bumpiness. The evaluated score for the clean board should be higher.
 */
class BoardEvaluatorTest {

    @Test
    void prefersClearedLinesAndFewerHoles() {
        // access the evaluator (package-private static class)
        TetrisAI.BoardEvaluator eval = new TetrisAI.BoardEvaluator();

        // Build two 6x4 boards as char[][] [x][y] (match TetrisAI expectation)
        int W = 6, H = 4;
        char[][] messy = new char[W][H];
        char[][] clean = new char[W][H];

        // messy: bottom row has a hole, and one column is tall
        for (int x = 0; x < W; x++) messy[x][H - 1] = (x == 3 ? ' ' : 'X');
        for (int y = H - 1; y >= H - 3; y--) messy[1][y] = 'X';

        // clean: bottom row full
        for (int x = 0; x < W; x++) clean[x][H - 1] = 'X';

        int sMessy = eval.evaluateBoard(messy);
        int sClean = eval.evaluateBoard(clean);

        assertTrue(sClean > sMessy, "clean full line should score higher");
    }
}
