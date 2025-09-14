package org.oosd.teamorange_milestoneone.tetris;

public class Timer {
    private Timeline timeline;
    private final Runnable onTick;
    private double dropInterval; // milliseconds

    public Timer(Runnable onTick) {
        this.onTick = onTick;
        this.dropInterval = 1000; // 1 second default
    }

    public void start() { /* Start JavaFX Timeline */ }
    public void stop() { /* Stop timeline */ }
    public void pause() { /* Pause timeline */ }
    public void setDropInterval(double interval) { /* Update speed */ }
}
