/// HighScoreEntry.java
// Data model representing a single high score.
// Used for persistence and display in HighScoreView.

package org.oosd.teamorange_finalsubmission;

// High score record
public record HighScoreEntry(String name, int score, long timestamp, String config) {
}
