package org.oosd.teamorange_milestoneone.tetris;

public class State {
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 20;

    private final int[][] board;
    private GameState gameState;
    private Tetromino currentPiece;
    private Tetromino nextPiece;
    private final List<BoardObserver> boardObservers;
    private final List<GameObserver> gameObservers;

    public State() { /* Initialize empty board, set MENU state */ }

    // Board operations
    public boolean isValidPosition(Tetromino piece) { /* ... */ }
    public void placePiece(Tetromino piece) throws InvalidMoveException { /* ... */ }
    public int clearLines() { /* Returns number of lines cleared */ }

    // State management
    public void changeState(GameState newState) throws GameStateException { /* ... */ }
    public void spawnNextPiece() { /* ... */ }

    // Observer pattern
    public void addBoardObserver(BoardObserver observer) { /* ... */ }
    public void addGameObserver(GameObserver observer) { /* ... */ }
    private void notifyBoardChanged() { /* ... */ }
    private void notifyGameStateChanged(GameState oldState) { /* ... */ }

    // Game State enum
    private GameState currentState = GameState.MENU;

    public void changeState(GameState newState) throws GameStateException {
        if (!isValidStateTransition(currentState, newState)) {
            throw new GameStateException("Invalid transition from " + currentState + " to " + newState);
        }
        GameState oldState = currentState;
        currentState = newState;
        notifyGameStateChanged(oldState, newState);
    }

    // Controls what actions are allowed
    public void moveLeft() throws InvalidMoveException {
        if (currentState != GameState.PLAYING) {
            throw new GameStateException("Cannot move piece when game is " + currentState);
        }
        // ... movement logic
    }

}
