package org.oosd.teamorange_milestoneone.tetris.records;

public record GameConfig(
        int difficulty,           // 1-10 speed multiplier
        boolean musicEnabled,
        boolean aiEnabled,
        int boardWidth,          // configurable width
        int boardHeight,         // configurable height
        Map<String, String> keyBindings
) {
    public static GameConfig defaultConfig() {
        Map<String, String> defaultKeys = Map.of(
                "LEFT", "A", "RIGHT", "D", "ROTATE", "W", "DROP", "S", "HARD_DROP", "SPACE"
        );
        return new GameConfig(5, true, false, 10, 20, defaultKeys);
    }
}
