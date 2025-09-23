# Project Plan

## PM-1.1 Work Breakdown Structure

The project implements all functional requirements shown in the demo video for a JavaFX Tetris game and packages the result as portfolio-ready material. Work covers project planning, implementation, testing, and submission only.

1. Project Management
    - 1.1 Define Project Scope
    - 1.2 Track Deliverables
    - 1.3 Manage Requirements
    - 1.4 Review Progress

2. Requirements Analysis
    - 2.1 Functional Requirements
      - 2.1.1 Screen Views
      - 2.1.2 Player Controls
      - 2.1.3 System Behavior
      - 2.1.4 Game Behavior
    - 2.2 Non-Functional Requirements
    - 2.3 Documentation

3. Design
    - 3.1 UML Diagrams
    - 3.2 Software Architecture
    - 3.3 File Structure

4. Implementation
    - 4.1 GitHub Setup
    - 4.2 Core Development
    - 4.3 Testing
    - 4.4 Bug Fixes

5. Submission
    - 5.1 Final Review
    - 5.2 Demo Video
    - 5.3 Documentation Package
    - 5.4 Submission Upload

---

## PM-1.2 Deliverables (D)

| D-ID | Deliverable                 | High-Level Description                                      | PM-Link | Dependencies |
|:-----|-----------------------------|-------------------------------------------------------------|---------|--------------|
| D-01 | Work Breakdown Structure    | Brief activity and impact statement.                        | PM-1.1  |              |
| D-02 | Deliverables List           | List of work products.                                      | PM-1.2  | PM-1.1       |
| D-03 | Functional Requirements     | Demo video user stories.                                    | PM-2    | PM-1.1       |
| D-04 | Non-Functional Requirements | Usability, reliability, performance, security requirements. | PM-3    | PM-2         |
| D-05 | Peer Reviews                | Self and group reviews.                                     | PM-4    | PM-6         |
| D-06 | Software Architecture       | UML Documentation                                           | PM-5    | PM-3         |
| D-07 | Implementation              | Output codebase.                                            | PM-6    | PM-5         |
| D-08 | Demo Video                  | Final PDF document.                                         | PM-7    | PM-6         |


---

## Functional Requirements (FR)

### PM-2.1 Application

#### FR-1 Screen Views

