package org.oosd.teamorange_milestoneone.tetris.observers;

public interface BoardObserver {
    void onBoardChanged(int[][] board);
    void onLinesCleared(int[] clearedLines);
}
