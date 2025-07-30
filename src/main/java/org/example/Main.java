package org.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
    private StackPane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        root = new StackPane();
        Scene scene = new Scene(root, 600, 400);
        showMainScreen();
        primaryStage.setTitle("JavaFX Multi-Screen App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showMainScreen() {
        VBox mainScreen = new VBox(10);
        mainScreen.setPadding(new Insets(20));
        mainScreen.setAlignment(Pos.CENTER);

        Label label = new Label("Main Screen");
        Button helloButton = new Button("Hello World");
        Button inputDemoButton = new Button("Input Demo");
        Button bouncingBallButton = new Button("Bouncing Ball");
        Button shapesButton = new Button("Shapes");
        Button keyEventButton = new Button("Key Event Demo");
        Button uiControlsButton = new Button("UI Controls");
        Button loginButton = new Button("Login Form");
        Button navLayoutButton = new Button("Navigation Layout");

        helloButton.setOnAction(e -> showHelloWorld());
        inputDemoButton.setOnAction(e -> showInputDemo());
        bouncingBallButton.setOnAction(e -> showBouncingBall());
        shapesButton.setOnAction(e -> showShapes());
        keyEventButton.setOnAction(e -> showKeyEvents());
        uiControlsButton.setOnAction(e -> showUIControls());
        loginButton.setOnAction(e -> showLoginForm());
        navLayoutButton.setOnAction(e -> showNavLayout());

        mainScreen.getChildren().addAll(
                label, helloButton, inputDemoButton, bouncingBallButton,
                shapesButton, keyEventButton, uiControlsButton, loginButton, navLayoutButton
        );
        root.getChildren().setAll(mainScreen);
    }

    private void showHelloWorld() {
        Label label = new Label("Hello from JavaFX + Maven!");
        Button back = new Button("Back");
        back.setOnAction(e -> showMainScreen());

        VBox vbox = new VBox(10, label, back);
        vbox.setAlignment(Pos.CENTER);
        root.getChildren().setAll(vbox);
    }

    private void showInputDemo() {
        Label label = new Label("Please input your name:");
        TextField input = new TextField();
        Button btn = new Button("Click Me!");
        Label result = new Label();
        Button back = new Button("Back");

        btn.setOnAction(e -> result.setText("Hello, " + input.getText() + "!"));
        back.setOnAction(e -> showMainScreen());

        VBox vbox = new VBox(10, label, input, btn, result, back);
        vbox.setAlignment(Pos.CENTER);
        root.getChildren().setAll(vbox);
    }

    private void showBouncingBall() {
        double width = 600, height = 400;
        int radius = 10;

        Circle ball = new Circle(radius, Color.RED);
        ball.setCenterX(width / 2);
        ball.setCenterY(height / 2);

        Pane pane = new Pane(ball);
        pane.setPrefSize(width, height);

        Button back = new Button("Back");
        back.setOnAction(e -> {
            timer.stop();
            showMainScreen();
        });

        VBox container = new VBox(pane, back);
        container.setAlignment(Pos.BOTTOM_CENTER);

        root.getChildren().setAll(container);

        timer = new AnimationTimer() {
            double dx = 3, dy = 3;

            public void handle(long now) {
                double nextX = ball.getCenterX() + dx;
                double nextY = ball.getCenterY() + dy;

                if (nextX - radius < 0 || nextX + radius > width) dx = -dx;
                if (nextY - radius < 0 || nextY + radius > height) dy = -dy;

                ball.setCenterX(ball.getCenterX() + dx);
                ball.setCenterY(ball.getCenterY() + dy);
            }
        };
        timer.start();
    }

    private AnimationTimer timer;

    private void showShapes() {
        Pane pane = new Pane();
        Circle circle = new Circle(50, Color.RED);
        circle.setCenterX(100);
        circle.setCenterY(100);

        Rectangle rect = new Rectangle(50, 50, 100, 60);
        rect.setFill(Color.BLUE);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(2);

        pane.getChildren().addAll(circle, rect);

        Button back = new Button("Back");
        back.setOnAction(e -> showMainScreen());

        VBox box = new VBox(pane, back);
        box.setAlignment(Pos.BOTTOM_CENTER);
        root.getChildren().setAll(box);
    }

    private void showKeyEvents() {
        Label label = new Label("Press any key...");
        StackPane pane = new StackPane(label);
        pane.setPrefSize(400, 200);

        Button back = new Button("Back");
        VBox container = new VBox(pane, back);
        container.setAlignment(Pos.CENTER);
        root.getChildren().setAll(container);

        root.getScene().addEventFilter(KeyEvent.KEY_PRESSED, e -> label.setText("Key Pressed: " + e.getCode()));
        root.getScene().addEventFilter(KeyEvent.KEY_RELEASED, e -> label.setText("Key Released: " + e.getCode()));
        root.getScene().addEventFilter(KeyEvent.KEY_TYPED, e -> label.setText("Key Typed: " + e.getCharacter()));

        back.setOnAction(e -> {
            root.getScene().getEventDispatcher(); // remove listeners if needed
            showMainScreen();
        });
    }

    private void showUIControls() {
        CheckBox cb1 = new CheckBox("Enable Notifications");
        CheckBox cb2 = new CheckBox("Enable Dark Mode");

        cb1.setOnAction(e -> System.out.println("Notifications: " + cb1.isSelected()));
        cb2.setOnAction(e -> System.out.println("Dark Mode: " + cb2.isSelected()));

        RadioButton rb1 = new RadioButton("Male");
        RadioButton rb2 = new RadioButton("Female");
        ToggleGroup genderGroup = new ToggleGroup();
        rb1.setToggleGroup(genderGroup);
        rb2.setToggleGroup(genderGroup);

        ComboBox<String> fruitsCombo = new ComboBox<>();
        fruitsCombo.getItems().addAll("Apple", "Banana", "Cherry");
        fruitsCombo.setOnAction(e -> System.out.println("Selected Fruit: " + fruitsCombo.getValue()));

        Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(25);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> System.out.println("Volume: " + newVal.intValue()));

        Button back = new Button("Back");
        back.setOnAction(e -> showMainScreen());

        VBox vbox = new VBox(10,
                new Label("Select options:"), cb1, cb2,
                new Label("Select Gender:"), rb1, rb2,
                new Label("Select Fruit:"), fruitsCombo,
                new Label("Adjust Volume:"), volumeSlider,
                back
        );
        vbox.setPadding(new Insets(20));
        root.getChildren().setAll(vbox);
    }

    private void showLoginForm() {
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Label messageLabel = new Label();
        Button back = new Button("Back");

        loginButton.setOnAction(e -> {
            String user = usernameField.getText();
            String pass = passwordField.getText();
            if ("admin".equals(user) && "password".equals(pass)) {
                messageLabel.setText("Login Successful!");
            } else {
                messageLabel.setText("Invalid Credentials!");
            }
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);
        grid.add(messageLabel, 1, 3);
        grid.add(back, 1, 4);

        back.setOnAction(e -> showMainScreen());
        root.getChildren().setAll(grid);
    }

    private void showNavLayout() {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> System.exit(0));
        fileMenu.getItems().add(exitItem);
        menuBar.getMenus().add(fileMenu);

        VBox nav = new VBox(10);
        nav.setPadding(new Insets(10));
        Button homeButton = new Button("Home");
        Button settingsButton = new Button("Settings");
        nav.getChildren().addAll(homeButton, settingsButton);

        Label mainContent = new Label("Welcome to the app!");
        mainContent.setStyle("-fx-font-size: 16px;");
        Label statusBar = new Label("Ready");

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setLeft(nav);
        borderPane.setCenter(mainContent);
        borderPane.setBottom(statusBar);

        Button back = new Button("Back");
        back.setOnAction(e -> showMainScreen());
        borderPane.setRight(back);

        root.getChildren().setAll(borderPane);
    }
}