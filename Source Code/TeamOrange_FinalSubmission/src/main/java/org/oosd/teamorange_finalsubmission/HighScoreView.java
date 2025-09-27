package org.oosd.teamorange_finalsubmission;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.util.List;

// High scores screen
public class HighScoreView {
    public static Parent create(HighScoreStore store, Runnable onBack) {
        // Build table
        TableView<HighScoreRow> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        table.setFocusTraversable(false);
        table.setPlaceholder(new Label("No high scores yet"));

        // Columns
        TableColumn<HighScoreRow, String> cRank = new TableColumn<>("#");
        cRank.setCellValueFactory(data -> data.getValue().rankProperty());
        cRank.setMinWidth(50);
        cRank.setMaxWidth(50);

        TableColumn<HighScoreRow, String> cName = new TableColumn<>("Name");
        cName.setCellValueFactory(data -> data.getValue().nameProperty());
        cName.setMinWidth(120);

        TableColumn<HighScoreRow, String> cScore = new TableColumn<>("Score");
        cScore.setCellValueFactory(data -> data.getValue().scoreProperty());
        cScore.setMinWidth(100);
        cScore.setMaxWidth(140);

        TableColumn<HighScoreRow, String> cConfig = new TableColumn<>("Config");
        cConfig.setCellValueFactory(data -> data.getValue().configProperty());
        cConfig.setMinWidth(150);

        table.getColumns().addAll(cRank, cName, cScore, cConfig);

        // Fixed height
        table.setFixedCellSize(40);
        final int VISIBLE_ROWS = 10;
        final double HEADER_H = 30;
        final double TABLE_H = HEADER_H + (VISIBLE_ROWS * table.getFixedCellSize());
        table.setPrefHeight(TABLE_H);
        table.setMinHeight(TABLE_H);
        table.setMaxHeight(TABLE_H);
        table.setMinWidth(Region.USE_PREF_SIZE);

        // Data loader
        Runnable refresh = () -> {
            List<HighScoreEntry> top = store.top10();
            ObservableList<HighScoreRow> rows = FXCollections.observableArrayList();
            for (int i = 0; i < top.size(); i++) {
                HighScoreEntry e = top.get(i);
                String cfg = (e.config() == null || e.config().isBlank()) ? "----" : e.config();
                rows.add(new HighScoreRow(i + 1, e.name(), e.score(), cfg));
            }
            for (int i = top.size(); i < VISIBLE_ROWS; i++) {
                rows.add(new HighScoreRow(i + 1, "----", 0, "----"));
            }
            table.setItems(rows);
        };
        refresh.run();

        // Buttons
        Button clear = new Button("Clear High Scores");
        clear.getStyleClass().add("primary-btn");
        clear.setOnAction(e -> {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Clear all high scores?", ButtonType.YES, ButtonType.NO);
            a.showAndWait().ifPresent(btn -> {
                if (btn == ButtonType.YES) {
                    store.clear();
                    refresh.run();
                }
            });
        });

        Button back = new Button("Back");
        back.getStyleClass().add("primary-btn");
        back.setOnAction(e -> {
            if (onBack != null) onBack.run();
        });

        HBox buttons = new HBox(10, clear, back);
        buttons.setAlignment(Pos.CENTER);

        // Root container
        BorderPane root = new BorderPane(table);
        root.setPadding(new Insets(12));
        root.setBottom(buttons);
        BorderPane.setMargin(buttons, new Insets(8, 0, 0, 0));
        return root;
    }
}
