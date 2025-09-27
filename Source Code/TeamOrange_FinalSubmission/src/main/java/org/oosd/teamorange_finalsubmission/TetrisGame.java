package org.oosd.teamorange_finalsubmission;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"FieldMayBeLocal", "SameParameterValue"}) // calm “can be local” & “same arg every call”
public class TetrisGame {

    // Tetromino definitions
    private static final char[][] TETROMINOS = {
            {
                    ' ', ' ', ' ', ' ',
                    'I', 'I', 'I', 'I',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '
            },
            {
                    ' ', ' ', ' ', ' ',
                    ' ', 'O', 'O', ' ',
                    ' ', 'O', 'O', ' ',
                    ' ', ' ', ' ', ' '
            },
            {
                    'J', ' ', ' ', ' ',
                    'J', 'J', 'J', ' ',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '
            },
            {
                    ' ', ' ', 'L', ' ',
                    'L', 'L', 'L', ' ',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '
            },
            {
                    ' ', 'S', 'S', ' ',
                    'S', 'S', ' ', ' ',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '
            },
            {
                    ' ', 'T', ' ', ' ',
                    'T', 'T', 'T', ' ',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '
            },
            {
                    'Z', 'Z', ' ', ' ',
                    ' ', 'Z', 'Z', ' ',
                    ' ', ' ', ' ', ' ',
                    ' ', ' ', ' ', ' '
            }
    };

    // Dependencies
    private HighScoreStore highScoreStore;

    public void setHighScoreStore(HighScoreStore store) {
        this.highScoreStore = store;
    }

    private MediaManager mediaManager;

    public void setMediaManager(MediaManager mm) {
        this.mediaManager = mm;
    }

    // Config values
    private int cfgWidth = 12;
    private int cfgHeight = 18;
    private int cfgLevel = 1;
    private boolean cfgExtended = false;
    private PlayerType cfgP1 = PlayerType.HUMAN;
    private PlayerType cfgP2 = PlayerType.HUMAN;

    public void setConfig(int width, int height, int level,
                          boolean extended, boolean musicOn, boolean sfxOn,
                          PlayerType p1, PlayerType p2) {
        this.cfgWidth = Math.max(5, Math.min(40, width));
        this.cfgHeight = Math.max(10, Math.min(60, height));
        this.cfgLevel = Math.max(1, Math.min(20, level));
        this.cfgExtended = extended;
        this.cfgP1 = p1;
        this.cfgP2 = p2;
        if (mediaManager != null) {
            mediaManager.setMusicMuted(!musicOn);
            mediaManager.setSfxMuted(!sfxOn);
        }
    }

    // Rendering sizes
    private int boardW;
    private int boardH;
    private static final int BLOCK_SIZE = 30;
    private double blockSize = BLOCK_SIZE;

    private StackPane rootWrapper;
    private StackPane boardStack1, boardStack2;

    // Canvases and contexts
    private Canvas gameCanvas1, nextCanvas1;
    private Canvas gameCanvas2, nextCanvas2;
    private GraphicsContext gc1, nextGc1;
    private GraphicsContext gc2, nextGc2;

    private Label p1ScoreLabel;
    private Label p1LevelLabel;
    private Label p1LinesLabel;
    private Label p1MusicLabel;
    private Label p1SfxLabel;
    private Label p2ScoreLabel;
    private Label p2LevelLabel;
    private Label p2LinesLabel;

    // AI fields
    private TetrisAI aiP1, aiP2;
    private TetrisAI.BestMove planP1, planP2;

    // External clients
    private ExternalClient extP1, extP2;
    private Label extWarn1, extWarn2;

    // High score UI
    private VBox hsPaneP1, hsPaneP2;
    private TextField hsNameP1, hsNameP2;
    private Button hsSubmitP1, hsSubmitP2;
    private boolean submittedP1 = false, submittedP2 = false;

    // Keyboard input
    private final ConcurrentHashMap<KeyCode, Boolean> keys = new ConcurrentHashMap<>();
    private final KeyInputHandler input = new KeyInputHandler();

    private int baseFallInterval = 30;
    private final int minFallInterval = 5;
    private long startTime = -1;
    private boolean paused = false;

    private Runnable onBack;

    // Player state (per player)
    private static class PlayerState {
        char[][] board;
        int currentPiece, rotation, x, y;
        int score = 0;
        int lines = 0;
        int nextIndex = 0;
        boolean gameOver = false;
        int fallCounter = 0;
    }

    // Two-player state
    private PlayerState p1, p2;
    private List<Integer> sharedSequence;

    // Size helpers
    private int cell() {
        return (int) Math.max(1, Math.floor(blockSize));
    }

    private double boardPixelW() {
        return boardW * cell();
    }

    private double boardPixelH() {
        return boardH * cell();
    }

    // Callbacks & toggles
    public void setOnBack(Runnable onBack) {
        this.onBack = onBack;
    }

    @SuppressWarnings("unused")
    public void setExtendedMode(boolean enabled) {
        this.cfgExtended = enabled;
    }

