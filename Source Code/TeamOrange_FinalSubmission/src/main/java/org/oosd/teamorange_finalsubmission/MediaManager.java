package org.oosd.teamorange_finalsubmission;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MediaManager {
    private final Map<String, Media> sfxMedia = new HashMap<>();
    private MediaPlayer bgPlayer;
    private boolean musicMuted = false;
    private boolean sfxMuted = false;

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
            bgPlayer.setMute(musicMuted);
            bgPlayer.setVolume(0.5);
        }
    }

    private void loadSound(String key, String path) {
        URL url = getClass().getResource(path);
        if (url != null) {
            sfxMedia.put(key, new Media(url.toExternalForm()));
        }
    }


    // --- Background music control ---
    public void playBgMusic() {
        if (bgPlayer != null && !musicMuted) bgPlayer.play();
    }

    public void stopBgMusic() {
        if (bgPlayer != null) bgPlayer.stop();
    }

    // --- SFX ---
    public void playSound(String key) {
        if (sfxMuted) return;
        Media media = sfxMedia.get(key);
        if (media == null) return;

        MediaPlayer mp = new MediaPlayer(media); // fresh instance => overlapping allowed
        mp.setOnEndOfMedia(() -> {
            mp.dispose();                        // free resources after playback
        });
        mp.play();
    }


    // --- Mute state setters/getters ---
    public void setMusicMuted(boolean muted) {
        musicMuted = muted;
        if (bgPlayer != null) {
            bgPlayer.setMute(muted);
            if (muted) bgPlayer.pause();
        }
    }

    public void setSfxMuted(boolean muted) {
        sfxMuted = muted;
    }

    public boolean isMusicMuted() {
        return musicMuted;
    }

    public boolean isSfxMuted() {
        return sfxMuted;
    }

    // Optional convenience for in-game toggles:
    public void toggleMusic() {
        setMusicMuted(!musicMuted);
        if (!musicMuted) playBgMusic();
        else stopBgMusic();
    }

    public void toggleSfx() {
        setSfxMuted(!sfxMuted);
    }
}
