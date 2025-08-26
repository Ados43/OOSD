package org.oosd.teamorange_milestoneone;

import org.oosd.teamorange_milestoneone.application.ApplicationController;

public class TetrisApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Setup 
        ApplicationController controller = new ApplicationController();

        // Load main menu FXML
        FXMLLoader fxmlLoader = new FXMLLoader( getResource("main-menu.fxml") );
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(scene);
        primaryStage.setUserData(controller);
        primaryStage.show();
    }
}
