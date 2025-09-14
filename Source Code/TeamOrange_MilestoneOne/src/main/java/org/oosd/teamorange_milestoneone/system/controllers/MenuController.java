package org.oosd.teamorange_milestoneone.system.controllers;

public class MenuController {
    private final ViewModule viewModule;

    public MenuController(ViewModule viewModule) {
        this.viewModule = viewModule;
    }

    /**
     * EXECUTIVE ACTION: Start new game
     * Demonstrates: System process coordination, state management
     */
    public void handlePlay() {
        System.out.println("[EXECUTIVE] Menu -> Play: Initiating new game");
        viewModule.getSystemProcess().startGame();
    }

    /**
     * EXECUTIVE ACTION: Show leaderboard
     * Demonstrates: File I/O, data presentation
     */
    public void handleLeaderboard() {
        System.out.println("[EXECUTIVE] Menu -> Leaderboard: Loading high scores");
        viewModule.showLeaderboard();
    }

    /**
     * EXECUTIVE ACTION: Show configuration
     * Demonstrates: Settings management, user preferences
     */
    public void handleConfig() {
        System.out.println("[EXECUTIVE] Menu -> Config: Opening settings");
        viewModule.showConfig();
    }

    /**
     * EXECUTIVE ACTION: Exit application
     * Demonstrates: Cleanup, graceful shutdown
     */
    public void handleExit() {
        System.out.println("[EXECUTIVE] Menu -> Exit: Shutting down application");
        Platform.exit();
    }
}