    // Build UI & start
    public Parent createContent() {
        // Live sizes
        boardW = cfgWidth;
        boardH = cfgHeight;

        // Players/AI/External
        aiP1 = (cfgP1 == PlayerType.AI) ? new TetrisAI(TETROMINOS, boardW, boardH) : null;
        aiP2 = (cfgP2 == PlayerType.AI) ? new TetrisAI(TETROMINOS, boardW, boardH) : null;
        extP1 = (cfgP1 == PlayerType.EXTERNAL) ? new ExternalClient() : null;
        extP2 = (cfgP2 == PlayerType.EXTERNAL) ? new ExternalClient() : null;

        // Speed from level
        baseFallInterval = Math.max(10, 34 - (cfgLevel - 1) * 3);

        // Init players & sequence
        p1 = createInitialPlayerState();
        submittedP1 = submittedP2 = false;
        if (cfgExtended) p2 = createInitialPlayerState();
        sharedSequence = generateSharedSequence(50_000, 123456789L);
        spawnNewPiece(p1);
        if (p2 != null) spawnNewPiece(p2);

        // Canvases & HUDs
        gameCanvas1 = new Canvas(boardPixelW(), boardPixelH());
        gc1 = gameCanvas1.getGraphicsContext2D();
        nextCanvas1 = new Canvas(6 * BLOCK_SIZE, 6 * BLOCK_SIZE);
        nextGc1 = nextCanvas1.getGraphicsContext2D();
        VBox rightPanel1 = buildHudForPlayer1();

        boardStack1 = new StackPane(gameCanvas1);
        boardStack1.setPrefSize(boardPixelW(), boardPixelH());
        StackPane.setAlignment(gameCanvas1, Pos.CENTER);

        // Layout: one or two columns
        HBox rootLeftOrSingle;
        // UI containers
        HBox rootRow;
        if (p2 == null) {
            VBox p1Side = new VBox(20, nextCanvas1, rightPanel1);
            p1Side.setAlignment(Pos.TOP_CENTER);
            rootLeftOrSingle = new HBox(20, boardStack1, p1Side);
            rootLeftOrSingle.setAlignment(Pos.TOP_CENTER);
            rootRow = new HBox(40, rootLeftOrSingle);
        } else {
            gameCanvas2 = new Canvas(boardPixelW(), boardPixelH());
            gc2 = gameCanvas2.getGraphicsContext2D();
            nextCanvas2 = new Canvas(6 * BLOCK_SIZE, 6 * BLOCK_SIZE);
            nextGc2 = nextCanvas2.getGraphicsContext2D();
            VBox rightPanel2 = buildHudForPlayer2();

            boardStack2 = new StackPane(gameCanvas2);
            boardStack2.setPrefSize(boardPixelW(), boardPixelH());
            StackPane.setAlignment(gameCanvas2, Pos.CENTER);

            VBox p1Side = new VBox(10, nextCanvas1, rightPanel1);
            p1Side.setAlignment(Pos.TOP_CENTER);
            VBox p2Side = new VBox(10, nextCanvas2, rightPanel2);
            p2Side.setAlignment(Pos.TOP_CENTER);

            HBox col1 = new HBox(20, boardStack1, p1Side);
            col1.setAlignment(Pos.TOP_CENTER);
            HBox col2 = new HBox(20, boardStack2, p2Side);
            col2.setAlignment(Pos.TOP_CENTER);
            rootRow = new HBox(40, col1, col2);
        }

        // External warnings
        extWarn1 = new Label("External server unavailable");
        extWarn1.setStyle("-fx-background-color: rgba(255,80,80,0.85); -fx-text-fill: white; -fx-padding: 4 8; -fx-background-radius: 6; -fx-font-size: 12;");
        extWarn1.setVisible(false);
        StackPane.setAlignment(extWarn1, Pos.TOP_RIGHT);
        StackPane.setMargin(extWarn1, new javafx.geometry.Insets(8, 8, 0, 0));
        boardStack1.getChildren().add(extWarn1);

        if (boardStack2 != null) {
            extWarn2 = new Label("External server unavailable");
            extWarn2.setStyle("-fx-background-color: rgba(255,80,80,0.85); -fx-text-fill: white; -fx-padding: 4 8; -fx-background-radius: 6; -fx-font-size: 12;");
            extWarn2.setVisible(false);
            StackPane.setAlignment(extWarn2, Pos.TOP_RIGHT);
            StackPane.setMargin(extWarn2, new javafx.geometry.Insets(8, 8, 0, 0));
            boardStack2.getChildren().add(extWarn2);
        }

        // Header row
        Label gameTitle = new Label("Enhanced Tetris Game");
        gameTitle.getStyleClass().add("screen-title");
        gameTitle.setMaxWidth(Double.MAX_VALUE);

        Button backButton = new Button("Back");
        backButton.setFocusTraversable(false);
        backButton.setOnAction(e -> {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                    "Your current progress will be lost if you return to the main menu. Are you sure?",
                    ButtonType.YES, ButtonType.NO);
            if (a.showAndWait().filter(ButtonType.YES::equals).isPresent()) {
                paused = true;
                if (mediaManager != null) mediaManager.stopBgMusic();
                if (onBack != null) onBack.run();
            }
        });

        BorderPane titleRow = createTitleRow(backButton, gameTitle); // small extract to satisfy suggestion

        // Settings bar
        HBox infoBar = buildInfoBar();
        infoBar.setAlignment(Pos.CENTER);

        // Header layout
        VBox header = new VBox(6, titleRow, infoBar);
        header.setAlignment(Pos.TOP_CENTER);
        header.setPadding(new javafx.geometry.Insets(6, 8, 12, 8));

