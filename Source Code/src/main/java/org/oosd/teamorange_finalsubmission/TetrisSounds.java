package org.oosd.teamorange_finalsubmission;

/**
 * TetrisSounds
 * ------------
 * Tiny helper to centralize which SFX to play based on clear count.
 * This is here purely to demonstrate Mockito in tests.
 */
public final class TetrisSounds {
    private TetrisSounds() {
    }

    public static void playForLines(MediaManager media, int linesCleared) {
        if (media == null) return;
        if (linesCleared > 0) {
            media.playSound(linesCleared >= 4 ? "tetris_clear" : "line_clear");
        } else {
            media.playSound("drop");
        }
    }
}
