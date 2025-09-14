package org.oosd.teamorange_milestoneone.system.controllers;

public class ConfigController {
    private final ViewModule viewModule;
    private GameConfig currentConfig;

    // UI Components for easy demo following
    private Slider difficultySlider;
    private CheckBox musicToggle;
    private CheckBox aiToggle;
    private Slider widthSlider;
    private Slider heightSlider;

    public ConfigController(ViewModule viewModule) {
        this.viewModule = viewModule;
    }

    /**
     * EXECUTIVE ACTION: Load current configuration
     * Demonstrates: File I/O, settings persistence
     */
    public void loadCurrentConfig(GameConfig config) {
        System.out.println("[EXECUTIVE] Config: Loading current settings");
        this.currentConfig = config;
        updateUIFromConfig();
    }

    /**
     * EXECUTIVE ACTION: Save configuration changes
     * Demonstrates: Data validation, file persistence
     */
    public void handleSaveConfig() {
        System.out.println("[EXECUTIVE] Config: Saving configuration changes");

        GameConfig newConfig = new GameConfig(
                (int) difficultySlider.getValue(),
                musicToggle.isSelected(),
                aiToggle.isSelected(),
                (int) widthSlider.getValue(),
                (int) heightSlider.getValue(),
                currentConfig.keyBindings()
        );

        viewModule.getSystemProcess().updateConfig(newConfig);
        viewModule.showMainMenu();
    }

    /**
     * EXECUTIVE ACTION: Reset to defaults
     * Demonstrates: Configuration management
     */
    public void handleResetDefaults() {
        System.out.println("[EXECUTIVE] Config: Resetting to default settings");
        loadCurrentConfig(GameConfig.defaultConfig());
    }

    /**
     * EXECUTIVE ACTION: Return to menu
     * Demonstrates: Navigation flow
     */
    public void handleBackToMenu() {
        System.out.println("[EXECUTIVE] Config -> Menu: Returning to main menu");
        viewModule.showMainMenu();
    }

    // UI binding methods for demo clarity
    public void bindSliders(Slider difficulty, Slider width, Slider height) {
        this.difficultySlider = difficulty;
        this.widthSlider = width;
        this.heightSlider = height;
    }

    public void bindToggles(CheckBox music, CheckBox ai) {
        this.musicToggle = music;
        this.aiToggle = ai;
    }

    private void updateUIFromConfig() { /* Update UI components from config */ }
}
