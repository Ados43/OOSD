package org.oosd.teamorange_finalsubmission;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class HighScoreView {
    public static Parent create(HighScoreStore store, Runnable onBack) {
        TableView<HighScoreRow> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<HighScoreRow, String> cRank = new TableColumn<>("#");
        cRank.setCellValueFactory(data -> data.getValue().rankProperty());
        cRank.setMaxWidth(60);
        cRank.setMinWidth(60);

        TableColumn<HighScoreRow, String> cName = new TableColumn<>("Name");
        cName.setCellValueFactory(data -> data.getValue().nameProperty());

        TableColumn<HighScoreRow, String> cScore = new TableColumn<>("Score");
        cScore.setCellValueFactory(data -> data.getValue().scoreProperty());
        cScore.setMaxWidth(120);
        cScore.setMinWidth(120);

        TableColumn<HighScoreRow, String> cConfig = new TableColumn<>("Config");
        cConfig.setCellValueFactory(data -> data.getValue().configProperty());

        table.getColumns().addAll(cRank, cName, cScore, cConfig);

        Runnable refresh = () -> {
            List<HighScoreEntry> top = store.top10();
            ObservableList<HighScoreRow> rows = FXCollections.observableArrayList();

            // Fill with actual results
            for (int i = 0; i < top.size(); i++) {
                HighScoreEntry e = top.get(i);
                String cfg = (e.config() == null || e.config().isBlank()) ? "----" : e.config();
                rows.add(new HighScoreRow(i + 1, e.name(), e.score(), cfg));
            }

            // Pad with blanks up to 10
            for (int i = top.size(); i < 10; i++) {
                rows.add(new HighScoreRow(i + 1, "----", 0, "----"));
            }

            table.setItems(rows);
        };
        refresh.run();

        Button clear = new Button("Clear High Scores");
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
        back.setOnAction(e -> {
            if (onBack != null) onBack.run();
        });

        HBox buttons = new HBox(10, clear, back);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        BorderPane root = new BorderPane(table);
        root.setPadding(new Insets(12));
        root.setBottom(buttons);
        BorderPane.setMargin(buttons, new Insets(8, 0, 0, 0));
        return root;
    }
}
