package org.oosd.teamorange_milestoneone;

import javafx.application.Application;
import javafx.stage.Stage;

public class TetrisApp extends Application {
    private SystemProcess systemProcess;
    private ViewModule viewModule;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Initialize system components
        this.systemProcess = new SystemProcess();
        this.viewModule = new ViewModule(primaryStage);

        // Connect system to view
        systemProcess.setViewModule(viewModule);
        viewModule.setSystemProcess(systemProcess);

        // Start system initialization
        systemProcess.initialize();

        primaryStage.setTitle("Tetris");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        if (systemProcess != null) {
            systemProcess.shutdown();
        }
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

