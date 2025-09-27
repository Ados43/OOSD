package org.oosd.teamorange_finalsubmission;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

// Main menu controller
public class HelloController {

    // Simple pair record
    public record HighScore(String player, int score) {
    }

    // FXML buttons
    @FXML
    private Button playButton, configurationButton, highScoreButton;

    // Theme path
    private static final String THEME = "/theme/tetris-theme.css";

    // Managers & stores
    private final MediaManager mediaManager = new MediaManager();
    private final HighScoreStore highScoreStore = new HighScoreStore();
    private final ConfigStore configStore = new ConfigStore();

    // Config cache
    private AppConfig cfg = configStore.load();

    // Apply theme
    private void applyTheme(Scene scene) {
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource(THEME)).toExternalForm()
        );
    }

    // Start game
    @FXML
    protected void onPlayTetris() {
        cfg = configStore.load();
        mediaManager.setMusicMuted(!cfg.musicOn);
        mediaManager.setSfxMuted(!cfg.sfxOn);

        TetrisGame game = new TetrisGame();
        game.setHighScoreStore(highScoreStore);
        game.setMediaManager(mediaManager);
        game.setConfig(
                cfg.fieldWidth,
                cfg.fieldHeight,
                cfg.gameLevel,
                cfg.extendedMode,
                cfg.musicOn,
                cfg.sfxOn,
                cfg.player1,
                cfg.player2
        );

        Parent tetrisRoot = game.createContent();
        Stage stage = (Stage) playButton.getScene().getWindow();

        game.setOnBack(() -> {
            mediaManager.stopBgMusic();
            goBackToMenu(stage);
        });

        Scene gameScene = new Scene(tetrisRoot, 1000, 900);
        applyTheme(gameScene);
        stage.setScene(gameScene);
        stage.setMinWidth(900);
        stage.setMinHeight(700);
        stage.show();
    }

    // Show scores
    @FXML
    protected void onShowHighScores() {
        Stage stage = (Stage) highScoreButton.getScene().getWindow();
        Parent view = HighScoreView.create(highScoreStore, () -> goBackToMenu(stage));

        Label title = new Label("High Scores");
        title.getStyleClass().add("screen-title");

        VBox card = new VBox(16, title, view);
        card.setAlignment(Pos.TOP_CENTER);
        card.setMaxWidth(900);

        StackPane root = new StackPane(card);

        Scene scene = new Scene(root, 900, 640);
        applyTheme(scene);
        stage.setScene(scene);
        stage.show();
    }

    // Configure game
    @FXML
    protected void onConfiguration() {
        cfg = configStore.load();
        Stage stage = (Stage) configurationButton.getScene().getWindow();

        Label title = new Label("Configuration");
        title.getStyleClass().add("screen-title");

        // Sliders
        Slider widthSlider = new Slider(5, 15, clamp(cfg.fieldWidth, 5, 15));
        Slider heightSlider = new Slider(15, 30, clamp(cfg.fieldHeight, 15, 30));
        Slider levelSlider = new Slider(1, 10, clamp(cfg.gameLevel, 1, 10));
        for (Slider s : new Slider[]{widthSlider, heightSlider, levelSlider}) {
            s.setMajorTickUnit(1);
            s.setMinorTickCount(0);
            s.setSnapToTicks(true);
            s.setShowTickMarks(true);
            s.setShowTickLabels(true);
        }

        Label widthLabel = new Label("Field Width (cells):");
        Label heightLabel = new Label("Field Height (cells):");
        Label levelLabel = new Label("Game Level:");
        widthLabel.getStyleClass().add("config-label");
        heightLabel.getStyleClass().add("config-label");
        levelLabel.getStyleClass().add("config-label");

        Label widthValue = new Label(String.valueOf((int) widthSlider.getValue()));
        Label heightValue = new Label(String.valueOf((int) heightSlider.getValue()));
        Label levelValue = new Label(String.valueOf((int) levelSlider.getValue()));
        widthSlider.valueProperty().addListener((obs, ov, nv) -> widthValue.setText(String.valueOf(nv.intValue())));
        heightSlider.valueProperty().addListener((obs, ov, nv) -> heightValue.setText(String.valueOf(nv.intValue())));
        levelSlider.valueProperty().addListener((obs, ov, nv) -> levelValue.setText(String.valueOf(nv.intValue())));

        // Toggles
        CheckBox musicChk = new CheckBox();
        musicChk.setSelected(cfg.musicOn);
        Label musicText = new Label("Music:");
        Label musicState = new Label(cfg.musicOn ? "On" : "Off");
        musicChk.selectedProperty().addListener((obs, ov, nv) -> musicState.setText(nv ? "On" : "Off"));

        CheckBox sfxChk = new CheckBox();
        sfxChk.setSelected(cfg.sfxOn);
        Label sfxText = new Label("Sound Effects:");
        Label sfxState = new Label(cfg.sfxOn ? "On" : "Off");
        sfxChk.selectedProperty().addListener((obs, ov, nv) -> sfxState.setText(nv ? "On" : "Off"));

        CheckBox extendChk = new CheckBox();
        extendChk.setSelected(cfg.extendedMode);
        Label extendText = new Label("Extended Mode:");
        Label extendState = new Label(cfg.extendedMode ? "On" : "Off");
        musicText.getStyleClass().add("config-label");
        sfxText.getStyleClass().add("config-label");
        extendText.getStyleClass().add("config-label");

        // Player one
        Label p1Text = new Label("Player One:");
        p1Text.getStyleClass().add("config-label");
        ToggleGroup p1Group = new ToggleGroup();
        RadioButton p1Human = new RadioButton("Human");
        RadioButton p1Ai = new RadioButton("AI");
        RadioButton p1External = new RadioButton("External");
        p1Human.getStyleClass().add("radio-clean");
        p1Ai.getStyleClass().add("radio-clean");
        p1External.getStyleClass().add("radio-clean");
        p1Human.setToggleGroup(p1Group);
        p1Ai.setToggleGroup(p1Group);
        p1External.setToggleGroup(p1Group);
        switch (cfg.player1) {
            case HUMAN -> p1Human.setSelected(true);
            case AI -> p1Ai.setSelected(true);
            case EXTERNAL -> p1External.setSelected(true);
        }
        HBox p1Row = new HBox(18, p1Human, p1Ai, p1External);
        p1Row.setAlignment(Pos.CENTER_LEFT);

        // Player two
        Label p2Text = new Label("Player Two:");
        p2Text.getStyleClass().add("config-label");
        ToggleGroup p2Group = new ToggleGroup();
        RadioButton p2Human = new RadioButton("Human");
        RadioButton p2Ai = new RadioButton("AI");
        RadioButton p2External = new RadioButton("External");
        p2Human.setToggleGroup(p2Group);
        p2Ai.setToggleGroup(p2Group);
        p2External.setToggleGroup(p2Group);
        switch (cfg.player2) {
            case HUMAN -> p2Human.setSelected(true);
            case AI -> p2Ai.setSelected(true);
            case EXTERNAL -> p2External.setSelected(true);
        }
        boolean enableP2Init = extendChk.isSelected();
        p2Human.setDisable(!enableP2Init);
        p2Ai.setDisable(!enableP2Init);
        p2External.setDisable(!enableP2Init);
        HBox p2Row = new HBox(18, p2Human, p2Ai, p2External);
        p2Row.setAlignment(Pos.CENTER_LEFT);
        extendChk.selectedProperty().addListener((obs, ov, nv) -> {
            extendState.setText(nv ? "On" : "Off");
            p2Human.setDisable(!nv);
            p2Ai.setDisable(!nv);
            p2External.setDisable(!nv);
        });

        // Layout grid
        GridPane grid = new GridPane();
        grid.getStyleClass().add("form-grid");
        grid.setHgap(22);
        grid.setVgap(18);
        grid.setAlignment(Pos.CENTER);
        int r = 0;
        grid.add(widthLabel, 0, r);
        grid.add(widthSlider, 1, r);
        grid.add(widthValue, 2, r++);
        grid.add(heightLabel, 0, r);
        grid.add(heightSlider, 1, r);
        grid.add(heightValue, 2, r++);
        grid.add(levelLabel, 0, r);
        grid.add(levelSlider, 1, r);
        grid.add(levelValue, 2, r++);
        grid.add(musicText, 0, r);
        grid.add(musicChk, 1, r);
        grid.add(musicState, 2, r++);
        grid.add(sfxText, 0, r);
        grid.add(sfxChk, 1, r);
        grid.add(sfxState, 2, r++);
        grid.add(extendText, 0, r);
        grid.add(extendChk, 1, r);
        grid.add(extendState, 2, r++);
        grid.add(p1Text, 0, r);
        grid.add(p1Row, 1, r, 2, 1);
        r++;
        grid.add(p2Text, 0, r);
        grid.add(p2Row, 1, r, 2, 1);
        r++;

        // Buttons row
        Button saveAndReturnButton = new Button("Save & Return");
        saveAndReturnButton.getStyleClass().addAll("menu-btn");
        saveAndReturnButton.setPrefWidth(220);
        saveAndReturnButton.setOnAction(e -> {
            cfg.fieldWidth = (int) widthSlider.getValue();
            cfg.fieldHeight = (int) heightSlider.getValue();
            cfg.gameLevel = (int) levelSlider.getValue();
            cfg.musicOn = musicChk.isSelected();
            cfg.sfxOn = sfxChk.isSelected();
            cfg.extendedMode = extendChk.isSelected();
            if (p1Human.isSelected()) cfg.player1 = PlayerType.HUMAN;
            if (p1Ai.isSelected()) cfg.player1 = PlayerType.AI;
            if (p1External.isSelected()) cfg.player1 = PlayerType.EXTERNAL;
            if (p2Human.isSelected()) cfg.player2 = PlayerType.HUMAN;
            if (p2Ai.isSelected()) cfg.player2 = PlayerType.AI;
            if (p2External.isSelected()) cfg.player2 = PlayerType.EXTERNAL;
            configStore.save(cfg);
            goBackToMenu(stage);
        });

        Button resetButton = new Button("Reset Defaults");
        resetButton.getStyleClass().addAll("menu-btn");
        resetButton.setPrefWidth(180);
        resetButton.setOnAction(e -> {
            AppConfig d = new AppConfig();
            widthSlider.setValue(clamp(d.fieldWidth, 5, 15));
            heightSlider.setValue(clamp(d.fieldHeight, 15, 30));
            levelSlider.setValue(clamp(d.gameLevel, 1, 10));
            musicChk.setSelected(d.musicOn);
            sfxChk.setSelected(d.sfxOn);
            extendChk.setSelected(d.extendedMode);
            widthValue.setText(String.valueOf((int) widthSlider.getValue()));
            heightValue.setText(String.valueOf((int) heightSlider.getValue()));
            levelValue.setText(String.valueOf((int) levelSlider.getValue()));
            musicState.setText(musicChk.isSelected() ? "On" : "Off");
            sfxState.setText(sfxChk.isSelected() ? "On" : "Off");
            extendState.setText(extendChk.isSelected() ? "On" : "Off");
            p1Human.setSelected(d.player1 == PlayerType.HUMAN);
            p1Ai.setSelected(d.player1 == PlayerType.AI);
            p1External.setSelected(d.player1 == PlayerType.EXTERNAL);
            p2Human.setSelected(d.player2 == PlayerType.HUMAN);
            p2Ai.setSelected(d.player2 == PlayerType.AI);
            p2External.setSelected(d.player2 == PlayerType.EXTERNAL);
            boolean nv = extendChk.isSelected();
            p2Human.setDisable(!nv);
            p2Ai.setDisable(!nv);
            p2External.setDisable(!nv);
        });

        HBox buttonRow = new HBox(18, saveAndReturnButton, resetButton);
        buttonRow.setAlignment(Pos.CENTER);

        VBox card = new VBox(20, title, grid, buttonRow);
        card.getStyleClass().add("menu-card");
        card.setAlignment(Pos.TOP_CENTER);
        card.setMaxWidth(900);

        StackPane root = new StackPane(card);
        root.getStyleClass().add("app-bg");
        root.getStyleClass().add("screen--config");

        Scene scene = new Scene(root, 900, 640);
        applyTheme(scene);
        stage.setScene(scene);
        stage.show();
    }

    // Quit app
    @FXML
    protected void onQuit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Are you sure you want to quit?");
        alert.setContentText("Choose 'OK' to exit or 'Cancel' to return to the menu.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) System.exit(0);
        });
    }

    // Navigation
    void goBackToMenu(Stage stage) {
        try {
            var loader = new javafx.fxml.FXMLLoader(
                    HelloApplication.class.getResource("main-menu.fxml")
            );
            Scene scene = new Scene(loader.load(), 900, 640);
            applyTheme(scene);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Clamp helper
    private static int clamp(int v, int lo, int hi) {
        return Math.max(lo, Math.min(hi, v));
    }
}