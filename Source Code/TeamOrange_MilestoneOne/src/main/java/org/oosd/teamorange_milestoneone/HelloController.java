package org.oosd.teamorange_milestoneone;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

public class HelloController {

    @FXML
    protected void onPlayTetris() {
        TetrisGame game = new TetrisGame();
        Parent tetrisRoot = game.createContent();

        // Get current stage
        Stage stage = (Stage) playButton.getScene().getWindow();

        // Set the onBack action to return to the main menu
        game.setOnBack(() -> goBackToMenu(stage));

        // Show Tetris scene
        stage.setScene(new Scene(tetrisRoot, 800, 600));
        stage.show();
    }


    @FXML
    private Button playButton, settingsButton, highScoreButton, quitButton;

    @FXML
    protected void onSettings() {
        Stage stage = (Stage) settingsButton.getScene().getWindow();
        VBox settingsRoot = new VBox(20, new Label("Settings Screen"),
                new Button("Back"));
        ((Button) settingsRoot.getChildren().get(1)).setOnAction(e -> goBackToMenu(stage));
        stage.setScene(new Scene(settingsRoot, 800, 600));
    }

    @FXML
    protected void onHighScore() {
        Stage stage = (Stage) highScoreButton.getScene().getWindow();
        VBox scoreRoot = new VBox(20, new Label("High Score Screen"),
                new Button("Back"));
        ((Button) scoreRoot.getChildren().get(1)).setOnAction(e -> goBackToMenu(stage));
        stage.setScene(new Scene(scoreRoot, 800, 600));
    }

    @FXML
    protected void onQuit() {
        System.exit(0);
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
}
