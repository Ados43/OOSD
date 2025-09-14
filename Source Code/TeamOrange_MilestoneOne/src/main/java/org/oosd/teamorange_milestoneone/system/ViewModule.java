package org.oosd.teamorange_milestoneone.system;

public class ViewModule {
    private final Stage primaryStage;
    private final BorderPane mainContainer;
    private final Map<String, SubModule> subModules;
    private SystemProcess systemProcess;

    // Executive level controllers for demo
    private final MenuController menuController;
    private final GameController gameController;
    private final ConfigController configController;
    private final LeaderboardController leaderboardController;

    public ViewModule(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.mainContainer = new BorderPane();
        this.subModules = new HashMap<>();

        // Initialize executive controllers
        this.menuController = new MenuController(this);
        this.gameController = new GameController(this);
        this.configController = new ConfigController(this);
        this.leaderboardController = new LeaderboardController(this);

        initializeSubModules();
        setupMainScene();
    }

    private void initializeSubModules() {
        subModules.put("splash", new SplashSubModule());
        subModules.put("menu", new MenuSubModule(menuController));
        subModules.put("game", new GameSubModule(gameController));
        subModules.put("config", new ConfigSubModule(configController));
        subModules.put("leaderboard", new LeaderboardSubModule(leaderboardController));
    }

    private void setupMainScene() {
        Scene scene = new Scene(mainContainer, 1024, 768);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    // Navigation methods called by SystemProcess
    public void showSplash() {
        switchToSubModule("splash");
    }

    public void showMainMenu() {
        switchToSubModule("menu");
    }

    public void showGameView() {
        switchToSubModule("game");
        // Enable key input for game
        primaryStage.getScene().setOnKeyPressed(systemProcess.getIO()::handleKeyPress);
    }

    public void showConfig() {
        configController.loadCurrentConfig(systemProcess.getConfig());
        switchToSubModule("config");
    }

    public void showLeaderboard() {
        leaderboardController.loadLeaderboard(systemProcess.getIO().loadLeaderboard());
        switchToSubModule("leaderboard");
    }

    private void switchToSubModule(String moduleName) {
        SubModule module = subModules.get(moduleName);
        if (module != null) {
            Platform.runLater(() -> {
                mainContainer.setCenter(module.getView());
                module.onShow();
            });
        }
    }

    // 60 FPS rendering called by SystemProcess
    public void render(State gameState, GameStats stats) {
        GameSubModule gameModule = (GameSubModule) subModules.get("game");
        if (gameModule != null) {
            gameModule.render(gameState, stats);
        }
    }

    // Executive level controller access for demo purposes
    public MenuController getMenuController() { return menuController; }
    public GameController getGameController() { return gameController; }
    public ConfigController getConfigController() { return configController; }
    public LeaderboardController getLeaderboardController() { return leaderboardController; }

    public void setSystemProcess(SystemProcess systemProcess) {
        this.systemProcess = systemProcess;
    }
}