| FR-ID    | Item Name            | Description                                                                                                                                         |  Precondition   |  Postcondition   |   Status    | 
|:---------|:---------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------|:---------------:|:----------------:|:-----------:|
| FR-1     | Views                | Defines the player interface consisting of multiple views with controls, information, and transition conditions.                                    |   App starts    | App initialized  |  COMPLETE   |
| FR-1.1   | mainView             | Displays the menu view with buttons to start a new game, change configuration settings, view high-scores, and close the application.                | App initialized |   Menu visible   |  COMPLETE   |    
| FR-1.1.1 | mainPlayBtn          | When the player uses the play button, the menu view closes, the game view opens, and a new game starts.                                             |  Menu visible   |   Game running   |  COMPLETE   |    
| FR-1.1.2 | mainConfigBtn        | When the player clicks the configuration button, the menu view closes and the configuration view opens.                                             |  Menu visible   |   Config open    |  COMPLETE   |    
| FR-1.1.3 | mainScoreBtn         | When the player clicks the high-score button, the menu view closes and the high-score view opens                                                    |  Menu visible   |   Scores open    |  COMPLETE   |     
| FR-1.1.4 | mainExitBtn          | When the player clicks the high-score button, the menu view closes and the high-score view opens.                                                   |  Menu visible   |    App closed    |  COMPLETE   |     
| FR-1.2   | playView             | Displays  and starts the game play view, including the game board, score, and an exit button.                                                       |  Menu visible   |   Game visible   |  COMPLETE   |    
| FR-1.2.1 | playGameView         | Displays  and starts the game play view, including the game board, score, and an exit button.                                                       |  Game running   |   Gameplay on    |  COMPLETE   |     
| FR-1.2.2 | playScoreView        | The game board displays the play area at the configured settings and renders tetrominoes real-time within the moveable play area.                   |  Game running   |   Score shown    |  COMPLETE   |     
| FR-1.2.3 | playExitBtn          | When the player clicks the Exit button, the game pauses and a confirmation dialog opens.                                                            |  Game running   |   Dialog Open    |  COMPLETE   |     
| FR-1.2.4 | playExitDialog       | When the player clicks the confirm option in the exit confirmation, the dialog closes, game ends, play view closes, and the menu view opens.        |   Dialog open   |   Menu Visible   |  COMPLETE   |     
| FR-1.2.5 | playNewScoreDialog   | When the player achieves a high score prompt for name.                                                                                              |    Game over    |   Name Entered   |  COMPLETE   |     
| FR-1.3   | configView           | Displays the configuration view with options for game board size (width × height), difficulty level, music option, AI option, Extended mode option. |  Menu visible   |   Config open    |  COMPLETE   |     
| FR-1.3.1 | configWidthView      | When the player changes the width field, the in-game width setting is updated.                                                                      |   Config open   |    Width set     | PLACEHOLDER |     
| FR-1.3.2 | configHeightView     | When the player changes the height field, the in-game height setting is updated.                                                                    |   Config open   |    Height set    | PLACEHOLDER |     
| FR-1.3.3 | configDifficultyView | When the player adjusts the slider, the in-game difficulty level is updated.                                                                        |   Config open   |  Difficulty Set  | PLACEHOLDER |     
| FR-1.3.4 | configMusicView      | When the player switches the toggle option it enables or disables the background music.                                                             |   Config open   |    Music Set     | PLACEHOLDER |     
| FR-1.3.5 | configAiView         | When the player switches the AI toggle option it enables or disables the in-game AI setting.                                                        |   Config open   |      AI set      | PLACEHOLDER |     
| FR-1.3.6 | configExtendedView   | When the player switches the Extended toggle option it enables or disables the in-game extended setting.                                            |   Config open   |   Extended set   | PLACEHOLDER |     
| FR-1.3.7 | configExitBtn        | When the player clicks the Back button, the configuration view closes and the menu view opens.                                                      |   Config open   |   Menu Visible   |  COMPLETE   |     
| FR-1.4   | scoreView            | Displays the high-score view and loads, then displays ten names and scores.                                                                         |  Menu Visible   |   Scores open    |  COMPLETE   |     
| FR-1.4.1 | scoreTopScoresView   | The window displays scores next to each name.                                                                                                       |   Scores open   |   Scores shown   |  COMPLETE   |     
| FR-1.4.2 | scoreTopNamesView    | The window loads and displays the top 10 names from the high-score file.                                                                            |   Scores open   |   Names shown    |  COMPLETE   |     
| FR-1.4.3 | scoreExitBtn         | When the player clicks the Back button, the high-score view closes and the menu view opens.                                                         |   Scores open   |   Menu visible   |  COMPLETE   |     
| FR-1.5   | splashView           | Displays a centered splash window containing the course code, authors, game title, and application details (language and version).                  |   App starts    |   Menu visible   |  COMPLETE   |     
   

### PM-2.2 Player Controls

| FR-ID  | Item Name          |                                                   Description                                                    | Precondition  | Postcondition |  Status  |  
|:-------|:-------------------|:----------------------------------------------------------------------------------------------------------------:|:-------------:|:-------------:|:--------:|
| FR-2   | Input              |                Defines the keyboard input controls for rotating, moving, and changing game state.                | Game running  | Input handled | COMPLETE |     
| FR-2.1 | keyUpInput         |                        When the player presses the Up key, the current tetromino rotates.                        | Game running  | Piece rotated | COMPLETE |      
| FR-2.2 | keyDownInput       |                    When the player presses the Down key, the current tetromino falls faster.                     | Game running  |  piece moved  | COMPLETE |       
| FR-2.3 | keyLeftInput       |                When the player presses the Left key, the current tetromino attempts to move left.                | Game running  |  Piece moved  | COMPLETE |       
| FR-2.4 | keyRightInput      |               When the player presses the right key, the current tetromino attempts to move right.               | Game running  |  Piece moved  | COMPLETE |     
| FR-2.5 | keyPInput          |             When the player presses the 'p' key, the game pauses and a paused message is displayed.              | Game running  |  Game paused  | COMPLETE |     
| FR-2.6 | keyEscInput        | When the player presses the 'esc' key, a confirmation dialog opens and the game is paused if it is not already.  | Game running  |  Dialog open  | COMPLETE |      

