package org.oosd.teamorange_finalsubmission;

import javafx.beans.property.SimpleStringProperty;

// Table row model
public class HighScoreRow {
    private final SimpleStringProperty rank;
    private final SimpleStringProperty name;
    private final SimpleStringProperty score;
    private final SimpleStringProperty config;

    // Constructor
    public HighScoreRow(int rank, String name, int score, String config) {
        this.rank = new SimpleStringProperty(String.valueOf(rank));
        this.name = new SimpleStringProperty(name);
        this.score = new SimpleStringProperty(String.valueOf(score));
        this.config = new SimpleStringProperty(config);
    }

    // Properties
    public SimpleStringProperty rankProperty() {
        return rank;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty scoreProperty() {
        return score;
    }

    public SimpleStringProperty configProperty() {
        return config;
    }
}
