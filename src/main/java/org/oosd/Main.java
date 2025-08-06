package org.oosd;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Main extends Application {
    private static final char[][] TETROMINOS = {
            {
                    ' ', ' ', ' ', ' ',
                    'I', 'I', 'I', 'I',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '
            },
            {
                    'O', 'O',
                    'O', 'O'
            },
            {
                    'J', ' ', ' ',
                    'J', 'J', 'J',
                    ' ', ' ', ' '
            },
            {
                    ' ', ' ', 'L',
                    'L', 'L', 'L',
                    ' ', ' ', ' '
            },
            {
                    ' ', 'S', 'S',
                    'S', 'S', ' ',
                    ' ', ' ', ' '
            },
            {
                    ' ', 'T', ' ',
                    'T', 'T', 'T',
                    ' ', ' ', ' '
            },
            {
                    'Z', 'Z', ' ',
                    ' ', 'Z', 'Z',
                    ' ', ' ', ' '
            }
    };

    static class KeyState {
        final boolean isPressed, isReleased;

        KeyState(boolean isPressed, boolean isReleased) {
            this.isPressed = isPressed;
            this.isReleased = isReleased;
        }
    }

    static class KeyInputHandler implements EventHandler<KeyEvent> {
        private final ConcurrentHashMap<KeyCode, Boolean> keys = new ConcurrentHashMap<>();

        public void reset() {
            keys.clear();
        }

        public KeyState get(KeyCode code) {
            Boolean state = keys.get(code);
            return new KeyState(Boolean.TRUE.equals(state), Boolean.FALSE.equals(state));
        }

        @Override
        public void handle(KeyEvent event) {
            if ("KEY_RELEASED".equals(event.getEventType().toString()))
                keys.put(event.getCode(), false);
            else if ("KEY_PRESSED".equals(event.getEventType().toString()))
                keys.put(event.getCode(), true);
        }
    }

    private static final int WIDTH = 12, HEIGHT = 18, BLOCK_SIZE = 20;

    private StackPane root;
    private Random rand;
    private AnimationTimer loop;
    private KeyInputHandler input = new KeyInputHandler();
    private char[][] board;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        rand = new Random();
    }

    @Override
    public void start(Stage primaryStage) {
        root = new StackPane();
        Scene scene = new Scene(root, 1000, 600);
        scene.setOnKeyPressed(input);
        scene.setOnKeyReleased(input);

        showMainScreen();

        primaryStage.setTitle("JavaFX Tetris");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showMainScreen() {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(20));
        menu.setAlignment(Pos.CENTER);

        Label title = new Label("Main Menu");
        Button playButton = new Button("Play!");
        Button settingsButton = new Button("Settings!");
        Button highScoreButton = new Button("High Score!");
        Button quitButton = new Button("Quit!");

        Button[] buttons = {playButton, settingsButton, highScoreButton, quitButton};
        for (Button b : buttons) {
            b.setStyle("-fx-border: 5px solid black; -fx-background-color: #355bcd; -fx-font-size: 30px; -fx-text-fill: white;");
            b.setPrefSize(300, 200);
        }

        playButton.setOnAction(e -> showPlay());
        settingsButton.setOnAction(e -> showSettings());
        highScoreButton.setOnAction(e -> showHighScore());
        quitButton.setOnAction(e -> System.exit(0));

        menu.getChildren().addAll(title, playButton, settingsButton, highScoreButton, quitButton);
        root.getChildren().setAll(menu);
    }

    private void showPlay() {
        Canvas canvas = new Canvas(2 * WIDTH * BLOCK_SIZE, HEIGHT * BLOCK_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();
//        Button back = new Button("Back");
//        back.setOnAction(e -> {
//            if (loop != null) loop.stop();
//            showMainScreen();
//        });

        VBox vbox = new VBox(10, canvas);
        vbox.setAlignment(Pos.CENTER);
        root.getChildren().setAll(vbox);

        input.reset();
        board = new char[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < HEIGHT; j++)
                board[i][j] = ' ';

        loop = createTetrisLoop(gc);
        loop.start();
    }

    private AnimationTimer createTetrisLoop(GraphicsContext gc) {
        return new AnimationTimer() {
            long prevTime = 0;
            int tetromino, x, y, rotation;
            int nextTetromino = rand.nextInt(TETROMINOS.length);
            int nextRotation = rand.nextInt(4);
            boolean isGameOver;
            int score = 0, bestScore = 0;

            {
                selectNewPiece();
            }

            private void restartGame() {
                for (int i = 0; i < WIDTH; i++)
                    for (int j = 0; j < HEIGHT; j++)
                        board[i][j] = ' ';
                nextTetromino = rand.nextInt(TETROMINOS.length);
                nextRotation = rand.nextInt(4);
                selectNewPiece();
                isGameOver = false;
                bestScore = Math.max(score, bestScore);
                score = 0;
            }

            private void selectNewPiece() {
                tetromino = nextTetromino;
                x = WIDTH / 2 - tetrominoSize(tetromino) / 2;
                y = 0;
                rotation = nextRotation;
                nextTetromino = rand.nextInt(TETROMINOS.length);
                nextRotation = rand.nextInt(4);
            }

            @Override
            public void handle(long now) {
                boolean tick = (now - prevTime) / 1e6 >= 1000;
                if (tick) prevTime = now;

                if (input.get(KeyCode.SPACE).isReleased) {
                    int[][] kicks = tetrominoWallKicks(tetromino, rotation, rotation + 1);
                    for (int[] pair : kicks) {
                        if (canMove(tetromino, x + pair[0], y + pair[1], rotation + 1)) {
                            x += pair[0];
                            y += pair[1];
                            rotation++;
                            break;
                        }
                    }
                } else if (input.get(KeyCode.LEFT).isPressed && canMove(tetromino, x - 1, y, rotation)) {
                    x--;
                } else if (input.get(KeyCode.RIGHT).isPressed && canMove(tetromino, x + 1, y, rotation)) {
                    x++;
                } else if (input.get(KeyCode.DOWN).isPressed && canMove(tetromino, x, y + 1, rotation)) {
                    y++;
                }

                if (input.get(KeyCode.ENTER).isReleased && isGameOver) {
                    restartGame();
                }

                input.reset();

                isGameOver = !canMove(tetromino, x, y, rotation);
                if (!isGameOver && tick) {
                    if (!canMove(tetromino, x, y + 1, rotation)) {
                        for (int i = 0; i < tetrominoSize(tetromino); i++) {
                            for (int j = 0; j < tetrominoSize(tetromino); j++) {
                                char value = TETROMINOS[tetromino][tetrominoIndex(tetromino, i, j, rotation)];
                                if (value != ' ') board[x + i][y + j] = value;
                            }
                        }

                        int[] lines = new int[HEIGHT];
                        int count = 0;
                        for (int j = 0; j < HEIGHT; j++) {
                            boolean full = true;
                            for (int i = 0; i < WIDTH; i++)
                                if (board[i][j] == ' ') full = false;
                            if (full) lines[count++] = j;
                        }

                        if (count > 0) {
                            score += 100 * (1 << (count - 1));
                            for (int idx = 0; idx < count; idx++) {
                                for (int j = lines[idx] - 1; j >= 0; j--) {
                                    for (int i = 0; i < WIDTH; i++) {
                                        board[i][j + 1] = board[i][j];
                                    }
                                }
                            }
                        }

                        selectNewPiece();
                    } else y++;
                }

                for (int i = 0; i < WIDTH; i++)
                    for (int j = 0; j < HEIGHT; j++) {
                        setColorCell(gc, board[i][j]);
                        gc.fillRect(i * BLOCK_SIZE, j * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    }

                for (int i = 0; i < tetrominoSize(tetromino); i++)
                    for (int j = 0; j < tetrominoSize(tetromino); j++) {
                        char value = TETROMINOS[tetromino][tetrominoIndex(tetromino, i, j, rotation)];
                        if (value != ' ') {
                            setColorCell(gc, value);
                            gc.fillRect((x + i) * BLOCK_SIZE, (y + j) * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                        }
                    }

                gc.clearRect(WIDTH * BLOCK_SIZE, 0, WIDTH * BLOCK_SIZE, HEIGHT * BLOCK_SIZE);
                gc.setFill(Color.BLACK);
                gc.setFont(Font.font(20));
                gc.fillText("Best: " + bestScore, (WIDTH + 1) * BLOCK_SIZE, BLOCK_SIZE);
                gc.fillText("Score: " + score, (WIDTH + 1) * BLOCK_SIZE, 2 * BLOCK_SIZE);
                gc.fillText("Next:", (WIDTH + 1) * BLOCK_SIZE, 4 * BLOCK_SIZE);

                for (int i = 0; i < tetrominoSize(nextTetromino); i++)
                    for (int j = 0; j < tetrominoSize(nextTetromino); j++) {
                        char value = TETROMINOS[nextTetromino][tetrominoIndex(nextTetromino, i, j, nextRotation)];
                        if (value != ' ') {
                            setColorCell(gc, value);
                            gc.fillRect((WIDTH + 1 + i) * BLOCK_SIZE, (5 + j) * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                        }
                    }

                if (isGameOver) {
                    gc.setFill(Color.RED);
                    gc.setFont(Font.font(30));
                    gc.fillText("GAME OVER", (WIDTH + 1) * BLOCK_SIZE, 12 * BLOCK_SIZE);
                    gc.setFont(Font.font(20));
                    gc.fillText("Press Enter to restart", (WIDTH + 1) * BLOCK_SIZE, 13 * BLOCK_SIZE);
                }
            }
        };
    }

    private int tetrominoSize(int piece) {
        return switch (piece) {
            case 0 -> 4;
            case 1 -> 2;
            default -> 3;
        };
    }

    private int tetrominoIndex(int piece, int i, int j, int r) {
        int s = tetrominoSize(piece), ip, jp;
        switch (r % 4) {
            case 0 -> {
                ip = i;
                jp = j;
            }
            case 1 -> {
                ip = j;
                jp = s - 1 - i;
            }
            case 2 -> {
                ip = s - 1 - i;
                jp = s - 1 - j;
            }
            case 3 -> {
                ip = s - 1 - j;
                jp = i;
            }
            default -> throw new AssertionError();
        }
        return ip + s * jp;
    }

    private boolean canMove(int piece, int x, int y, int r) {
        int s = tetrominoSize(piece);
        for (int i = 0; i < s; i++)
            for (int j = 0; j < s; j++) {
                char v = TETROMINOS[piece][tetrominoIndex(piece, i, j, r)];
                int cx = x + i, cy = y + j;
                if (v == ' ') continue;
                if (cx < 0 || cx >= WIDTH || cy < 0 || cy >= HEIGHT || board[cx][cy] != ' ')
                    return false;
            }
        return true;
    }

    private int[][] tetrominoWallKicks(int piece, int from, int to) {
        from %= 4;
        to %= 4;
        if (piece == 0) {
            if (from == 0 && to == 1) return new int[][]{{0, 0}, {-2, 0}, {+1, 0}, {-2, -1}, {+1, +2}};
            if (from == 1 && to == 2) return new int[][]{{0, 0}, {-1, 0}, {+2, 0}, {-1, +2}, {+2, -1}};
            if (from == 2 && to == 3) return new int[][]{{0, 0}, {+2, 0}, {-1, 0}, {+2, +1}, {-1, -2}};
            if (from == 3 && to == 0) return new int[][]{{0, 0}, {+1, 0}, {-2, 0}, {+1, -2}, {-2, +1}};
        } else {
            if (from == 0 && to == 1) return new int[][]{{0, 0}, {-1, 0}, {-1, +1}, {0, -2}, {-1, -2}};
            if (from == 1 && to == 2) return new int[][]{{0, 0}, {+1, 0}, {+1, -1}, {0, +2}, {+1, +2}};
            if (from == 2 && to == 3) return new int[][]{{0, 0}, {+1, 0}, {+1, +1}, {0, -2}, {+1, -2}};
            if (from == 3 && to == 0) return new int[][]{{0, 0}, {-1, 0}, {-1, -1}, {0, +2}, {-1, +2}};
        }
        return new int[][]{{0, 0}};
    }

    private void setColorCell(GraphicsContext gc, char v) {
        switch (v) {
            case 'I' -> gc.setFill(Color.CYAN);
            case 'O' -> gc.setFill(Color.YELLOW);
            case 'J' -> gc.setFill(Color.BLUE);
            case 'L' -> gc.setFill(Color.ORANGE);
            case 'S' -> gc.setFill(Color.GREEN);
            case 'T' -> gc.setFill(Color.PURPLE);
            case 'Z' -> gc.setFill(Color.RED);
            default -> gc.setFill(Color.GRAY);
        }
    }

    private void showSettings() {
        Label label = new Label("Settings");
        Button back = new Button("Back");
        back.setOnAction(e -> showMainScreen());
        VBox vbox = new VBox(10, label, back);
        vbox.setAlignment(Pos.CENTER);
        root.getChildren().setAll(vbox);
    }

    private void showHighScore() {
        Label label = new Label("High Score");
        Button back = new Button("Back");
        back.setOnAction(e -> showMainScreen());
        VBox vbox = new VBox(10, label, back);
        vbox.setAlignment(Pos.CENTER);
        root.getChildren().setAll(vbox);
    }
}
