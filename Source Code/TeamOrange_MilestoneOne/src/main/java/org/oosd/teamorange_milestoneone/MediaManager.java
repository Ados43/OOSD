package org.oosd.teamorange_milestoneone;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MediaManager {
    private final Map<String, MediaPlayer> sounds = new HashMap<>();
    private MediaPlayer bgPlayer;
    private boolean musicMuted = false;
    private boolean sfxMuted = false;

    public void setSfxMuted(boolean muted) {
        sfxMuted = muted;
    }


    public MediaManager() {
        loadSound("drop", "/sounds/drop.mp3");
        loadSound("line_clear", "/sounds/line_clear.mp3");
        loadSound("tetris_clear", "/sounds/tetris_clear.mp3");
        loadSound("game_over", "/sounds/game_over.mp3");
        URL bgUrl = getClass().getResource("/sounds/bg_music.mp3");
        if (bgUrl != null) {
            Media media = new Media(bgUrl.toExternalForm());
            bgPlayer = new MediaPlayer(media);
            bgPlayer.setOnEndOfMedia(() -> bgPlayer.seek(Duration.ZERO)); // loop
        }
    }

    public void setMusicMuted(boolean muted) {
        musicMuted = muted;
        if (bgPlayer != null) {
            bgPlayer.setMute(muted);
            if (muted) {
                bgPlayer.pause();
            }
        }
    }


    private void loadSound(String key, String path) {
        URL url = getClass().getResource(path);
        if (url != null) {
            sounds.put(key, new MediaPlayer(new Media(url.toExternalForm())));
        }
    }

    public void playBgMusic() {
        if (bgPlayer != null && !musicMuted) {
            bgPlayer.setVolume(0.5);
            bgPlayer.play();
        }
    }

    public void stopBgMusic() {
        if (bgPlayer != null) bgPlayer.stop();
    }

    public void playSound(String key) {
        if (sfxMuted) return;
        MediaPlayer mp = sounds.get(key);
        if (mp != null) {
            mp.stop();
            mp.play();
        }
    }

    // --- mute toggles ---
    public void toggleMusicMute() {
        musicMuted = !musicMuted;
        if (bgPlayer != null) {
            bgPlayer.setMute(musicMuted);
            if (!musicMuted) {
                // If unmuting, resume music
                bgPlayer.play();
            } else {
                // If muting, stop music
                bgPlayer.pause();
            }
        }
    }


    public void toggleSfxMute() {
        sfxMuted = !sfxMuted;
    }

    public boolean isMusicMuted() {
        return musicMuted;
    }

    public boolean isSfxMuted() {
        return sfxMuted;
    }
}