package org.oosd.teamorange_milestoneone.application.views;

public class ScoreView {

    // //High Score Menu
    // @FXML
    // protected void onHighScore() {
    //     Stage stage = (Stage) highScoreButton.getScene().getWindow();

    //     // Create a table for player scores
    //     javafx.scene.control.TableView<String[]> table = new javafx.scene.control.TableView<>();
    //     table.setColumnResizePolicy(javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY);
    //     table.setMaxWidth(400);

    //     // Define table columns: Player and Score
    //     javafx.scene.control.TableColumn<String[], String> nameCol = new javafx.scene.control.TableColumn<>("Player");
    //     nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[0]));
    //     javafx.scene.control.TableColumn<String[], String> scoreCol = new javafx.scene.control.TableColumn<>("Score");
    //     scoreCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[1]));
    //     table.getColumns().setAll(nameCol, scoreCol);

    //     // Dummy high score data
    //     table.getItems().addAll(
    //             new String[]{"Aidan", "1200"},
    //             new String[]{"Fletcher", "1100"},
    //             new String[]{"Bernie", "1000"},
    //             new String[]{"Sam", "950"},
    //             new String[]{"Alex", "900"},
    //             new String[]{"Jordan", "850"},
    //             new String[]{"Chris", "800"},
    //             new String[]{"Taylor", "750"},
    //             new String[]{"Jamie", "700"},
    //             new String[]{"Morgan", "650"}
    //     );

    //     // Back button to return to menu
    //     Button backButton = new Button("Back");
    //     backButton.setOnAction(e -> goBackToMenu(stage));

    //     // Layout for high score screen
    //     VBox scoreRoot = new VBox(20, new Label("High Scores"), table, backButton);
    //     scoreRoot.setAlignment(javafx.geometry.Pos.CENTER);
    //     scoreRoot.setStyle("-fx-padding: 20;");

    //     // Show high score scene
    //     stage.setScene(new Scene(scoreRoot, 800, 600));
    // }
    
}
