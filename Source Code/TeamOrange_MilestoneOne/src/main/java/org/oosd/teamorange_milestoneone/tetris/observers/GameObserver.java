package org.oosd.teamorange_milestoneone.tetris.observers;

public interface GameObserver {
    void onGameStateChanged(GameState oldState, GameState newState);
    void onGameStatsUpdated(GameStats stats);
}
