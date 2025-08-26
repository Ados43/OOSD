package org.oosd.teamorange_milestoneone.application.views.splash;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Objects;

public class SplashView extends Application {

    @Override
    public void start(Stage stage) {

        // Setup splash screen
        Stage splashStage = new Stage();
        
        // Load and create image
        ImageView splashImage = new ImageView(
            new Image(
                Objects.requireNonNull(
                    getClass().getResourceAsStream("/org/oosd/teamorange_milestoneone/splash.png")
                )
            )
        );
        
        // Configure image properties
        splashImage.setPreserveRatio(true);
        splashImage.setSmooth(true);
        splashImage.setCache(true);

        // Center image in stack pane
        javafx.scene.layout.StackPane splashRoot = new javafx.scene.layout.StackPane(splashImage);
        Scene splashScene = new Scene(splashRoot, 600, 400);

        // Dynamically bind image and scene sizes
        splashImage.fitWidthProperty().bind(splashScene.widthProperty());
        splashImage.fitHeightProperty().bind(splashScene.heightProperty());

        // Show screen
        splashStage.setScene(splashScene);
        splashStage.show();

        // Pause for 5 seconds
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(5));
        pause.setOnFinished(e -> splashStage.close());

        // Start countdown
        pause.play();
    }
}