### PM-2.3 Application Behaviour

| FR-ID  | Item Name                |                                                           Description                                                           | Precondition | Postcondition |  Status   |
|:-------|:-------------------------|:-------------------------------------------------------------------------------------------------------------------------------:|:------------:|:-------------:|:---------:| 
| FR-3   | TetrisApp                |                            Defines the gameplay logic, including scoring, pausing, and tetrominoes.                             | Game running |  Game active  | COMPLETE  |        
| FR-3.1 | gameShapeSelectionModule |    Tetrominoes are modeled in the seven standard shapes, represented by the letters I, O, T, S, Z, J, L. Selected at random.    | Game running |  Piece shown  | COMPLETE  |         
| FR-3.4 | gamePauseState           |                           When the game is paused, the game actions stop and a message is displayed.                            | Game running |  Game paused  | COMPLETE  |         
| FR-3.5 | gameOverState            |                                         When tetrominoes land while spawning end game.                                          | Game running |   Game over   | COMPLETE  |        
| FR-3.6 | gameRowUpdate            | When the player completes a row of tetrominoes, it removes the row and increases the score based on the number of rows removed. | Game running | Score updated | COMPLETE  |        
| FR-3.6 | gameSingleUpdate         |                                   When one row is removed the score increases by 100 points.                                    | Game running | Score updated | COMPLETE  |          
| FR-3.7 | gameDoubleUpdate         |                                  When two rows are removed the score increases by 300 points.                                   | Game running | Score updated | COMPLETE  |          
| FR-3.8 | gameTripleUpdate         |                                  When two rows are removed the score increases by 300 points.                                   | Game running | Score updated | COMPLETE  |          
| FR-3.9 | gameTetrisUpdate         |                                 When four rows are removed the score increases by 1200 points.                                  | Game running | Score updated | COMPLETE  |          

---

## Non-Functional Requirements (NFR)

| NFR-ID  | Category    | Requirement Description                                                          | Pass Criteria                                                                        |
|:--------|-------------|----------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|
| NFR-USA | Usability   | The system must be intuitive for first time players with a consistent interface. | Players can start the game with a single menu click. Menu items are clearly labeled. | 
| NFR-REL | Reliability | Score and state consistency across app focus changes.                            | No piece duplication or score/timer changes when app focus changes.                  | 
| NFR-PER | Performance | Stable frame rate.                                                               | Frame rate stays within 5 frames of set value.                                       | 
| NFR-SEC | Security    | No unhandled exceptions.                                                         | 0 unhandled exceptions crash the app.                                                | 

## Role Descriptions (TM)

| TM-ID  |     Role / Title     |                                   Responsibilities                                   | Assigned To | 
|:-------|:--------------------:|:------------------------------------------------------------------------------------:|:-----------:| 
| TM-01  |   Project Manager    |    Define the project scope, assumptions, constraints, and manage scope changes.     |      -      |
| TM-02  | Requirements Analyst | Identify functional and non-functional requirements based on the initial demo video. |      -      |
| TM-03  |     UML Designer     |                  Create UML documentation (use case, activity etc.)                  |      -      |     
| TM-04  |      Developer       |                Write and integrate Java code following PM standards.                 |      -      |
| TM-05  |   Demo Coordinator   |                         Record and deliver final demo video.                         |      -      |    

---

## Peer Review Process

### PM-4.1 Self Reviews

    What was your primary role in this milestone?
    
    What specific tasks or deliverables did you complete?
    
    What challenges, if any, did you face while contributing to the project?
    
    What is one aspect of your contribution that you would like to improve in the next milestone?
    
    On a scale of 1 to 10, how would you rate your overall contribution to the team?

### PM-4.2 Self Reflection

    Was the peer review process fair and helpful in identifying your contributions and role within the team?
    
    Did the feedback you received help you identify any areas for personal or team improvement?
    
    On a scale of 1 to 10, how useful was this peer review cycle for your learning and team development?

---

## Software Architecture (UML)

### PM-5.1 Activity Diagram

### PM-5.2 Use Case Diagram

---

## Implementation

### PM-6.1 File Structure

### PM-6.2 Code Snippets

## PM-7 Final Submission Link
