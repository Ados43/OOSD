package org.oosd.teamorange_milestoneone.application.views;

public class ConfigView {

//     //Configuration Menu
//     @FXML
//     protected void OnConfiguration() {
//         Stage stage = (Stage) configurationButton.getScene().getWindow();

//         // Title label
//         Label title = new Label("Configuration");
//         title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

//         // Sliders for game settings (field width, height, difficulty)
//         Label fieldWidthValue = new Label("10");
//         Slider fieldWidthSlider = new Slider(5, 15, 10);
//         fieldWidthSlider.setShowTickLabels(true);
//         fieldWidthSlider.setShowTickMarks(true);
//         Label fieldWidthLabel = new Label("Field Width (no. of cells):");
//         fieldWidthSlider.valueProperty().addListener((obs, oldVal, newVal) ->
//                 fieldWidthValue.setText(String.valueOf(newVal.intValue())));
//         Label fieldHeightValue = new Label("20");
//         Slider fieldHeightSlider = new Slider(15, 30, 20);
//         fieldHeightSlider.setShowTickLabels(true);
//         fieldHeightSlider.setShowTickMarks(true);
//         Label fieldHeightLabel = new Label("Field Height (no. of cells):");
//         fieldHeightSlider.valueProperty().addListener((obs, oldVal, newVal) ->
//                 fieldHeightValue.setText(String.valueOf(newVal.intValue())));
//         Label difficultyValue = new Label("5");
//         Slider difficultySlider = new Slider(1, 10, 5);
//         difficultySlider.setShowTickLabels(true);
//         difficultySlider.setShowTickMarks(true);
//         Label difficultyLabel = new Label("Game Difficulty:");
//         difficultySlider.valueProperty().addListener((obs, oldVal, newVal) ->
//                 difficultyValue.setText(String.valueOf(newVal.intValue())));

//         // Checkboxes (toggles) for music, sound effects, AI, and extended mode
//         Label musicValue = new Label("ON");
//         CheckBox music = new CheckBox("Music");
//         music.setSelected(true);
//         music.selectedProperty().addListener((obs, oldVal, newVal) ->
//                 musicValue.setText(newVal ? "ON" : "OFF"));
//         Label sfxValue = new Label("ON");
//         CheckBox sfx = new CheckBox("Sound Effects");
//         sfx.setSelected(true);
//         sfx.selectedProperty().addListener((obs, oldVal, newVal) ->
//                 sfxValue.setText(newVal ? "ON" : "OFF"));
//         Label aiValue = new Label("OFF");
//         CheckBox aiPlay = new CheckBox("AI Play");
//         aiPlay.selectedProperty().addListener((obs, oldVal, newVal) ->
//                 aiValue.setText(newVal ? "ON" : "OFF"));
//         Label extendedValue = new Label("OFF");
//         CheckBox extendedMode = new CheckBox("Extended Mode");
//         extendedMode.selectedProperty().addListener((obs, oldVal, newVal) ->
//                 extendedValue.setText(newVal ? "ON" : "OFF"));

//         // Back button to return to main menu
//         Button backButton = new Button("Back");
//         backButton.setStyle("-fx-font-size: 16px; -fx-padding: 8 16;");
//         backButton.setOnAction(e -> goBackToMenu(stage));

//         // Grid layout for sliders and toggles
//         GridPane grid = new GridPane();
//         grid.setVgap(15);
//         grid.setHgap(20);
//         grid.setAlignment(Pos.CENTER);

//         // Add sliders + values
//         grid.add(fieldWidthLabel, 0, 0);
//         grid.add(fieldWidthSlider, 1, 0);
//         grid.add(fieldWidthValue, 2, 0);
//         grid.add(fieldHeightLabel, 0, 1);
//         grid.add(fieldHeightSlider, 1, 1);
//         grid.add(fieldHeightValue, 2, 1);
//         grid.add(difficultyLabel, 0, 2);
//         grid.add(difficultySlider, 1, 2);
//         grid.add(difficultyValue, 2, 2);

//         // Add toggles
//         grid.add(music, 0, 3);
//         grid.add(musicValue, 2, 3);
//         grid.add(sfx, 0, 4);
//         grid.add(sfxValue, 2, 4);
//         grid.add(aiPlay, 0, 5);
//         grid.add(aiValue, 2, 5);
//         grid.add(extendedMode, 0, 6);
//         grid.add(extendedValue, 2, 6);

//         // Put everything inside a VBox and set in BorderPane
//         VBox settingsBox = new VBox(30, title, grid, backButton);
//         settingsBox.setAlignment(Pos.CENTER);
//         settingsBox.setStyle("-fx-padding: 30; -fx-font-size: 16px;");
//         BorderPane root = new BorderPane();
//         root.setCenter(settingsBox);

//         // Show configuration scene
//         stage.setScene(new Scene(root, 800, 600));
//     }
    
}
