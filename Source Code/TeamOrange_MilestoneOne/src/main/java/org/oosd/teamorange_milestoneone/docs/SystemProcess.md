# SystemProcess Class Documentation

## Threading Architecture

### Core Threads

| Thread | Purpose | Responsibilities |
|--------|---------|------------------|
| **Game Thread** | Game Logic | Collision detection, scoring, state management, piece movement |
| **Render Thread** | Display Updates | 60 FPS rendering pipeline, UI coordination |
| **JavaFX Thread** | UI Events | Event handling, final rendering via `Platform.runLater()` |

### Thread Communication

```mermaid
A[SystemProcess] --> B[Game Thread]
A --> C[Render Thread]
B --> D[Game Logic Updates]
C --> E[60 FPS Rendering]
E --> F[JavaFX Thread]
F --> G[UI Updates]
D --> H[Shared Game State]
E --> H
```

## System Lifecycle

### 1. Initialization Phase
- Load configuration from JSON files
- Setup executor threads with proper naming
- Initialize ViewModule connection
- Display splash screen
- Transition to main menu

### 2. Runtime Phase
- Coordinate game logic updates (variable timing)
- Maintain 60 FPS rendering loop (16.67ms intervals)
- Handle user input and system events
- Manage pause/resume functionality

### 3. Shutdown Phase
- Stop all running timelines
- Terminate executor threads gracefully
- Save configuration and game state
- Clean up system resources

## Thread Safety

### Synchronization Strategy
- `AtomicBoolean` for simple state flags
- Executor service isolation for thread safety
- Proper JavaFX thread coordination via `Platform.runLater()`

### Safe Operations
```java
// Example of thread-safe game state access
public void startGame() {
    gameThread.submit(() -> {
        try {
            game.start();
            Platform.runLater(() -> startRenderLoop());
        } catch (Exception e) {
            handleGameError(e);
        }
    });
}
```

## Usage Examples

### Basic Initialization
```java
SystemProcess systemProcess = new SystemProcess();
systemProcess.setViewModule(viewModule);
systemProcess.initialize();
```

### Game Lifecycle Management
```java
// Starting a new game
systemProcess.startGame();

// Pausing during gameplay
systemProcess.pauseGame();

// Resuming from pause
systemProcess.resumeGame();

// Ending and cleanup
systemProcess.endGame();
```

### Configuration Updates
```java
GameConfig newConfig = new GameConfig(8, true, false, 12, 22, keyBindings);
systemProcess.updateConfig(newConfig);
```

## Class Signature

```java
public class SystemProcess {
    private final ExecutorService gameThread;
    private final ExecutorService renderThread;
    private final AtomicBoolean running;
    private Timeline renderLoop;
    
    // Core components
    private Game game;
    private IO ioHandler;
    private ViewModule viewModule;
    private GameConfig config;
    
    // Frame rate management
    private static final long FRAME_TIME_NS = 1_000_000_000L / 60;
}
```

