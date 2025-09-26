package org.oosd.teamorange_finalsubmission;

public class AppConfig {
    // === Defaults ===
    public int fieldWidth = 10;     // 5..15 in UI
    public int fieldHeight = 20;     // 15..30 in UI
    public int gameLevel = 1;      // 1..10 in UI

    public boolean musicOn = true;
    public boolean sfxOn = true;
    public boolean extendedMode = false;

    public PlayerType player1 = PlayerType.HUMAN;
    public PlayerType player2 = PlayerType.HUMAN;

    // Convenience constructor for cloning/overrides if ever needed
    public AppConfig() {
    }

    public AppConfig(AppConfig other) {
        this.fieldWidth = other.fieldWidth;
        this.fieldHeight = other.fieldHeight;
        this.gameLevel = other.gameLevel;
        this.musicOn = other.musicOn;
        this.sfxOn = other.sfxOn;
        this.extendedMode = other.extendedMode;
        this.player1 = other.player1;
        this.player2 = other.player2;
    }
}
