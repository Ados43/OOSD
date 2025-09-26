package org.oosd.teamorange_finalsubmission;

import javafx.beans.property.SimpleStringProperty;

public class HighScoreRow {
    private final SimpleStringProperty rank;
    private final SimpleStringProperty name;
    private final SimpleStringProperty score;

    public HighScoreRow(int rank, String name, int score) {
        this.rank = new SimpleStringProperty(String.valueOf(rank));
        this.name = new SimpleStringProperty(name);
        this.score = new SimpleStringProperty(String.valueOf(score));
    }

    public SimpleStringProperty rankProperty() {
        return rank;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty scoreProperty() {
        return score;
    }
}
