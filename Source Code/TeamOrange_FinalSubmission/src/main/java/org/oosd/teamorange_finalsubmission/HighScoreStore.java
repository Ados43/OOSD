package org.oosd.teamorange_finalsubmission;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// High scores storage
public class HighScoreStore {
    // Gson setup
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type LIST_TYPE = new TypeToken<List<HighScoreEntry>>() {
    }.getType();
    private static final Path FILE = Paths.get("data", "highscores.json");

    // Ensure file exists
    private static void ensureParent() throws IOException {
        Files.createDirectories(FILE.getParent());
        if (!Files.exists(FILE)) {
            try (Writer w = Files.newBufferedWriter(FILE)) {
                GSON.toJson(new ArrayList<>(), LIST_TYPE, w);
            }
        }
    }

    // Load list
    private static synchronized List<HighScoreEntry> load() {
        try {
            ensureParent();
            try (Reader r = Files.newBufferedReader(FILE)) {
                List<HighScoreEntry> list = GSON.fromJson(r, LIST_TYPE);
                return list != null ? list : new ArrayList<>();
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    // Save list
    private static synchronized void save(List<HighScoreEntry> list) {
        try {
            ensureParent();
            try (Writer w = Files.newBufferedWriter(FILE)) {
                GSON.toJson(list, LIST_TYPE, w);
            }
        } catch (IOException ignored) {
        }
    }

    // Add with config
    public synchronized void add(String name, int score, String config) {
        List<HighScoreEntry> list = load();
        list.add(new HighScoreEntry(name, score, Instant.now().toEpochMilli(), config));
        list.sort(Comparator.comparingInt(HighScoreEntry::score).reversed()
                .thenComparingLong(HighScoreEntry::timestamp));
        if (list.size() > 10) list = list.subList(0, 10);
        save(list);
    }

    // Fetch top 10
    public synchronized List<HighScoreEntry> top10() {
        List<HighScoreEntry> list = load();
        list.sort(Comparator.comparingInt(HighScoreEntry::score).reversed()
                .thenComparingLong(HighScoreEntry::timestamp));
        return list.size() > 10 ? new ArrayList<>(list.subList(0, 10)) : list;
    }

    // Clear all
    public synchronized void clear() {
        save(new ArrayList<>());
    }

}
