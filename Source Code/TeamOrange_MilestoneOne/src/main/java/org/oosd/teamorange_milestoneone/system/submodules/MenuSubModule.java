package org.oosd.teamorange_milestoneone.system.submodules;

public class MenuSubModule extends SubModule {
    private final MenuController controller;

    public MenuSubModule(MenuController controller) {
        this.controller = controller;
    }

    @Override
    public Parent createView() {
        VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.getStyleClass().add("menu-container");

        Label titleLabel = new Label("TETRIS");
        titleLabel.getStyleClass().add("game-title");

        Button playButton = createMenuButton("PLAY", controller::handlePlay);
        Button leaderboardButton = createMenuButton("LEADERBOARD", controller::handleLeaderboard);
        Button configButton = createMenuButton("CONFIG", controller::handleConfig);
        Button exitButton = createMenuButton("EXIT", controller::handleExit);

        menuBox.getChildren().addAll(titleLabel, playButton, leaderboardButton, configButton, exitButton);
        return menuBox;
    }

    private Button createMenuButton(String text, Runnable action) {
        Button button = new Button(text);
        button.getStyleClass().add("menu-button");
        button.setOnAction(e -> {
            playButtonSound();
            action.run();
        });
        return button;
    }

    private void playButtonSound() {
        // Audio feedback for menu navigation
    }

    @Override
    public void onShow() {
        // Reset any animations or state
    }

    @Override
    public void onHide() {
        // Cleanup if needed
    }
}
