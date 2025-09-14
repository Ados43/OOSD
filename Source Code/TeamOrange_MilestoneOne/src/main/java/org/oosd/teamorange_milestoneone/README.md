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