package org.oosd.teamorange_milestoneone.tetris;

public class Tetromino {
    private final TetrominoType type;
    private Position[] shape;  // Current rotation shape
    private Position position; // Top-left position on board
    private int rotation;      // Current rotation state (0-3)

    public Tetromino(TetrominoType type) { /* ... */ }
    public Tetromino rotate() throws InvalidMoveException { /* ... */ }
    public Tetromino move(int dx, int dy) { /* ... */ }
    public Position[] getAbsolutePositions() { /* ... */ }
}
