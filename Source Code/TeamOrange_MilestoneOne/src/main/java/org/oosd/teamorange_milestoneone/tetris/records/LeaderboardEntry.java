package org.oosd.teamorange_milestoneone.tetris.records;

public record LeaderboardEntry(String playerName, int score, int level, LocalDateTime dateAchieved)
        implements Comparable<LeaderboardEntry> {

    @Override
    public int compareTo(LeaderboardEntry other) {
        return Integer.compare(other.score, this.score); // Descending order
    }
}
