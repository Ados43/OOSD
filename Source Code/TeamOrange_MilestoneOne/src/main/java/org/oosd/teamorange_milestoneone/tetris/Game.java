package org.oosd.teamorange_milestoneone.tetris;

public class Game {
    private final State state;
    private final Score score;
    private final Timer timer;
    private final GameConfig config;
    private final AtomicBoolean active;
    private final Object gameLock = new Object();

    public Game(GameConfig config) {
        this.config = config;
        this.state = new State(config.boardWidth(), config.boardHeight());
        this.score = new Score();
        this.timer = new Timer(this::onTimerTick);
        this.active = new AtomicBoolean(false);

        configureGameSettings();
    }

    private void configureGameSettings() {
        // Set difficulty-based drop speed
        double dropSpeed = 1000.0 / config.difficulty(); // Higher difficulty = faster
        timer.setDropInterval(dropSpeed);

        // Configure AI if enabled
        if (config.aiEnabled()) {
            // Initialize AI player
        }
    }

    public void start() {
        synchronized (gameLock) {
            active.set(true);
            state.changeState(GameState.PLAYING);
            timer.start();
            System.out.println("[GAME] Game started - Difficulty: " + config.difficulty());
        }
    }

    public void update() {
        if (!active.get()) return;

        synchronized (gameLock) {
            // Update game logic (called at 60 FPS from SystemProcess)
            if (state.getCurrentState() == GameState.PLAYING) {
                // Handle any continuous updates here
                checkForCompletedLines();
            }
        }
    }

    private void onTimerTick() {
        synchronized (gameLock) {
            if (active.get() && state.getCurrentState() == GameState.PLAYING) {
                try {
                    if (!state.moveCurrentPieceDown()) {
                        // Piece landed
                        state.placePiece();
                        int linesCleared = state.clearLines();
                        score.addLinesCleared(linesCleared);

                        if (!state.spawnNextPiece()) {
                            // Game over
                            gameOver();
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Game tick error: " + e.getMessage());
                }
            }
        }
    }

    // Thread-safe game actions
    public void moveLeft() throws InvalidMoveException {
        synchronized (gameLock) {
            if (active.get()) state.moveCurrentPiece(-1, 0);
        }
    }

    public void moveRight() throws InvalidMoveException {
        synchronized (gameLock) {
            if (active.get()) state.moveCurrentPiece(1, 0);
        }
    }

    public void rotate() throws InvalidMoveException {
        synchronized (gameLock) {
            if (active.get()) state.rotateCurrentPiece();
        }
    }

    public boolean isActive() { return active.get(); }
    public State getState() { return state; }
    public Score getScore() { return score; }
}
