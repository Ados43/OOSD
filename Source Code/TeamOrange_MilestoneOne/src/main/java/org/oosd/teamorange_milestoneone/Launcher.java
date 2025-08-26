package org.oosd.teamorange_milestoneone;

import javafx.application.Application;
import org.oosd.teamorange_milestoneone.application.views.splash.SplashView;

public class Launcher {
    public static void main(String[] args) {

        // Display Splash Screen
        Application.launch(SplashView.class, args);

        // Display Main Application
        Application.launch(TetrisApp.class, args);

    }
}
