package org.oosd.teamorange_milestoneone.tetris;

public class Score {
    private int score;
    private int level;
    private int linesCleared;
    private long gameStartTime;

    public Score() { /* Initialize to 0 */ }

    public void addLinesCleared(int lines) {
        this.linesCleared += lines;
        calculateScore(lines);
        updateLevel();
    }

    private void calculateScore(int lines) { /* Tetris scoring algorithm */ }
    private void updateLevel() { /* Level up every 10 lines */ }
    public GameStats getGameStats() { /* Return record */ }
}
