package org.oosd.teamorange_milestoneone.system;

import java.util.Objects;

public class IO {
    private static final String CONFIG_FILE = "tetris_config.json";
    private static final String LEADERBOARD_FILE = "leaderboard.json";

    private final ObjectMapper jsonMapper;
    private final Map<KeyCode, Runnable> keyBindings;
    private final AudioManager audioManager;

    public IO() {
        this.jsonMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        this.keyBindings = new HashMap<>();
        this.audioManager = new AudioManager();
    }

    // Configuration Management
    public GameConfig loadConfig() {
        try {
            File configFile = new File(CONFIG_FILE);
            if (configFile.exists()) {
                return jsonMapper.readValue(configFile, GameConfig.class);
            }
        } catch (Exception e) {
            System.err.println("Failed to load config: " + e.getMessage());
        }
        return GameConfig.defaultConfig();
    }

    public void saveConfig(GameConfig config) {
        try {
            jsonMapper.writeValue(new File(CONFIG_FILE), config);
            System.out.println("Configuration saved successfully");
        } catch (Exception e) {
            System.err.println("Failed to save config: " + e.getMessage());
        }
    }

    // Leaderboard Management
    public List<LeaderboardEntry> loadLeaderboard() {
        try {
            File leaderboardFile = new File(LEADERBOARD_FILE);
            if (leaderboardFile.exists()) {
                return jsonMapper.readValue(leaderboardFile,
                        new TypeReference<List<LeaderboardEntry>>() {});
            }
        } catch (Exception e) {
            System.err.println("Failed to load leaderboard: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void saveLeaderboard(List<LeaderboardEntry> entries) {
        try {
            // Keep only top 10 scores
            List<LeaderboardEntry> topScores = entries.stream()
                    .sorted()
                    .limit(10)
                    .collect(Collectors.toList());

            jsonMapper.writeValue(new File(LEADERBOARD_FILE), topScores);
            System.out.println("Leaderboard saved successfully");
        } catch (Exception e) {
            System.err.println("Failed to save leaderboard: " + e.getMessage());
        }
    }

    public boolean isNewHighScore(int score) {
        List<LeaderboardEntry> entries = loadLeaderboard();
        return entries.size() < 10 || entries.get(entries.size() - 1).score() < score;
    }

    // Input Handling
    public void setupInputHandling(Game game) {
        keyBindings.clear();
        keyBindings.put(KeyCode.LEFT, () -> safeExecute(() -> {
            game.moveLeft();
            audioManager.playSound("move");
        }));
        keyBindings.put(KeyCode.RIGHT, () -> safeExecute(() -> {
            game.moveRight();
            audioManager.playSound("move");
        }));
        keyBindings.put(KeyCode.UP, () -> safeExecute(() -> {
            game.rotate();
            audioManager.playSound("rotate");
        }));
        keyBindings.put(KeyCode.DOWN, () -> {
            game.softDrop();
            audioManager.playSound("drop");
        });
        keyBindings.put(KeyCode.SPACE, () -> {
            game.hardDrop();
            audioManager.playSound("hard_drop");
        });
        keyBindings.put(KeyCode.P, () -> {
            game.togglePause();
            audioManager.playSound("button");
        });
    }

    public void handleKeyPress(KeyEvent event) {
        Runnable action = keyBindings.get(event.getCode());
        if (action != null) {
            action.run();
        }
    }

    private void safeExecute(ThrowingRunnable action) {
        try { action.run(); }
        catch (Exception e) {
            System.err.println("Input action failed: " + e.getMessage());
        }
    }

    @FunctionalInterface
    private interface ThrowingRunnable {
        void run() throws Exception;
    }
}

// Audio Manager for sound effects
class AudioManager {
    private final Map<String, MediaPlayer> soundEffects;
    private boolean soundEnabled = true;

    public AudioManager() {
        this.soundEffects = new HashMap<>();
        loadSoundEffects();
    }

    private void loadSoundEffects() {
        try {
            loadSound("move", "/sounds/move.wav");
            loadSound("rotate", "/sounds/rotate.wav");
            loadSound("drop", "/sounds/drop.wav");
            loadSound("hard_drop", "/sounds/hard_drop.wav");
            loadSound("line_clear", "/sounds/line_clear.wav");
            loadSound("button", "/sounds/button.wav");
            loadSound("game_over", "/sounds/game_over.wav");
        } catch (Exception e) {
            System.err.println("Failed to load sound effects: " + e.getMessage());
        }
    }

    private void loadSound(String name, String path) {
        try {
            Media sound = new Media(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
            soundEffects.put(name, new MediaPlayer(sound));
        } catch (Exception e) {
            System.err.println("Failed to load sound: " + name);
        }
    }

    public void playSound(String name) {
        if (!soundEnabled) return;

        MediaPlayer player = soundEffects.get(name);
        if (player != null) {
            player.stop();
            player.play();
        }
    }

    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
    }
}
