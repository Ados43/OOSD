# Architecture Highlights
- State class handles board and game state
- Observer pattern decouples UI from game logic
- Position record used for tetromino coordinates and transformations
- Custom exceptions for clean error handling without state changes
- IO class centralizes input handling with configurable key bindings

## Core Structure
- Interfaces and data classes
- Tetromino class is the most complex
- State class is the heart
- Game class handles main game loop

```Psuedocode
start()
new SystemProcess
  gameThread = new Thread("GameLogic")
  renderThread = new Thread("Renderer")
new ViewModule
  mainContainer = new BorderPane()
  subModules = new HashMap<>()
  this.menuController = new MenuController(this)
  this.gameController = new GameCotnroller(this)
  this.configController = new ConfigControler(this)
  this.leaderboardController = new LeaderboardController(this)
  initializeSubModules()
    subModules.put("splash", new SplashSubModule())
    subModules.put("menu", new MenuSubModule(menuController)
      this.controller = menuController
    subModule.put("game", new GameSubModule(gameController))
      ...
    ...
  setupMainScene()
    scene = new Scene(mainContainer, 1024, 768)
    scene.getStylesheets()...
    primaryStage.setScene(scene)
systemProcess.initialize()
  running.set(true)
  config = ioHandler.loadConfig()
  renderLoop = new Timeline (new KeyFrame(Duration.millis(1000.0 / 60), e-> renderFrame()
  renderLoop.setCycleCounter(Timeline.INDEFINITE)
  viewModule.showSplash()
    switchToSubModule("splash")
      module = subModules.get(moduleName)
      Platforn.runLater(()-> mainContainer.setCenter(module.getView())
      module.onShow()
  runInitializationTests()
    Platform.runLater(()-> viewModule.showMainMenu())
      switchToSubModule("menu")
        module = subModules.get(moduleName)
        Platforn.runLater(()-> mainContainer.setCenter(module.getView())
        module.onShow()
primaryStage.show()
@Override createView
  menuBox = Vbox(20)
  menuBox.setAlignment(Pos.CENTER)
  menuBox.setStyleClass().add("menu-container")
  titleLabel = new Label("TETRIS")
  titleLabel.getStyleClass().add("game-title");
  playButton = createMenuButton("PLAY", controller::handlePlay)
    button = new Button(text)
    button.getStyleClass().add("menu-button")
    button.setOnAction(e -> { action.run() }
      System.out.println("[EXECUTIVE] Menu -> Play: Initiating new game")
      viewModule.getSystemProcess().startGame();
        game = new Game(config)
        ioHandler.setupInputHandling(game)
        gameThread.submit(() -> {
            try {
                game.start();
                   synchronized (gameLock) {
                   active.set(true);
                   state.changeState(GameState.PLAYING); <------------------------------------ @here
                   timer.start();
                   System.out.println("[GAME] Game started - Difficulty: " + config.difficulty()); }
                startRenderLoop();
                Platform.runLater(() -> renderLoop.play())
            } catch (Exception e) {
                handleGameError(e);
            } } );
        viewModule.showGameView();
          switchToSubModule("game");
          primaryStage.getScene().setOnKeyPressed(systemProcess.getIO()::handleKeyPress);
            Runnable action = keyBindings.get(event.getCode());
             if (action != null) { action.run(); }

  leaderboardButton = createMenuButton("LEADERBOARD", controller::handleLeaderboard);
    ...
  configButton = createMenuButton("CONFIG", controller::handleConfig);
    ...
  exitButton = createMenuButton("EXIT", controller::handleExit);
    ...
  menuBox.getChildren().addAll(titleLabel, playButton, leaderboardButton, configButton, exitButton);
  return menuBox
```

- Need to set up splash view to display during Thread.sleep;
- Observer patterns not implemented yet.
- Most difficult section whill be gameState stuff.
- Get the splash view then menu then others working before play view.
- 