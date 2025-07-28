package org.example;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import static java.awt.SystemColor.control;

public class Main extends Application {
    private StackPane root;
    public static void main(String[] args) {
        System.out.println("Hello World!");
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        double fieldWidth = 400;
        double fieldHeight = 300;
        int radius = 10;
        Circle ball = new Circle(radius, Color.RED);
        ball.setCenterX(fieldWidth / 2);
        ball.setCenterY(fieldHeight / 2);
        Pane pane = new Pane(ball);
        Scene scene = new Scene(pane, fieldWidth, fieldHeight);
        AnimationTimer timer = new AnimationTimer() {
            private double dx = 3, dy = 3;
            public void handle(long now) {
                double nextX = ball.getCenterX() + dx;
                double nextY = ball.getCenterY() + dy;
                if (nextX - radius < 0 || nextX + radius > fieldWidth) dx = -dx;
                if (nextY - radius < 0 || nextY + radius > fieldHeight) dy = -dy;
                ball.setCenterX(ball.getCenterX() + dx);
                ball.setCenterY(ball.getCenterY() + dy);
            }
        };
        timer.start();
        primaryStage.setTitle("JavaFX Bouncing Ball Animation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
//        Pane pane = new Pane();
//        //Circle
//        Circle circle = new Circle(50, Color.RED); // Radius = 50, filled red
//        circle.setCenterX(100);
//        circle.setCenterY(100);
//
//        // Rectangle
//        Rectangle rect = new Rectangle(50,50,100,60);
//        rect.setFill(Color.BLUE);
//        rect.setStroke(Color.BLACK);
//        rect.setStrokeWidth(2);
//        pane.getChildren().addAll(circle, rect);
//
//        Scene scene = new Scene(pane, 300, 250);
//        primaryStage.setTitle("Circle and Rectangle Demo");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }












//        Label label = new Label("Press any key...");
//        StackPane root = new StackPane(label);
//        Scene scene = new Scene(root, 300, 200);
//
//        // Add key event handlers to the scene
//        scene.setOnKeyPressed(e -> {
//            label.setText("Key Pressed: " + e.getCode());
//        });
//        scene.setOnKeyReleased(e -> {
//            label.setText("Key Released: " + e.getCode());
//        });
//        scene.setOnKeyTyped(e -> {
//            label.setText("Key Typed: " + e.getCharacter());
//        });
//        primaryStage.setTitle("JavaFX Key Event Demo");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//        root = new StackPane();
//        Scene scene = new Scene(root, 400, 300);
//        showMainSCreen();
//        primaryStage.setTitle("JavaFX Multi-Screen Demo");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//    private void showMainSCreen() {
//        VBox mainScreen = new VBox(10);
//        mainScreen.setPadding(new Insets(20));
//        Label mainLabel = new Label("Main Screen");
//        Button screen1Button = new Button("Go to Screen 1");
//        Button screen2Button = new Button("Go to Screen 2");
//        screen1Button.setOnAction(e -> showScreen1());
//        screen2Button.setOnAction(e -> showScreen2());
//        mainScreen.getChildren().addAll(mainLabel, screen1Button, screen2Button);
//        root.getChildren().setAll(mainScreen);
//    }
//    private void showScreen1() {
//        VBox screen1 = new VBox(10);
//        screen1.setPadding(new Insets(20));
//        Label label = new Label("Screen 1");
//        Button backButton = new Button("Back to Main Screen");
//        backButton.setOnAction(e -> showMainSCreen());
//        screen1.getChildren().addAll(label, backButton);
//        root.getChildren().setAll(screen1);
//    }
//    private void showScreen2() {
//        VBox screen2 = new VBox(10);
//        screen2.setPadding(new Insets(20));
//        Label label = new Label("Screen 2");
//        Button backButton = new Button("Back to Main Screen");
//        backButton.setOnAction(e -> showMainSCreen());
//        screen2.getChildren().addAll(label, backButton);
//        root.getChildren().setAll(screen2);
//    }





//        CheckBox cb1 = new CheckBox("Enable Notifications");
//        CheckBox cb2 = new CheckBox("Enable Dark Mode");
//        cb1.setOnAction(e -> System.out.println("Notifications: " + cb1.isSelected()));
//        cb2.setOnAction(e -> System.out.println("Dark Mode: : " + cb2.isSelected()));
//        // Radio Buttons
//        RadioButton rb1 = new RadioButton("Male");
//        RadioButton rb2 = new RadioButton("Female");
//        ToggleGroup genderGroup = new ToggleGroup();
//        rb1.setToggleGroup(genderGroup);
//        rb2.setToggleGroup(genderGroup);
//        rb1.setOnAction(e -> System.out.println("Selected Gender: Male"));
//        rb2.setOnAction(e -> System.out.println("Selected Gender: Female"));
//        // ComboBox
//        ComboBox<String> fruitsCombo = new ComboBox<>();
//        fruitsCombo.getItems().addAll("Apple", "Banana", "Cherry");
//        fruitsCombo.setOnAction(e -> {
//            String selected = fruitsCombo.getValue();
//            System.out.println("Selected Fruit: " + selected);
//        });
//        // Slider
//        Slider volumneSlider = new Slider(0, 100, 50);
//        volumneSlider.setShowTickLabels(true);
//        volumneSlider.setShowTickMarks(true);
//        volumneSlider.setMajorTickUnit(25);
//        volumneSlider.valueProperty().addListener((obs, oldVal, newVal) -> System.out.println("Volume: " + newVal.intValue()));
//        //Layout
//        VBox vbox = new VBox(10);
//        vbox.setPadding(new Insets(20));
//        vbox.getChildren().addAll(
//                new Label("Select options:"),
//                cb1, cb2,
//                new Label("Select Gender:"),
//                rb1, rb2,
//                new Label("Select Fruits:"),
//                fruitsCombo,
//                new Label("Adjust Volume:"),
//                volumneSlider
//        );
//        Scene scene = new Scene(vbox, 300, 400);
//        primaryStage.setTitle("UI Controls Example!");
//        primaryStage.setScene(scene);
//        primaryStage.show();


//        //Top: Menu Bar
//        MenuBar menuBar = new MenuBar();
//        Menu fileMenu = new Menu("File");
//        MenuItem exitItem = new MenuItem("Exit");
//        exitItem.setOnAction(e -> primaryStage.close());
//        fileMenu.getItems().add(exitItem);
//        menuBar.getMenus().add(fileMenu);
//        // Left: Navigation
//        VBox nav = new VBox(10);
//        nav.setPadding(new javafx.geometry.Insets(10));
//        Button homeButton = new Button("Home");
//        Button settingsButton = new Button("Settings");
//        nav.getChildren().addAll(homeButton, settingsButton);
//        // Center: Main content
//        Label mainContent = new Label("Welcome to the app!");
//        mainContent.setStyle("-fx-font-size: 16px;");
//        // Bottom: Status Bar
//        Label statusBar = new Label("Ready");
//        // Assemble BorderPane
//        BorderPane borderPane = new BorderPane();
//        borderPane.setTop(menuBar);
//        borderPane.setLeft(nav);
//        borderPane.setCenter(mainContent);
//        borderPane.setBottom(statusBar);
//        // Create Scene
//        Scene scene = new Scene(borderPane, 600, 400);
//        primaryStage.setTitle("My JavaFX App");
//        primaryStage.setScene(scene);
//        primaryStage.show();





//        primaryStage.setScene(scene);
//        primaryStage.setTitle("GridPane Demo!");
//        primaryStage.show();
        // Login section:
//        Label usernameLabel = new Label("Username:");
//        TextField usernameField = new TextField();
//        Label passwordLabel = new Label("Password:");
//        PasswordField passwordField = new PasswordField();
//        Button loginButton = new Button("Login");
//        Label messageLabel = new Label();
//        // Create GridPane and add controls
//        GridPane grid = new GridPane();
//        grid.setPadding(new Insets(20));
//        grid.setHgap(10);
//        grid.setVgap(10);
//
//        grid.add(usernameLabel, 0, 0);
//        grid.add(usernameField, 1, 0);
//        grid.add(passwordLabel, 0, 1);
//        grid.add(passwordField, 1, 1);
//        grid.add(loginButton, 1, 2);
//        grid.add(messageLabel, 1, 3);
//
//        loginButton.setOnAction(e -> {
//            String user = usernameField.getText();
//            String pass = passwordField.getText();
//            if ("admin".equals(user) && "password".equals(pass)) {
//                messageLabel.setText("Login Successful!");
//            } else {
//                messageLabel.setText("Invalid Credentials!");
//            }
//        });
        // Create scene
//        Scene scene = new Scene(grid, 300, 200);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("GridPane Demo!");
//        primaryStage.show();


//        Label label = new Label("Please input your name:");
//        TextField input = new TextField();
//        Button btn = new Button("Click Me!");
//        StackPane root = new StackPane();
//        root.getChildren().addAll(label, input, btn);
//        btn.setOnAction(e -> {
//            label.setText("Hello, " + input.getText() + "!");
//        });
//        StackPane.setAlignment(label, Pos.TOP_CENTER);
//        StackPane.setAlignment(input, Pos.CENTER);
//        StackPane.setAlignment(btn, Pos.BOTTOM_CENTER);
//        Scene scene = new Scene(root, 400, 200);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("JavaFX Demo!");
//        primaryStage.show();



//        Label label = new Label("Hello from JavaFX + Maven!");
//        StackPane root = new StackPane(label);
//        Scene scene = new Scene(root, 400, 200);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Maven Project");
//        primaryStage.show();
}