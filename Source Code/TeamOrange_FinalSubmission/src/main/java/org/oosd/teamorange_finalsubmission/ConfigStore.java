package org.oosd.teamorange_finalsubmission;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

// App config persistence
public class ConfigStore {

    // Paths
    private static final String DATA_DIR = "data";
    private static final String CONFIG_FN = "appConfig.json";

    // Resolve path
    private Path configPath() {
        return Path.of(DATA_DIR, CONFIG_FN);
    }

    // Ensure directory
    private void ensureDataDir() {
        try {
            Files.createDirectories(Path.of(DATA_DIR));
        } catch (IOException ignored) {
        }
    }

    // Load config
    public AppConfig load() {
        ensureDataDir();
        Path p = configPath();
        AppConfig cfg = new AppConfig();
        if (!Files.exists(p)) {
            return cfg;
        }
        try {
            String json = Files.readString(p, StandardCharsets.UTF_8);
            cfg.fieldWidth = getInt(json, "fieldWidth", cfg.fieldWidth);
            cfg.fieldHeight = getInt(json, "fieldHeight", cfg.fieldHeight);
            cfg.gameLevel = getInt(json, "gameLevel", cfg.gameLevel);
            cfg.musicOn = getBool(json, "musicOn", cfg.musicOn);
            cfg.sfxOn = getBool(json, "sfxOn", cfg.sfxOn);
            cfg.extendedMode = getBool(json, "extendedMode", cfg.extendedMode);
            cfg.player1 = getEnum(json, "player1", cfg.player1);
            cfg.player2 = getEnum(json, "player2", cfg.player2);
        } catch (Exception ignored) {
        }
        return cfg;
    }

    // Save config
    public void save(AppConfig cfg) {
        ensureDataDir();
        String json =
                "{\n" +
                        "  \"fieldWidth\": " + cfg.fieldWidth + ",\n" +
                        "  \"fieldHeight\": " + cfg.fieldHeight + ",\n" +
                        "  \"gameLevel\": " + cfg.gameLevel + ",\n" +
                        "  \"musicOn\": " + cfg.musicOn + ",\n" +
                        "  \"sfxOn\": " + cfg.sfxOn + ",\n" +
                        "  \"extendedMode\": " + cfg.extendedMode + ",\n" +
                        "  \"player1\": \"" + cfg.player1.name() + "\",\n" +
                        "  \"player2\": \"" + cfg.player2.name() + "\"\n" +
                        "}\n";
        try {
            Files.writeString(configPath(), json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get int
    private static int getInt(String json, String key, int def) {
        String v = extractRaw(json, key);
        if (v == null) return def;
        try {
            return Integer.parseInt(stripQuotes(v));
        } catch (NumberFormatException e) {
            return def;
        }
    }

    // Get boolean
    private static boolean getBool(String json, String key, boolean def) {
        String v = extractRaw(json, key);
        if (v == null) return def;
        v = stripQuotes(v).toLowerCase();
        if ("true".equals(v)) return true;
        if ("false".equals(v)) return false;
        return def;
    }

    // Get enum
    private static <E extends Enum<E>> E getEnum(String json, String key, E def) {
        String v = extractRaw(json, key);
        if (v == null) return def;
        String cleaned = stripQuotes(v).trim();
        try {
            return Enum.valueOf((Class<E>) PlayerType.class, cleaned);
        } catch (IllegalArgumentException e) {
            return def;
        }
    }

    // Extract raw value
    private static String extractRaw(String json, String key) {
        String needle = "\"" + key + "\"";
        int i = json.indexOf(needle);
        if (i < 0) return null;
        int colon = json.indexOf(':', i + needle.length());
        if (colon < 0) return null;
        int j = colon + 1;
        while (j < json.length() && Character.isWhitespace(json.charAt(j))) j++;
        int k = j;
        boolean inString = false;
        while (k < json.length()) {
            char c = json.charAt(k);
            if (c == '"' && json.charAt(k - 1) != '\\') inString = !inString;
            if (!inString && (c == ',' || c == '}')) break;
            k++;
        }
        if (k <= j) return null;
        return json.substring(j, k).trim();
    }

    // Strip quotes
    private static String stripQuotes(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
}