        // Content layout
        rootRow.setAlignment(Pos.CENTER);
        rootRow.setSpacing(40);
        rootRow.setPadding(new javafx.geometry.Insets(10, 20, 20, 20));

        // Root wrapper
        BorderPane wrapper = new BorderPane();
        wrapper.setTop(header);
        wrapper.setCenter(rootRow);
        wrapper.setPadding(new javafx.geometry.Insets(16, 16, 50, 16));
        rootWrapper = new StackPane(wrapper);
        rootWrapper.getStyleClass().add("app-bg");

        // High score panes
        hsNameP1 = new TextField();
        hsNameP1.setPromptText("Name (P1)");
        hsNameP1.setPrefColumnCount(12);
        hsNameP1.setMaxWidth(180);
        hsSubmitP1 = new Button("Submit P1");
        hsSubmitP1.setFocusTraversable(false);
        hsSubmitP1.setOnAction(e -> {
            if (!submittedP1) {
                String name = hsNameP1.getText().trim();
                if (!name.isEmpty() && highScoreStore != null)
                    highScoreStore.add(name, p1.score, buildConfigString() + " (P1)");
                submittedP1 = true;
                hsSubmitP1.setDisable(true);
                hsPaneP1.setVisible(false);
                hsPaneP1.setMaxWidth(220);
                hsNameP1.setMinWidth(220);
                if (p2 != null && !p2.gameOver) gameCanvas2.requestFocus();
                else gameCanvas1.requestFocus();
            }
        });
        hsPaneP1 = new VBox(8, new Label("High Score (P1)"), hsNameP1, hsSubmitP1);
        hsPaneP1.setAlignment(Pos.CENTER);
        hsPaneP1.setStyle("-fx-background-color: rgba(0,0,0,0.65); -fx-padding: 12; -fx-background-radius: 8;");
        hsPaneP1.setVisible(false);
        StackPane.setAlignment(hsPaneP1, Pos.CENTER);
        boardStack1.getChildren().add(hsPaneP1);

        if (boardStack2 != null) {
            hsNameP2 = new TextField();
            hsNameP2.setPromptText("Name (P2)");
            hsNameP2.setPrefColumnCount(12);
            hsNameP2.setMaxWidth(180);
            hsSubmitP2 = new Button("Submit P2");
            hsSubmitP2.setFocusTraversable(false);
            hsSubmitP2.setOnAction(e -> {
                if (!submittedP2) {
                    String name = hsNameP2.getText().trim();
                    if (!name.isEmpty() && highScoreStore != null)
                        highScoreStore.add(name, p2 != null ? p2.score : 0, buildConfigString() + " (P2)");
                    submittedP2 = true;
                    hsSubmitP2.setDisable(true);
                    hsPaneP2.setVisible(false);
                    hsPaneP2.setMaxWidth(220);
                    hsPaneP2.setMinWidth(220);
                    if (!p1.gameOver) gameCanvas1.requestFocus();
                    else if (gameCanvas2 != null) gameCanvas2.requestFocus();
                }
            });
            hsPaneP2 = new VBox(8, new Label("High Score (P2)"), hsNameP2, hsSubmitP2);
            hsPaneP2.setAlignment(Pos.CENTER);
            hsPaneP2.setStyle("-fx-background-color: rgba(0,0,0,0.65); -fx-padding: 12; -fx-background-radius: 8;");
            hsPaneP2.setVisible(false);
            StackPane.setAlignment(hsPaneP2, Pos.CENTER);
            boardStack2.getChildren().add(hsPaneP2);
        }

        // Input focus
        for (Canvas c : (p2 == null ? new Canvas[]{gameCanvas1} : new Canvas[]{gameCanvas1, gameCanvas2})) {
            c.setFocusTraversable(true);
            c.setOnKeyPressed(input);
            c.setOnKeyReleased(input);
        }

        // Resize hooks
        rootWrapper.sceneProperty().addListener((obs, o, s) -> {
            if (s != null) {
                s.widthProperty().addListener((oo, a, b) -> resizeToAvailable(rootWrapper.getWidth(), rootWrapper.getHeight()));
                s.heightProperty().addListener((oo, a, b) -> resizeToAvailable(rootWrapper.getWidth(), rootWrapper.getHeight()));
            }
        });

        // Initial resize
        Platform.runLater(() -> {
            resizeToAvailable(rootWrapper.getWidth(), rootWrapper.getHeight());
            if (gameCanvas1 != null) gameCanvas1.requestFocus();
        });

        // Start audio & loop
        if (mediaManager != null) mediaManager.playBgMusic();
        startGameLoop();

