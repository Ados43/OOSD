package org.oosd.teamorange_milestoneone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        // Create a separate splash stage (window) to show the splash screen image
        Stage splashStage = new Stage();
        javafx.scene.image.ImageView splashImage = new javafx.scene.image.ImageView(
                Objects.requireNonNull(HelloApplication.class.getResource("splash.png")).toExternalForm()
        );

        // Configure splash image properties (scaling, smoothing, caching)
        splashImage.setPreserveRatio(true);
        splashImage.setSmooth(true);
        splashImage.setCache(true);

        // Put splash image inside a StackPane so it is centered
        javafx.scene.layout.StackPane splashRoot = new javafx.scene.layout.StackPane(splashImage);
        Scene splashScene = new Scene(splashRoot, 600, 400);

        // Dynamically bind splash image size to scene size
        splashImage.fitWidthProperty().bind(splashScene.widthProperty());
        splashImage.fitHeightProperty().bind(splashScene.heightProperty());

        // Show splash screen
        splashStage.setScene(splashScene);
        splashStage.show();

        // Create a 3-second delay before switching to the main menu
        javafx.animation.PauseTransition pause =
                new javafx.animation.PauseTransition(javafx.util.Duration.seconds(3));

        // After pause, load the main menu and close the splash screen
        pause.setOnFinished(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(
                        HelloApplication.class.getResource("main-menu.fxml")
                );
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setTitle("Game Hub");
                stage.setScene(scene);
                stage.show();
                splashStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Start countdown for splash
        pause.play();
    }
}
