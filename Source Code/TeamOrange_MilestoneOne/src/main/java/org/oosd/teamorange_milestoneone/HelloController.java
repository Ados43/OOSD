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

public class HelloController {

    // References to main menu buttons from FXML
    @FXML
    private Button playButton, configurationButton, highScoreButton;

    @FXML
    protected void onPlayTetris() {
        // Create a new Tetris game instance and its root node
        TetrisGame game = new TetrisGame();
        Parent tetrisRoot = game.createContent();

        // Get reference to the current stage (main window)
        Stage stage = (Stage) playButton.getScene().getWindow();

        // Allow returning to the main menu via game’s back action
        game.setOnBack(() -> goBackToMenu(stage));

        // Set Tetris scene in the stage
        stage.setScene(new Scene(tetrisRoot, 800, 600));
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

        // Create a table for player scores
        javafx.scene.control.TableView<String[]> table = new javafx.scene.control.TableView<>();
        table.setColumnResizePolicy(javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMaxWidth(400);

        // Define table columns: Player and Score
        javafx.scene.control.TableColumn<String[], String> nameCol = new javafx.scene.control.TableColumn<>("Player");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[0]));
        javafx.scene.control.TableColumn<String[], String> scoreCol = new javafx.scene.control.TableColumn<>("Score");
        scoreCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[1]));
        table.getColumns().setAll(nameCol, scoreCol);

        // Dummy high score data
        table.getItems().addAll(
                new String[]{"Aidan", "1200"},
                new String[]{"Fletcher", "1100"},
                new String[]{"Bernie", "1000"},
                new String[]{"Sam", "950"},
                new String[]{"Alex", "900"},
                new String[]{"Jordan", "850"},
                new String[]{"Chris", "800"},
                new String[]{"Taylor", "750"},
                new String[]{"Jamie", "700"},
                new String[]{"Morgan", "650"}
        );

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