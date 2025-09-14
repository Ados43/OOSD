package org.oosd.teamorange_milestoneone.tetris.records;

public record Position(int x, int y) {
    public Position add(Position other) {
        return new Position(x + other.x, y + other.y);
    }
}
