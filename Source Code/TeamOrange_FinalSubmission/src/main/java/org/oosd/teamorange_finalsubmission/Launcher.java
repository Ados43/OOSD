/// Launcher.java
// Launcher of the application.
// Build/runtime setups prefer this separate launcher class.

package org.oosd.teamorange_finalsubmission;

import javafx.application.Application;

// App entrypoint
public class Launcher {
    public static void main(String[] args) {
        Application.launch(HelloApplication.class, args);
    }
}
