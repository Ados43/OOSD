package org.oosd.teamorange_finalsubmission;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloController {

    public record HighScore(String player, int score) {
    }

    @FXML
    private Button playButton, configurationButton, highScoreButton;

    private final MediaManager mediaManager = new MediaManager();
    private final HighScoreStore highScoreStore = new HighScoreStore();
    private final ConfigStore configStore = new ConfigStore();

    // Cache of current, saved config (kept in sync with JSON)
    private AppConfig cfg = configStore.load();

    @FXML
    protected void onPlayTetris() {
        // Always refresh from disk before starting a game
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
        game.setExtendedMode(cfg.extendedMode);

        Parent tetrisRoot = game.createContent();
        Stage stage = (Stage) playButton.getScene().getWindow();

        game.setOnBack(() -> {
            mediaManager.stopBgMusic();
            goBackToMenu(stage);
        });

        stage.setScene(new Scene(tetrisRoot, 800, 600));
        stage.setMinWidth(640);
        stage.setMinHeight(480);
        stage.show();
    }

    @FXML
    protected void onShowHighScores() {
        Stage stage = (Stage) playButton.getScene().getWindow();
        Parent view = HighScoreView.create(highScoreStore, () -> goBackToMenu(stage));
        Scene scene = new Scene(view, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onConfiguration() {
        // Load latest from disk so the screen always reflects persisted values on open
        cfg = configStore.load();

        Stage stage = (Stage) configurationButton.getScene().getWindow();

        Label title = new Label("Configuration");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        // --- Sliders ---
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

        Label widthLabel = new Label("Field Width (No of cells):");
        Label heightLabel = new Label("Field Height (No of cells):");
        Label levelLabel = new Label("Game Level:");

        Label widthValue = new Label(String.valueOf((int) widthSlider.getValue()));
        Label heightValue = new Label(String.valueOf((int) heightSlider.getValue()));
        Label levelValue = new Label(String.valueOf((int) levelSlider.getValue()));

        widthSlider.valueProperty().addListener((obs, ov, nv) -> widthValue.setText(String.valueOf(nv.intValue())));
        heightSlider.valueProperty().addListener((obs, ov, nv) -> heightValue.setText(String.valueOf(nv.intValue())));
        levelSlider.valueProperty().addListener((obs, ov, nv) -> levelValue.setText(String.valueOf(nv.intValue())));

        // --- Toggles ---
        CheckBox musicChk = new CheckBox();
        musicChk.setSelected(cfg.musicOn);
        Label musicText = new Label("Music (On|Off):");
        Label musicState = new Label(cfg.musicOn ? "On" : "Off");
        musicChk.selectedProperty().addListener((obs, ov, nv) -> musicState.setText(nv ? "On" : "Off"));

        CheckBox sfxChk = new CheckBox();
        sfxChk.setSelected(cfg.sfxOn);
        Label sfxText = new Label("Sound Effect (On|Off):");
        Label sfxState = new Label(cfg.sfxOn ? "On" : "Off");
        sfxChk.selectedProperty().addListener((obs, ov, nv) -> sfxState.setText(nv ? "On" : "Off"));

        CheckBox extendChk = new CheckBox();
        extendChk.setSelected(cfg.extendedMode);
        Label extendText = new Label("Extend Mode (On|Off):");
        Label extendState = new Label(cfg.extendedMode ? "On" : "Off");

        // --- Player One ---
        Label p1Text = new Label("Player One Type:");
        ToggleGroup p1Group = new ToggleGroup();
        RadioButton p1Human = new RadioButton("Human");
        RadioButton p1Ai = new RadioButton("AI");
        RadioButton p1External = new RadioButton("External");
        p1Human.setToggleGroup(p1Group);
        p1Ai.setToggleGroup(p1Group);
        p1External.setToggleGroup(p1Group);
        switch (cfg.player1) {
            case HUMAN -> p1Human.setSelected(true);
            case AI -> p1Ai.setSelected(true);
            case EXTERNAL -> p1External.setSelected(true);
        }
        HBox p1Row = new HBox(20, p1Human, p1Ai, p1External);
        p1Row.setAlignment(Pos.CENTER_LEFT);

        // --- Player Two (enabled only if Extend Mode is ON) ---
        Label p2Text = new Label("Player Two Type:");
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
        HBox p2Row = new HBox(20, p2Human, p2Ai, p2External);
        p2Row.setAlignment(Pos.CENTER_LEFT);

        // keep state in sync while the user changes
        extendChk.selectedProperty().addListener((obs, ov, nv) -> {
            extendState.setText(nv ? "On" : "Off");
            p2Human.setDisable(!nv);
            p2Ai.setDisable(!nv);
            p2External.setDisable(!nv);
        });

        // --- Layout grid (label | control | value) ---
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(16);
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

        // --- Buttons row ---
        Button saveAndReturnButton = new Button("Save and return to main menu");
        saveAndReturnButton.setPrefWidth(240);
        saveAndReturnButton.setOnAction(e -> {
            // Commit UI values to cfg model
            cfg.fieldWidth = (int) widthSlider.getValue();
            cfg.fieldHeight = (int) heightSlider.getValue();
            cfg.gameLevel = (int) levelSlider.getValue();

            cfg.musicOn = musicChk.isSelected();
            cfg.sfxOn = sfxChk.isSelected();
            cfg.extendedMode = extendChk.isSelected();

            if (p1Human.isSelected()) cfg.player1 = PlayerType.HUMAN;
            if (p1Ai.isSelected()) cfg.player1 = PlayerType.AI;
            if (p1External.isSelected()) cfg.player1 = PlayerType.EXTERNAL;

            if (extendChk.isSelected()) {
                if (p2Human.isSelected()) cfg.player2 = PlayerType.HUMAN;
                if (p2Ai.isSelected()) cfg.player2 = PlayerType.AI;
                if (p2External.isSelected()) cfg.player2 = PlayerType.EXTERNAL;
            } else {
                // If extended mode is off, keep player2 selection but it won't be used.
                if (p2Human.isSelected()) cfg.player2 = PlayerType.HUMAN;
                if (p2Ai.isSelected()) cfg.player2 = PlayerType.AI;
                if (p2External.isSelected()) cfg.player2 = PlayerType.EXTERNAL;
            }

            // Persist to disk (single write)
            configStore.save(cfg);

            // Return to main menu
            goBackToMenu(stage);
        });

        Button resetButton = new Button("Reset to Defaults");
        resetButton.setPrefWidth(180);
        resetButton.setOnAction(e -> {
            // Pull defaults from AppConfig (ensure AppConfig.java has your desired defaults)
            AppConfig d = new AppConfig();

            // Update controls (NO SAVE here)
            widthSlider.setValue(clamp(d.fieldWidth, 5, 15));
            heightSlider.setValue(clamp(d.fieldHeight, 15, 30));
            levelSlider.setValue(clamp(d.gameLevel, 1, 10));

            musicChk.setSelected(d.musicOn);
            sfxChk.setSelected(d.sfxOn);
            extendChk.setSelected(d.extendedMode);

            // Update selection labels
            widthValue.setText(String.valueOf((int) widthSlider.getValue()));
            heightValue.setText(String.valueOf((int) heightSlider.getValue()));
            levelValue.setText(String.valueOf((int) levelSlider.getValue()));
            musicState.setText(musicChk.isSelected() ? "On" : "Off");
            sfxState.setText(sfxChk.isSelected() ? "On" : "Off");
            extendState.setText(extendChk.isSelected() ? "On" : "Off");

            // Player types
            p1Human.setSelected(d.player1 == PlayerType.HUMAN);
            p1Ai.setSelected(d.player1 == PlayerType.AI);
            p1External.setSelected(d.player1 == PlayerType.EXTERNAL);

            p2Human.setSelected(d.player2 == PlayerType.HUMAN);
            p2Ai.setSelected(d.player2 == PlayerType.AI);
            p2External.setSelected(d.player2 == PlayerType.EXTERNAL);

            // Enable/disable P2 row to match Extend Mode
            boolean nv = extendChk.isSelected();
            p2Human.setDisable(!nv);
            p2Ai.setDisable(!nv);
            p2External.setDisable(!nv);
        });

        HBox buttonRow = new HBox(20, saveAndReturnButton, resetButton);
        buttonRow.setAlignment(Pos.CENTER);

        Label footer = new Label("Authors: Aidan Lietz, Fletcher Carlton, Bernie Miall");
        footer.setStyle("-fx-text-fill: #555;");

        VBox rootBox = new VBox(24, title, grid, buttonRow, footer);
        rootBox.setAlignment(Pos.CENTER);
        rootBox.setStyle("-fx-padding: 20;");

        stage.setScene(new Scene(rootBox, 800, 600));
        stage.show();
    }

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

    private void goBackToMenu(Stage stage) {
        try {
            javafx.fxml.FXMLLoader loader =
                    new javafx.fxml.FXMLLoader(HelloApplication.class.getResource("main-menu.fxml"));
            stage.setScene(new Scene(loader.load(), 800, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int clamp(int v, int lo, int hi) {
        return Math.max(lo, Math.min(hi, v));
    }
}
