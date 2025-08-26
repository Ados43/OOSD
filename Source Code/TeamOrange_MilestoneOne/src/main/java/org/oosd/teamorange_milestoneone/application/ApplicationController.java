package org.oosd.teamorange_milestoneone.application;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

import org.oosd.teamorange_milestoneone.application.game.GameController;
import org.oosd.teamorange_milestoneone.application.system.KeyInputHandler;

import static org.oosd.teamorange_milestoneone.application.game.GameRender.createGameCanvas;

public class ApplicationController {

    public GameController gameController = new GameController();
    public KeyInputHandler input = new KeyInputHandler();

    public Parent playGame(ApplicationController appController) {

        Canvas gameCanvas = createGameCanvas(gameController);
        GraphicsContext graphicsContext = gameCanvas.getGraphicsContext2D();
        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(input);
        gameCanvas.setOnKeyReleased(input);

        Canvas nextCanvas = createGameCanvas(gameController);
        GraphicsContext nextGraphicsContext = nextCanvas.getGraphicsContext2D();

        // set up game controller
        gameController.setGameCanvas(gameCanvas);
        gameController.setNextCanvas(nextCanvas);

        Button backButton = new Button("Back");

        backButton.setOnAction(e -> {

            int pauseCondition = 0;
            if (!gameController.paused) { // If not paused, pause the game
                gameController.pause();
                pauseCondition++;
            } 

            Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                    "Your current progress will be lost if you return to the main menu. Are you sure?",
                    ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> res = a.showAndWait();

            if (res.isPresent() && res.get() == ButtonType.YES) {
                if (onBack != null) onBack.run();

            } else {
                if (pauseCondition > 0) gameController.unpause(); // Unpause if it was paused by the back button
            }
        });

        backButton.setFocusTraversable(false);
        HBox root = new HBox(20, gameCanvas, nextCanvas);
        root.setAlignment(Pos.CENTER);

        // UI components for game board and next piece preview
        StackPane rootWrapper = new StackPane(root, backButton);
        StackPane.setAlignment(backButton, Pos.TOP_LEFT);

        backButton.setTranslateX(10);
        backButton.setTranslateY(10);

        rootWrapper.setPrefSize(gameController.getWidth() + (int) nextCanvas.getWidth() + 60,
                gameController.getHeight() + 40);

        rootWrapper.setAlignment(Pos.CENTER);

        gameController.newBoard();

        nameField = new TextField();
        nameField.setPromptText("Enter your name");
        VBox.setMaxWidth(120);

        Button submitHS = new Button("Submit");
        submitHS.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                System.out.println("Highscore submit: " + name + " Score: " + score);
                highScorePane.setVisible(false);
                gameController.setHighScoreSubmitted(true); // <-- mark as submitted
            }
        });

        highScorePane = new VBox(10, nameField, submitHS);
        highScorePane.setAlignment(Pos.CENTER);
        highScorePane.setMaxWidth(200);
        highScorePane.setVisible(false);
        rootWrapper.getChildren().add(highScorePane);

        spawnNewGamePieces();

        rootWrapper.sceneProperty().addListener((obs, oldS, newS) -> {
            if (newS != null) gameCanvas.requestFocus();
        });

        startGameLoop(appController, graphicsContext);
        return rootWrapper;

    }

    public void setOnBack(Runnable onBack) {
        this.onBack = onBack;
    }
}
