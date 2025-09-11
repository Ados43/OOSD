package org.oosd.teamorange_milestoneone;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class HelloController {

    // Record type to store player high scores
    public record HighScore(String player, int score) {
    }

    // References to main menu buttons from FXML
    @FXML
    private Button playButton, configurationButton, highScoreButton;

    private final HighScoreStore highScoreStore = new HighScoreStore();

    @FXML
    protected void onPlayTetris() {
        TetrisGame game = new TetrisGame();
        game.setHighScoreStore(highScoreStore); // <-- pass store

        Parent tetrisRoot = game.createContent();
        Stage stage = (Stage) playButton.getScene().getWindow();
        game.setOnBack(() -> goBackToMenu(stage));

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


    //Configuration Menu
    @FXML
    protected void OnConfiguration() {
        Stage stage = (Stage) configurationButton.getScene().getWindow();

        // Title label
        Label title = new Label("Configuration");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        // Sliders for game settings (field width, height, difficulty)
        Label fieldWidthValue = new Label("10");
        Slider fieldWidthSlider = new Slider(5, 15, 10);
        fieldWidthSlider.setShowTickLabels(true);
        fieldWidthSlider.setShowTickMarks(true);
        Label fieldWidthLabel = new Label("Field Width (no. of cells):");
        fieldWidthSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                fieldWidthValue.setText(String.valueOf(newVal.intValue())));
        Label fieldHeightValue = new Label("20");
        Slider fieldHeightSlider = new Slider(15, 30, 20);
        fieldHeightSlider.setShowTickLabels(true);
        fieldHeightSlider.setShowTickMarks(true);
        Label fieldHeightLabel = new Label("Field Height (no. of cells):");
        fieldHeightSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                fieldHeightValue.setText(String.valueOf(newVal.intValue())));
        Label difficultyValue = new Label("5");
        Slider difficultySlider = new Slider(1, 10, 5);
        difficultySlider.setShowTickLabels(true);
        difficultySlider.setShowTickMarks(true);
        Label difficultyLabel = new Label("Game Difficulty:");
        difficultySlider.valueProperty().addListener((obs, oldVal, newVal) ->
                difficultyValue.setText(String.valueOf(newVal.intValue())));

        // Checkboxes (toggles) for music, sound effects, AI, and extended mode
        Label musicValue = new Label("ON");
        CheckBox music = new CheckBox("Music");
        music.setSelected(true);
        music.selectedProperty().addListener((obs, oldVal, newVal) ->
                musicValue.setText(newVal ? "ON" : "OFF"));
        Label sfxValue = new Label("ON");
        CheckBox sfx = new CheckBox("Sound Effects");
        sfx.setSelected(true);
        sfx.selectedProperty().addListener((obs, oldVal, newVal) ->
                sfxValue.setText(newVal ? "ON" : "OFF"));
        Label aiValue = new Label("OFF");
        CheckBox aiPlay = new CheckBox("AI Play");
        aiPlay.selectedProperty().addListener((obs, oldVal, newVal) ->
                aiValue.setText(newVal ? "ON" : "OFF"));
        Label extendedValue = new Label("OFF");
        CheckBox extendedMode = new CheckBox("Extended Mode");
        extendedMode.selectedProperty().addListener((obs, oldVal, newVal) ->
                extendedValue.setText(newVal ? "ON" : "OFF"));

        // Back button to return to main menu
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 16px; -fx-padding: 8 16;");
        backButton.setOnAction(e -> goBackToMenu(stage));

        // Grid layout for sliders and toggles
        GridPane grid = new GridPane();
        grid.setVgap(15);
        grid.setHgap(20);
        grid.setAlignment(Pos.CENTER);

        // Add sliders + values
        grid.add(fieldWidthLabel, 0, 0);
        grid.add(fieldWidthSlider, 1, 0);
        grid.add(fieldWidthValue, 2, 0);
        grid.add(fieldHeightLabel, 0, 1);
        grid.add(fieldHeightSlider, 1, 1);
        grid.add(fieldHeightValue, 2, 1);
        grid.add(difficultyLabel, 0, 2);
        grid.add(difficultySlider, 1, 2);
        grid.add(difficultyValue, 2, 2);

        // Add toggles
        grid.add(music, 0, 3);
        grid.add(musicValue, 2, 3);
        grid.add(sfx, 0, 4);
        grid.add(sfxValue, 2, 4);
        grid.add(aiPlay, 0, 5);
        grid.add(aiValue, 2, 5);
        grid.add(extendedMode, 0, 6);
        grid.add(extendedValue, 2, 6);

        // Put everything inside a VBox and set in BorderPane
        VBox settingsBox = new VBox(30, title, grid, backButton);
        settingsBox.setAlignment(Pos.CENTER);
        settingsBox.setStyle("-fx-padding: 30; -fx-font-size: 16px;");
        BorderPane root = new BorderPane();
        root.setCenter(settingsBox);

        // Show configuration scene
        stage.setScene(new Scene(root, 800, 600));
    }

    //High Score Menu
    @FXML
    protected void onHighScore() {
        Stage stage = (Stage) highScoreButton.getScene().getWindow();

        // Create a table for HighScore records
        TableView<HighScore> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMaxWidth(400);

        // Define columns
        TableColumn<HighScore, String> nameCol = new TableColumn<>("Player");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().player()));
        TableColumn<HighScore, String> scoreCol = new TableColumn<>("Score");
        scoreCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().score())));

        table.getColumns().setAll(nameCol, scoreCol);

        // Add dummy high score data using records
        List<HighScore> scores = List.of(
                new HighScore("Aidan", 1200),
                new HighScore("Fletcher", 1100),
                new HighScore("Bernie", 1000),
                new HighScore("Sam", 950),
                new HighScore("Alex", 900),
                new HighScore("Jordan", 850),
                new HighScore("Chris", 800),
                new HighScore("Taylor", 750),
                new HighScore("Jamie", 700),
                new HighScore("Morgan", 650)
        );
        table.getItems().addAll(scores);

        // Back button to return to menu
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> goBackToMenu(stage));

        // Layout for high score screen
        VBox scoreRoot = new VBox(20, new Label("High Scores"), table, backButton);
        scoreRoot.setAlignment(javafx.geometry.Pos.CENTER);
        scoreRoot.setStyle("-fx-padding: 20;");

        // Show high score scene
        stage.setScene(new Scene(scoreRoot, 800, 600));
    }

    //Quit Application
    @FXML
    protected void onQuit() {
        // Create a confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Are you sure you want to quit?");
        alert.setContentText("Choose 'OK' to exit or 'Cancel' to return to the menu.");

        // Show the alert and wait for the user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Exit the application if OK is pressed
                System.exit(0);
            }
            // If Cancel is pressed, do nothing (stay in menu)
        });
    }

    // Utility method to reload the main menu
    private void goBackToMenu(Stage stage) {
        try {
            javafx.fxml.FXMLLoader loader =
                    new javafx.fxml.FXMLLoader(HelloApplication.class.getResource("main-menu.fxml"));
            stage.setScene(new Scene(loader.load(), 800, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}