package org.oosd.teamorange_finalsubmission;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Tiny hand-rolled JSON persistence (no external libraries).
 * - Saves once on "Back" from Configuration.
 * - Loads on app/menu open.
 * - Falls back to sensible defaults if file is missing or malformed.
 * <p>
 * File: data/appConfig.json
 */
public class ConfigStore {

    private static final String DATA_DIR = "data";
    private static final String CONFIG_FN = "appConfig.json";

    private Path configPath() {
        return Path.of(DATA_DIR, CONFIG_FN);
    }

    private void ensureDataDir() {
        try {
            Files.createDirectories(Path.of(DATA_DIR));
        } catch (IOException ignored) {
        }
    }

    public AppConfig load() {
        ensureDataDir();
        Path p = configPath();
        AppConfig cfg = new AppConfig(); // defaults

        if (!Files.exists(p)) {
            return cfg; // first run, defaults in memory
        }

        try {
            String json = Files.readString(p, StandardCharsets.UTF_8);
            // Extremely small, robust-ish parsing (whitespace tolerant).
            cfg.fieldWidth = getInt(json, "fieldWidth", cfg.fieldWidth);
            cfg.fieldHeight = getInt(json, "fieldHeight", cfg.fieldHeight);
            cfg.gameLevel = getInt(json, "gameLevel", cfg.gameLevel);

            cfg.musicOn = getBool(json, "musicOn", cfg.musicOn);
            cfg.sfxOn = getBool(json, "sfxOn", cfg.sfxOn);
            cfg.extendedMode = getBool(json, "extendedMode", cfg.extendedMode);

            cfg.player1 = getEnum(json, "player1", PlayerType.class, cfg.player1);
            cfg.player2 = getEnum(json, "player2", PlayerType.class, cfg.player2);

        } catch (Exception ignored) {
            // Any failure: keep defaults silently.
        }
        return cfg;
    }

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
            // Non-fatal; if this fails we still keep in-memory settings
            e.printStackTrace();
        }
    }

    // ---- Minimal parsing helpers ----

    private static int getInt(String json, String key, int def) {
        String v = extractRaw(json, key);
        if (v == null) return def;
        try {
            return Integer.parseInt(stripQuotes(v));
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private static boolean getBool(String json, String key, boolean def) {
        String v = extractRaw(json, key);
        if (v == null) return def;
        v = stripQuotes(v).toLowerCase();
        if ("true".equals(v)) return true;
        if ("false".equals(v)) return false;
        return def;
    }

    private static <E extends Enum<E>> E getEnum(String json, String key, Class<E> enumType, E def) {
        String v = extractRaw(json, key);
        if (v == null) return def;
        String cleaned = stripQuotes(v).trim();
        try {
            return Enum.valueOf(enumType, cleaned);
        } catch (IllegalArgumentException e) {
            return def;
        }
    }

    // Extracts the raw value string after "key": (handles whitespace), up to a comma or end brace.
    private static String extractRaw(String json, String key) {
        // naive but sufficient for our tiny file format
        String needle = "\"" + key + "\"";
        int i = json.indexOf(needle);
        if (i < 0) return null;
        int colon = json.indexOf(':', i + needle.length());
        if (colon < 0) return null;

        int j = colon + 1;
        // skip whitespace
        while (j < json.length() && Character.isWhitespace(json.charAt(j))) j++;

        // read until comma or closing brace at same level
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

    private static String stripQuotes(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
}
