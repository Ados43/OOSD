/// HelloApplication.java
// JavaFX entry point. Shows a splash image briefly, then loads main-menu.fxml
// Applies the global CSS theme to keep visuals consistent.
// No game logic is here. This class only bootstraps the UI.

package org.oosd.teamorange_finalsubmission;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

// App bootstrap
public class HelloApplication extends Application {

    // Start UI
    @Override
    public void start(Stage stage) {
        Stage splashStage = new Stage();
        javafx.scene.image.ImageView splashImage = new javafx.scene.image.ImageView(
                Objects.requireNonNull(HelloApplication.class.getResource("splash.png")).toExternalForm()
        );
        splashImage.setPreserveRatio(true);
        splashImage.setSmooth(true);
        splashImage.setCache(true);
        javafx.scene.layout.StackPane splashRoot = new javafx.scene.layout.StackPane(splashImage);
        Scene splashScene = new Scene(splashRoot, 600, 400);
        splashImage.fitWidthProperty().bind(splashScene.widthProperty());
        splashImage.fitHeightProperty().bind(splashScene.heightProperty());
        splashStage.setScene(splashScene);
        splashStage.show();

        // Delay then load menu
        PauseTransition pause = getPauseTransition(stage, splashStage);
        pause.play();
    }

    // Splash delay
    @NotNull
    private static PauseTransition getPauseTransition(Stage stage, Stage splashStage) {
        PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(3));
        pause.setOnFinished(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(
                        HelloApplication.class.getResource("main-menu.fxml")
                );
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                scene.getStylesheets().add(
                        Objects.requireNonNull(
                                HelloApplication.class.getResource("/theme/tetris-theme.css")
                        ).toExternalForm()
                );
                stage.setTitle("Game Hub");
                stage.setScene(scene);
                stage.show();
                splashStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        return pause;
    }
}
