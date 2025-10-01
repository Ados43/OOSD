/// MediaManager.java
// Small helper around JavaFX Media/AudioClip for background music and sound effects.
// Supports muting/togglin.
// Plays short cues (drop, line clear, tetris, game over) triggered by TetrisGame.

package org.oosd.teamorange_finalsubmission;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

// Audio manager
public class MediaManager {
    // Fields
    private final Map<String, Media> sfxMedia = new HashMap<>();
    private MediaPlayer bgPlayer;
    private boolean musicMuted = false;
    private boolean sfxMuted = false;

    // Load audio
    public MediaManager() {
        loadSound("drop", "/sounds/drop.mp3");
        loadSound("line_clear", "/sounds/line_clear.mp3");
        loadSound("tetris_clear", "/sounds/tetris_clear.mp3");
        loadSound("game_over", "/sounds/game_over.mp3");
        URL bgUrl = getClass().getResource("/sounds/bg_music.mp3");
        if (bgUrl != null) {
            Media media = new Media(bgUrl.toExternalForm());
            bgPlayer = new MediaPlayer(media);
            bgPlayer.setOnEndOfMedia(() -> bgPlayer.seek(Duration.ZERO));
            bgPlayer.setMute(musicMuted);
            bgPlayer.setVolume(0.5);
        }
    }

    // Load sfx helper
    private void loadSound(String key, String path) {
        URL url = getClass().getResource(path);
        if (url != null) {
            sfxMedia.put(key, new Media(url.toExternalForm()));
        }
    }

    // Play bgm
    public void playBgMusic() {
        if (bgPlayer != null && !musicMuted) bgPlayer.play();
    }

    // Stop bgm
    public void stopBgMusic() {
        if (bgPlayer != null) bgPlayer.stop();
    }

    // Play sfx
    public void playSound(String key) {
        if (sfxMuted) return;
        Media media = sfxMedia.get(key);
        if (media == null) return;
        MediaPlayer mp = new MediaPlayer(media);
        mp.setOnEndOfMedia(mp::dispose);
        mp.play();
    }

    // Set mute flags
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

    // Toggle helpers
    public void toggleMusic() {
        setMusicMuted(!musicMuted);
        if (!musicMuted) playBgMusic();
        else stopBgMusic();
    }

    public void toggleSfx() {
        setSfxMuted(!sfxMuted);
    }
}