        return rootWrapper;
    }

    // small helper to quiet “extract method” suggestion
    private BorderPane createTitleRow(Button backButton, Label gameTitle) {
        BorderPane titleRow = new BorderPane();
        titleRow.setLeft(backButton);
        titleRow.setCenter(gameTitle);
        titleRow.setPadding(new javafx.geometry.Insets(50, 10, 10, 10));
        return titleRow;
    }

    // Build P1 HUD
    private VBox buildHudForPlayer1() {
        String p1Desc = (cfgP1 == null) ? "Human" : cfgP1.name();
        // HUD labels
        Label p1PlayerLabel = new Label("Player 1: " + p1Desc);
        p1ScoreLabel = new Label("Score: 0");
        p1LevelLabel = new Label("Level: " + cfgLevel);
        p1LinesLabel = new Label("Lines: 0");
        Label controls = new Label("P1: Arrows / Space");
        VBox hud = new VBox(8, p1PlayerLabel, p1ScoreLabel, p1LevelLabel, p1LinesLabel, controls);
        hud.setAlignment(Pos.TOP_CENTER);
        return hud;
    }

    // Build P2 HUD
    private VBox buildHudForPlayer2() {
        String p2Desc = (cfgP2 == null) ? "Human" : cfgP2.name();
        Label p2PlayerLabel = new Label("Player 2: " + p2Desc);
        p2ScoreLabel = new Label("Score: 0");
        p2LevelLabel = new Label("Level: " + cfgLevel);
        p2LinesLabel = new Label("Lines: 0");
        Label controls = new Label("P2: WASD + Shift");
        VBox hud = new VBox(8, p2PlayerLabel, p2ScoreLabel, p2LevelLabel, p2LinesLabel, controls);
        hud.setAlignment(Pos.TOP_CENTER);
        return hud;
    }

    // Build info bar
    private HBox buildInfoBar() {
        p1MusicLabel = new Label((mediaManager != null && !mediaManager.isMusicMuted()) ? "Music: ON" : "Music: OFF");
        p1SfxLabel = new Label((mediaManager != null && !mediaManager.isSfxMuted()) ? "Sound: ON" : "Sound: OFF");
        Label hint = new Label("M: Toggle Music   •   N: Toggle Sound");
        HBox bar = new HBox(20, p1MusicLabel, p1SfxLabel, hint);
        bar.setAlignment(Pos.CENTER);
        return bar;
    }

    // New player state
    private PlayerState createInitialPlayerState() {
        PlayerState ps = new PlayerState();
        ps.board = new char[boardW][boardH];
        for (int x = 0; x < boardW; x++)
            for (int y = 0; y < boardH; y++)
                ps.board[x][y] = ' ';
        return ps;
    }

    // Shared piece sequence
    @SuppressWarnings("SameParameterValue")
    private List<Integer> generateSharedSequence(int length, long seed) {
        Random r = new Random(seed);
        List<Integer> seq = new ArrayList<>(length);
        for (int i = 0; i < length; i++) seq.add(r.nextInt(TETROMINOS.length));
        return seq;
    }

    // Spawn new piece
    private void spawnNewPiece(PlayerState ps) {
        ps.currentPiece = sharedSequence.get(ps.nextIndex++ % sharedSequence.size());
        ps.rotation = 0;
        ps.x = Math.max(0, boardW / 2 - 2);
        ps.y = 1;

        // Local AI plan
        if (ps == p1 && aiP1 != null && !ps.gameOver) {
            planP1 = aiP1.findBestMove(copyBoard(ps.board), ps.currentPiece);
        } else if (ps == p2 && aiP2 != null && !ps.gameOver) {
            planP2 = aiP2.findBestMove(copyBoard(ps.board), ps.currentPiece);
        }

        // External plan P1
        if (ps == p1 && extP1 != null && !ps.gameOver) {
            PureGame g = buildPureGameSnapshot(ps);
            extP1.requestMoveAsync(g).whenComplete((move, ex) -> {
                if (move == null || ex != null) {
                    javafx.application.Platform.runLater(() -> {
                        if (extWarn1 != null) extWarn1.setVisible(true);
                    });
                    return;
                }
                int desiredRot = move.opRotate() & 3;
                int[] bounds = pieceBounds(ps.currentPiece, desiredRot);
                int desiredOriginX = move.opX() - bounds[0];
                int minOrigin = -bounds[0];
                int maxOrigin = boardW - 1 - bounds[1];
                desiredOriginX = Math.max(minOrigin, Math.min(maxOrigin, desiredOriginX));
                planP1 = TetrisAI.BestMove.of(desiredOriginX, desiredRot);
                javafx.application.Platform.runLater(() -> {
                    if (extWarn1 != null) extWarn1.setVisible(false);
                });
            });
        } else if (ps == p2 && extP2 != null && !ps.gameOver) {
            // External plan P2
            PureGame g = buildPureGameSnapshot(ps);
            extP2.requestMoveAsync(g).whenComplete((move, ex) -> {
                if (move == null || ex != null) {
                    javafx.application.Platform.runLater(() -> {
                        if (extWarn2 != null) extWarn2.setVisible(true);
                    });
                    return;
                }
                int desiredRot = move.opRotate() & 3;
                int[] bounds = pieceBounds(ps.currentPiece, desiredRot);
                int desiredOriginX = move.opX() - bounds[0];
                int minOrigin = -bounds[0];
                int maxOrigin = boardW - 1 - bounds[1];
                desiredOriginX = Math.max(minOrigin, Math.min(maxOrigin, desiredOriginX));
                planP2 = TetrisAI.BestMove.of(desiredOriginX, desiredRot);
                javafx.application.Platform.runLater(() -> {
                    if (extWarn2 != null) extWarn2.setVisible(false);
                });
            });
        }
    }

    // Copy board
    private char[][] copyBoard(char[][] src) {
        char[][] dst = new char[boardW][boardH];
        for (int x = 0; x < boardW; x++) System.arraycopy(src[x], 0, dst[x], 0, boardH);
        return dst;
    }

    // Snapshot for external
    private PureGame buildPureGameSnapshot(PlayerState ps) {
        PureGame g = new PureGame();
        g.setWidth(boardW);
        g.setHeight(boardH);
        g.setCells(boardToIntRows(ps.board));
        int nextIdx = sharedSequence.get(ps.nextIndex % sharedSequence.size());
        g.setCurrentShape(shapeMatrixTrim(ps.currentPiece, 0));
        g.setNextShape(shapeMatrixTrim(nextIdx, 0));
        return g;
    }

    // Board to int rows
    private int[][] boardToIntRows(char[][] b) {
        int[][] rows = new int[boardH][boardW];
        for (int y = 0; y < boardH; y++) {
            for (int x = 0; x < boardW; x++) {
                rows[y][x] = (b[x][y] == ' ') ? 0 : 1;
            }
        }
        return rows;
    }

    // Trimmed shape matrix
    @SuppressWarnings("SameParameterValue")
    private int[][] shapeMatrixTrim(int tetIdx, int r) {
        int minX = 4, maxX = -1, minY = 4, maxY = -1;
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                char c = TETROMINOS[tetIdx][rotate(px, py, r)];
                if (c != ' ') {
                    if (px < minX) minX = px;
                    if (px > maxX) maxX = px;
                    if (py < minY) minY = py;
                    if (py > maxY) maxY = py;
                }
            }
        if (maxX < minX || maxY < minY) return new int[][]{{}};
        int w = maxX - minX + 1, h = maxY - minY + 1;
        int[][] m = new int[h][w];
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                char c = TETROMINOS[tetIdx][rotate(px, py, r)];
                if (c != ' ') m[py - minY][px - minX] = 1;
            }
        return m;
    }

    // Piece bounds
    private int[] pieceBounds(int tetIdx, int r) {
        int minX = 4, maxX = -1, minY = 4, maxY = -1;
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                char c = TETROMINOS[tetIdx][rotate(px, py, r)];
                if (c != ' ') {
                    if (px < minX) minX = px;
                    if (px > maxX) maxX = px;
                    if (py < minY) minY = py;
                    if (py > maxY) maxY = py;
                }
            }
        return new int[]{minX, maxX, minY, maxY};
    }

    // Start game loop
    private void startGameLoop() {
        // Pause toggle
        // Audio toggles
        // Time & speed
        // HUD update
        // Controls
        // Gravity & lock
        // Render
        // Game over overlays
        // Game loop timing
        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastFrame = 0;

            @Override
            public void handle(long now) {
                if (startTime < 0) startTime = now;
                if (now - lastFrame < 50_000_000) return;
                lastFrame = now;

                // Pause toggle
                if (input.consumeRelease(KeyCode.ESCAPE)) paused = !paused;

                // Audio toggles
                if (input.consumePress(KeyCode.M) && mediaManager != null) {
                    mediaManager.toggleMusic();
                    p1MusicLabel.setText(mediaManager.isMusicMuted() ? "Music: OFF" : "Music: ON");
                }
                if (input.consumePress(KeyCode.N) && mediaManager != null) {
                    mediaManager.toggleSfx();
                    p1SfxLabel.setText(mediaManager.isSfxMuted() ? "SFX: OFF" : "SFX: ON");
                }

                // Time & speed
                double elapsedSec = (now - startTime) / 1_000_000_000.0;
                int currentLevel = Math.max(1, cfgLevel + (int) (elapsedSec / 30.0));
                int timeDecrease = (int) (elapsedSec / 5.0);
                int currentFallInterval = Math.max(minFallInterval, baseFallInterval - timeDecrease);

                // HUD update
                p1ScoreLabel.setText("Score: " + p1.score);
                p1LinesLabel.setText("Lines: " + p1.lines);
                p1LevelLabel.setText("Level: " + currentLevel);
                if (p2 != null) {
                    p2ScoreLabel.setText("Score: " + p2.score);
                    p2LinesLabel.setText("Lines: " + p2.lines);
                    p2LevelLabel.setText("Level: " + currentLevel);
                }

                if (paused) {
                    renderAll(currentFallInterval);
                    return;
                }

                // Controls
                if (cfgP1 == PlayerType.HUMAN) {
                    handlePlayer1Input(p1);
                } else if (cfgP1 == PlayerType.AI || cfgP1 == PlayerType.EXTERNAL) {
                    driveAI(p1, planP1);
                }
                if (p2 != null) {
                    if (cfgP2 == PlayerType.HUMAN) {
                        handlePlayer2Input(p2);
                    } else if (cfgP2 == PlayerType.AI || cfgP2 == PlayerType.EXTERNAL) {
                        driveAI(p2, planP2);
                    }
                }

                // Gravity & lock
                stepPlayer(p1, currentFallInterval);
                if (p2 != null) stepPlayer(p2, currentFallInterval);

                // Render
                renderAll(currentFallInterval);

                // Game over overlays
                if (p1.gameOver) {
                    drawGameOver(gc1, p1.score, "P1");
                    if (!submittedP1) hsPaneP1.setVisible(true);
                }
                if (p2 != null && p2.gameOver) {
                    drawGameOver(gc2, p2.score, "P2");
                    if (!submittedP2) hsPaneP2.setVisible(true);
                }
            }
        };
        gameLoop.start();
    }

    // Handle P1 input
    private void handlePlayer1Input(PlayerState ps) {
        if (ps.gameOver) return;
        if (input.consumePress(KeyCode.LEFT) && canMove(ps, ps.rotation, ps.x - 1, ps.y)) ps.x--;
        if (input.consumePress(KeyCode.RIGHT) && canMove(ps, ps.rotation, ps.x + 1, ps.y)) ps.x++;
        if (input.consumePress(KeyCode.DOWN)) {
            if (canMove(ps, ps.rotation, ps.x, ps.y + 1)) ps.y++;
        }
        if (input.consumePress(KeyCode.UP)) {
            int nr = ps.rotation + 1;
            if (canMove(ps, nr, ps.x, ps.y)) ps.rotation = nr;
        }
        if (input.consumePress(KeyCode.SPACE)) {
            while (canMove(ps, ps.rotation, ps.x, ps.y + 1)) ps.y++;
            lockAndSpawn(ps);
        }
    }

    // Handle P2 input
    private void handlePlayer2Input(PlayerState ps) {
        if (ps.gameOver) return;
        if (input.consumePress(KeyCode.A) && canMove(ps, ps.rotation, ps.x - 1, ps.y)) ps.x--;
        if (input.consumePress(KeyCode.D) && canMove(ps, ps.rotation, ps.x + 1, ps.y)) ps.x++;
        if (input.consumePress(KeyCode.S)) {
            if (canMove(ps, ps.rotation, ps.x, ps.y + 1)) ps.y++;
        }
        if (input.consumePress(KeyCode.W)) {
            int nr = ps.rotation + 1;
            if (canMove(ps, nr, ps.x, ps.y)) ps.rotation = nr;
        }
        if (input.consumePress(KeyCode.SHIFT)) {
            while (canMove(ps, ps.rotation, ps.x, ps.y + 1)) ps.y++;
            lockAndSpawn(ps);
        }
    }

    // Gravity step
    private void stepPlayer(PlayerState ps, int currentFallInterval) {
        if (ps.gameOver) return;
        ps.fallCounter++;
        if (ps.fallCounter >= currentFallInterval) {
            ps.fallCounter = 0;
            if (canMove(ps, ps.rotation, ps.x, ps.y + 1)) ps.y++;
            else lockAndSpawn(ps);
        }
    }

    // Collision check
    private boolean canMove(PlayerState ps, int rot, int posX, int posY) {
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                char cell = TETROMINOS[ps.currentPiece][rotate(px, py, rot)];
                if (cell == ' ') continue;
                int nx = posX + px, ny = posY + py;
                if (nx < 0 || nx >= boardW || ny < 0 || ny >= boardH) return false;
                if (ps.board[nx][ny] != ' ') return false;
            }
        return true;
    }

    // Lock piece & spawn
    private void lockAndSpawn(PlayerState ps) {
        placePiece(ps, ps.currentPiece, ps.rotation, ps.x, ps.y);
        int cleared = clearLines(ps);
        if (cleared > 0 && mediaManager != null) {
            if (cleared == 4) mediaManager.playSound("tetris_clear");
            else mediaManager.playSound("line_clear");
        } else if (mediaManager != null) {
            mediaManager.playSound("drop");
        }
        spawnNewPiece(ps);
        if (!canMove(ps, ps.rotation, ps.x, ps.y)) {
            ps.gameOver = true;
            if (mediaManager != null) {
                mediaManager.stopBgMusic();
                mediaManager.playSound("game_over");
            }
        }
    }

    // Place piece
    private void placePiece(PlayerState ps, int tetromino, int rot, int posX, int posY) {
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                char cell = TETROMINOS[tetromino][rotate(px, py, rot)];
                if (cell == ' ') continue;
                int nx = posX + px, ny = posY + py;
                if (nx >= 0 && nx < boardW && ny >= 0 && ny < boardH) ps.board[nx][ny] = cell;
            }
    }

    // Clear lines
    private int clearLines(PlayerState ps) {
        int linesCleared = 0;
        for (int by = 0; by < boardH; by++) {
            boolean full = true;
            for (int bx = 0; bx < boardW; bx++) {
                if (ps.board[bx][by] == ' ') {
                    full = false;
                    break;
                }
            }
            if (full) {
                linesCleared++;
                for (int ty = by; ty > 0; ty--)
                    for (int bx = 0; bx < boardW; bx++)
                        ps.board[bx][ty] = ps.board[bx][ty - 1];
                for (int bx = 0; bx < boardW; bx++) ps.board[bx][0] = ' ';
            }
        }
        if (linesCleared == 1) ps.score += 100;
        else if (linesCleared == 2) ps.score += 300;
        else if (linesCleared == 3) ps.score += 600;
        else if (linesCleared >= 4) ps.score += 1000;
        ps.lines += linesCleared;
        return linesCleared;
    }

    // Rotate index
    private int rotate(int px, int py, int r) {
        return switch (r & 3) {
            case 0 -> py * 4 + px;
            case 1 -> 12 + py - (px * 4);
            case 2 -> 15 - (py * 4) - px;
            default -> 3 - py + (px * 4);
        };
    }

    // Render everything
    private void renderAll(int currentFallInterval) {
        renderBoard(gc1, p1);
        drawPiece(gc1, p1.currentPiece, p1.rotation, p1.x, p1.y);
        renderNextPiece(nextGc1, p1);
        renderScore(gc1, p1.score);
        drawSpeed(gc1, currentFallInterval);
        if (p2 != null) {
            renderBoard(gc2, p2);
            drawPiece(gc2, p2.currentPiece, p2.rotation, p2.x, p2.y);
            renderNextPiece(nextGc2, p2);
            renderScore(gc2, p2.score);
            drawSpeed(gc2, currentFallInterval);
        }
        if (paused) {
            drawPause(gc1);
            if (gc2 != null) drawPause(gc2);
        }
    }

    // Responsive resize
    private void resizeToAvailable(double sceneW, double sceneH) {
        double spacing = 20;
        double columns = (p2 == null) ? 1 : 2;
        double sidePerColumn = Math.max(160, Math.min(sceneW * (0.22 / columns), 260));
        double columnWidth = (sceneW - (columns - 1) * 40 - 60) / columns;
        double availW = Math.max(200, columnWidth - sidePerColumn - spacing);
        double availH = Math.max(200, sceneH - 160);

        // remove redundant temp local: assign directly
        blockSize = Math.max(12d, Math.floor(Math.min(availW / boardW, availH / boardH)));

        gameCanvas1.setWidth(boardPixelW());
        gameCanvas1.setHeight(boardPixelH());
        nextCanvas1.setWidth(sidePerColumn);
        nextCanvas1.setHeight(sidePerColumn);

        if (boardStack1 != null) boardStack1.setPrefSize(boardPixelW(), boardPixelH());
        if (boardStack2 != null) boardStack2.setPrefSize(boardPixelW(), boardPixelH());

        if (gameCanvas2 != null) {
            gameCanvas2.setWidth(boardPixelW());
            gameCanvas2.setHeight(boardPixelH());
            nextCanvas2.setWidth(sidePerColumn);
            nextCanvas2.setHeight(sidePerColumn);
        }

        renderAll(Math.max(minFallInterval, baseFallInterval));
    }

    // Draw board
    private void renderBoard(GraphicsContext gc, PlayerState ps) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, boardPixelW(), boardPixelH());
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(0.5);
        for (int bx = 0; bx <= boardW; bx++)
            gc.strokeLine(bx * cell(), 0, bx * cell(), boardPixelH());
        for (int by = 0; by <= boardH; by++)
            gc.strokeLine(0, by * cell(), boardPixelW(), by * cell());
        for (int bx = 0; bx < boardW; bx++)
            for (int by = 0; by < boardH; by++)
                if (ps.board[bx][by] != ' ')
                    drawBlock(gc, ps.board[bx][by], bx * cell(), by * cell());
    }

    // Draw active piece
    private void drawPiece(GraphicsContext gc, int tetromino, int rot, int posX, int posY) {
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                char cell = TETROMINOS[tetromino][rotate(px, py, rot)];
                if (cell != ' ') drawBlock(gc, cell, (posX + px) * cell(), (posY + py) * cell());
            }
    }

    // Draw next piece
    private void renderNextPiece(GraphicsContext ngc, PlayerState ps) {
        ngc.setFill(Color.DARKGRAY);
        ngc.fillRect(0, 0, nextCanvas1.getWidth(), nextCanvas1.getHeight());
        ngc.setFill(Color.WHITE);
        ngc.setFont(Font.font(18));
        ngc.fillText("Next:", 10, 20);

        int nextPiece = sharedSequence.get(ps.nextIndex % sharedSequence.size());

        int minX = 4, maxX = -1, minY = 4, maxY = -1;
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                if (TETROMINOS[nextPiece][rotate(px, py, 0)] != ' ') {
                    if (px < minX) minX = px;
                    if (px > maxX) maxX = px;
                    if (py < minY) minY = py;
                    if (py > maxY) maxY = py;
                }
            }
        int pieceW = Math.max(1, (maxX - minX + 1));
        int pieceH = Math.max(1, (maxY - minY + 1));

        double areaW = (ngc == nextGc1 ? nextCanvas1.getWidth() : nextCanvas2.getWidth());
        double areaH = (ngc == nextGc1 ? nextCanvas1.getHeight() : nextCanvas2.getHeight());

        double scale = 0.85 * Math.min(
                areaW / (pieceW * BLOCK_SIZE * 1.2),
                areaH / (pieceH * BLOCK_SIZE * 1.2)
        );
        double offsetX = (areaW - pieceW * BLOCK_SIZE * scale) / 2;
        double offsetY = (areaH - pieceH * BLOCK_SIZE * scale) / 2 + 10;

        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                char cell = TETROMINOS[nextPiece][rotate(px, py, 0)];
                if (cell != ' ') {
                    double drawX = offsetX + (px - minX) * BLOCK_SIZE * scale;
                    double drawY = offsetY + (py - minY) * BLOCK_SIZE * scale;
                    drawBlock(ngc, cell, (int) drawX, (int) drawY, (int) (BLOCK_SIZE * scale));
                }
            }
    }

    // Draw score
    private void renderScore(GraphicsContext gc, int score) {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(20));
        gc.fillText("Score: " + score, 10, 25);
    }

    // Draw speed
    private void drawSpeed(GraphicsContext gc, int fallInterval) {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(16));
        double speedValue = Math.round((double) baseFallInterval / fallInterval * 10) / 10.0;
        gc.fillText("Speed: " + speedValue, 10, 50);
    }

    // Draw pause
    private void drawPause(GraphicsContext gc) {
        gc.setFill(Color.color(0, 0, 0, 0.5));
        gc.fillRect(0, 0, boardPixelW(), boardPixelH());
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font(40));
        String pauseText = "PAUSED";
        Text textNode = new Text(pauseText);
        textNode.setFont(gc.getFont());
        double textWidth = textNode.getLayoutBounds().getWidth();
        double textHeight = textNode.getLayoutBounds().getHeight();
        double textX = (boardPixelW() - textWidth) / 2;
        double textY = (boardPixelH() + textHeight) / 2;
        gc.fillText(pauseText, textX, textY);
    }

    // Draw game over
    private void drawGameOver(GraphicsContext gc, int score, String tag) {
        gc.setFill(Color.color(0, 0, 0, 0.6));
        gc.fillRect(0, 0, boardPixelW(), boardPixelH());

        double cx = boardPixelW() / 2.0;
        double cy = boardPixelH() / 2.0;

        gc.setFill(Color.RED);
        gc.setFont(Font.font(36));
        String l1 = "GAME OVER";
        double l1w = new Text(l1) {{
            setFont(gc.getFont());
        }}.getLayoutBounds().getWidth();
        gc.fillText(l1, Math.max(8, cx - l1w / 2), cy - 18);

        gc.setFont(Font.font(22));
        String l2 = "(" + tag + ")";
        double l2w = new Text(l2) {{
            setFont(gc.getFont());
        }}.getLayoutBounds().getWidth();
        gc.fillText(l2, Math.max(8, cx - l2w / 2), cy + 6);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(20));
        String l3 = "Final Score: " + score;
        double l3w = new Text(l3) {{
            setFont(gc.getFont());
        }}.getLayoutBounds().getWidth();
        gc.fillText(l3, Math.max(8, cx - l3w / 2), cy + 32);

        gc.setFont(Font.font(15));
        String l4 = "Enter name to submit high score";
        double l4w = new Text(l4) {{
            setFont(gc.getFont());
        }}.getLayoutBounds().getWidth();
        gc.fillText(l4, Math.max(8, cx - l4w / 2), cy + 54);
    }

    // Draw single block
    private void drawBlock(GraphicsContext gc, char kind, int x, int y) {
        drawBlock(gc, kind, x, y, cell());
    }

    // Draw sized block
    private void drawBlock(GraphicsContext gc, char kind, int x, int y, int size) {
        Color color = switch (kind) {
            case 'I' -> Color.CYAN;
            case 'O' -> Color.YELLOW;
            case 'T' -> Color.MEDIUMPURPLE;
            case 'S' -> Color.LIMEGREEN;
            case 'Z' -> Color.RED;
            case 'J' -> Color.DODGERBLUE;
            case 'L' -> Color.ORANGE;
            default -> Color.GRAY;
        };
        gc.setFill(color);
        gc.fillRect(x, y, size, size);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, y, size, size);
    }

    // Config summary
    private String buildConfigString() {
        String size = cfgWidth + "x" + cfgHeight + "(" + cfgLevel + ")";
        String p1s = (cfgP1 == null ? "HUMAN" : cfgP1.name());
        if (!cfgExtended) return size + " " + p1s + " Single";
        String p2s = (cfgP2 == null ? "HUMAN" : cfgP2.name());
        return size + " " + p1s + " + " + p2s;
    }

    // Keyboard state handler
    private class KeyInputHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent e) {
            if (e.getEventType() == KeyEvent.KEY_PRESSED) keys.put(e.getCode(), true);
            else if (e.getEventType() == KeyEvent.KEY_RELEASED) keys.put(e.getCode(), false);
        }

        boolean consumePress(KeyCode code) {
            if (Boolean.TRUE.equals(keys.get(code))) {
                keys.put(code, false);
                return true;
            }
            return false;
        }

        @SuppressWarnings("SameParameterValue")
            // currently only used with ESCAPE
        boolean consumeRelease(KeyCode code) {
            if (Boolean.FALSE.equals(keys.get(code))) {
                keys.remove(code);
                return true;
            }
            return false;
        }
    }

    // Drive AI plan
    private void driveAI(PlayerState ps, TetrisAI.BestMove plan) {
        if (ps.gameOver || plan == null) return;
        int desiredRot = plan.rotation() & 3;
        if ((ps.rotation & 3) != desiredRot) {
            int nr = ps.rotation + 1;
            if (canMove(ps, nr, ps.x, ps.y)) {
                ps.rotation = nr;
            } else {
                if (canMove(ps, ps.rotation, ps.x + 1, ps.y)) {
                    ps.x++;
                    return;
                }
                if (canMove(ps, ps.rotation, ps.x - 1, ps.y)) {
                    ps.x--;
                    return;
                }
            }
            return;
        }

        int targetX = plan.col();
        if (ps.x < targetX) {
            if (canMove(ps, ps.rotation, ps.x + 1, ps.y)) ps.x++;
        } else if (ps.x > targetX) {
            if (canMove(ps, ps.rotation, ps.x - 1, ps.y)) ps.x--;
        } else {
            while (canMove(ps, ps.rotation, ps.x, ps.y + 1)) ps.y++;
            lockAndSpawn(ps);
        }
    }
}
