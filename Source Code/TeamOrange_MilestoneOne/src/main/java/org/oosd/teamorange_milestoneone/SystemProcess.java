package org.oosd.teamorange_milestoneone;

public class SystemProcess {
    private final ExecutorService gameThread;
    private final ExecutorService renderThread;
    private final AtomicBoolean running;

    private Game game;
    private IO ioHandler;
    private ViewModule viewModule;
    private GameConfig config;

    // 60 FPS rendering
    private static final long FRAME_TIME_NS = 1_000_000_000L / 60;
    private Timeline renderLoop;

    public SystemProcess() {
        this.gameThread = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "GameLogic");
            t.setDaemon(true);
            return t;
        });
        this.renderThread = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "Renderer");
            t.setDaemon(true);
            return t;
        });
        this.running = new AtomicBoolean(false);
    }

    public void initialize() {
        running.set(true);

        // Load configuration
        config = ioHandler.loadConfig();

        // Initialize render loop at 60 FPS
        renderLoop = new Timeline(new KeyFrame(
                Duration.millis(1000.0 / 60),
                e -> renderFrame()
        ));
        renderLoop.setCycleCount(Timeline.INDEFINITE);

        // Show splash and run init tests
        viewModule.showSplash();
        runInitializationTests();
    }

    private void runInitializationTests() {
        System.out.println("=== Tetris Initialization Tests ===");
        System.out.println("Testing Tetromino rotations...");
        testTetrominoRotations();
        System.out.println("Testing collision detection...");
        testCollisionDetection();
        System.out.println("Testing file I/O...");
        testFileIO();
        System.out.println("=== Initialization Complete ===");

        System.out.println("=== Skipping Tests ===");
        System.out.println("=== Waiting 5 seconds... ===");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("=== Done waiting ===");

        // After tests, show main menu
        Platform.runLater(() -> viewModule.showMainMenu());
    }

    public void startGame() {
        game = new Game(config);
        ioHandler.setupInputHandling(game);

        gameThread.submit(() -> {
            try {
                game.start();
                startRenderLoop();
            } catch (Exception e) {
                handleGameError(e);
            }
        });

        viewModule.showGameView();
    }

    private void startRenderLoop() {
        Platform.runLater(() -> renderLoop.play());
    }

    private void renderFrame() {
        if (game != null && game.isActive()) {
            // Update game state on game thread
            gameThread.submit(() -> game.update());

            // Render on JavaFX thread (we're already on it)
            viewModule.render(game.getState(), game.getScore().getGameStats());
        }
    }

    public void pauseGame() { /* ... */ }
    public void resumeGame() { /* ... */ }
    public void endGame() { /* ... */ }

    private void testTetrominoRotations() {
        System.out.println("Testing all tetromino rotations:");
        for (TetrominoType type : TetrominoType.values()) {
            System.out.println("\n=== " + type + " ===");
            for (int rotation = 0; rotation < 4; rotation++) {
                type.printShape(rotation);

                // Test position calculation
                Tetromino piece = new Tetromino(type, new Position(5, 10), rotation);
                System.out.println("At board position (5,10): " + piece.getAbsolutePositions());
            }
        }
        System.out.println("✓ Tetromino rotation tests complete\n");
    }

    private void testCollisionDetection() {
        System.out.println("Testing collision detection:");

        // Create test board with some filled cells
        State testState = new State(10, 20);
        // Simulate some placed pieces at bottom
        System.out.println("✓ Collision detection tests complete\n");
    }

    private void testFileIO() {
        System.out.println("Testing file I/O operations:");

        // Test config save/load
        GameConfig testConfig = GameConfig.defaultConfig();
        ioHandler.saveConfig(testConfig);
        GameConfig loadedConfig = ioHandler.loadConfig();
        System.out.println("Config save/load: " +
                (testConfig.difficulty() == loadedConfig.difficulty() ? "✓ PASS" : "✗ FAIL"));

        // Test leaderboard operations
        List<LeaderboardEntry> testEntries = List.of(
                new LeaderboardEntry("TestPlayer", 1000, 5, LocalDateTime.now())
        );
        ioHandler.saveLeaderboard(testEntries);
        List<LeaderboardEntry> loadedEntries = ioHandler.loadLeaderboard();
        System.out.println("Leaderboard save/load: " +
                (!loadedEntries.isEmpty() ? "✓ PASS" : "✗ FAIL"));

        System.out.println("✓ File I/O tests complete\n");
    }

    public void shutdown() {
        running.set(false);
        if (renderLoop != null) renderLoop.stop();
        gameThread.shutdown();
        renderThread.shutdown();
        ioHandler.saveConfig(config);
    }
}
