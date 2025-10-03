/// AppConfig.java
// Configuration model for the app which holds the user’s
// chosen values and are loaded/saved by ConfigStore.java

package org.oosd.teamorange_finalsubmission;

public class AppConfig {
    // Defaults
    public int fieldWidth = 10;
    public int fieldHeight = 20;
    public int gameLevel = 1;
    public boolean musicOn = true;
    public boolean sfxOn = true;
    public boolean extendedMode = false;
    public PlayerType player1 = PlayerType.HUMAN;
    public PlayerType player2 = PlayerType.HUMAN;

    // Constructors
    public AppConfig() {
    }
}