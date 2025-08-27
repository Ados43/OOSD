# Project Plan

## PM-1.1 Project Scope

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

| D-ID | Deliverable                               | High-Level Description                        | Input Work Products | Dependencies | Milestone        | Status |  
|:-----|-------------------------------------------|-----------------------------------------------|---------------------|--------------|------------------|:------:|
| D-01 | Project Scope Description                 | Brief activity and impact statement.          | WP-01               |              | PM-Documentation |  Done  |

---

## Functional Requirements (FR)

### PM-2.1 Application

#### FR-1 Screen Views

| FR-ID    | Item Name            |                  Dependencies                   |                                     Precondition                                      | Function Calls                                                                                                                                  |                       Postcondition                       | Test Case ID  | 
|:---------|:---------------------|:-----------------------------------------------:|:-------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------------------------------------------|:---------------------------------------------------------:|:-------------:|
| FR-1.1   | mainView             | FR-1.1.1 <br>FR-1.1.2 <br>FR-1.1.3 <br>FR-1.1.4 |                        viewChangeModule.mainView() <br>noError                        | MainView() <br>MainView.refresh()                                                                                                               |                        isMainView                         |               |           |      -       |
| FR-1.1.1 | mainPlayBtn          | FR-1.1 <br>FR-1.2 <br>FR-3.2 <br>FR-3 <br>FR-4  |                                isMainView <br>noError                                 | viewChangeModule.playView() <br>gameConfigModule.refresh() <br>gameStateModule.new() <br>gameRenderModule.new() <br>systemDisplayModule.start() |           isPlayView <br> isPlaying <br>noError           |               |     -     |      -       |
| FR-1.1.2 | mainConfigBtn        |                        -                        |                                isMainView <br>noError                                 | viewChangeModule.configView() <br>systemSaveModule.Config.refresh()                                                                             |                 isConfigView <br>noError                  |       -       |     -     |      -       |
| FR-1.1.3 | mainScoreBtn         |                        -                        |                                isMainView <br>noError                                 | viewChangeModule.scoreView()  <br>systemSaveModule.Leaderboard.refresh()                                                                        |                  isScoreView <br>noError                  |       -       |     -     |      -       |
| FR-1.1.4 | mainExitBtn          |                        -                        |                                isMainView <br>noError                                 | changeViewModule.exit()                                                                                                                         |                      EXIT_CONDITION                       |       -       |     -     |      -       |
| FR-1.2   | playView             |                        -                        |                                                                                       |                                                                                                                                                 |                                                           |       -       |     -     |      -       |
| FR-1.2.1 | playGameView         |                        -                        |                                                                                       |                                                                                                                                                 |                                                           |       -       |     -     |      -       |
| FR-1.2.2 | playScoreView        |                        -                        |                                                                                       |                                                                                                                                                 |                                                           |       -       |     -     |      -       |
| FR-1.2.3 | playExitBtn          |                        -                        |                                                                                       |                                                                                                                                                 |                                                           |       -       |     -     |      -       |
| FR-1.2.4 | playExitDialog       |                        -                        |                                                                                       |                                                                                                                                                 |                                                           |       -       |     -     |      -       |
| FR-1.2.5 | playNewScoreDialog   |                        -                        |                                                                                       |                                                                                                                                                 |                                                           |       -       |     -     |      -       |
| FR-1.3   | configView           |                        -                        |    viewChangeModule.configView() <br>systemSaveModule.Config.refresh() <br>noError    | ConfigView() <br>ConfigView.refresh()                                                                                                           |                 isConfigView <br>noError                  |       -       |     -     |      -       |
| FR-1.3.1 | configWidthView      |                        -                        |          isConfigView <br>systemConfigModule.WidthState.return() <br>noError          | systemConfigModule.WeightState.set(x)                                                                                                           |         systemConfigModule.hasChanges <br>noError         |       -       |     -     |      -       |
| FR-1.3.2 | configHeightView     |                        -                        |         isConfigView <br>systemConfigModule.HeightState.return() <br>noError          | systemConfigModule.HeightState.set(x)                                                                                                           |         systemConfigModule.hasChanges <br>noError         |       -       |     -     |      -       |
| FR-1.3.3 | configDifficultyView |                        -                        |       isConfigView <br>systemConfigModule.DifficultyState.return() <br>noError        | systemConfigModule.DifficultyState.set(x)                                                                                                       |         systemConfigModule.hasChanges <br>noError         |       -       |     -     |      -       |
| FR-1.3.4 | configMusicView      |                        -                        |          isConfigView <br>systemConfigModule.MusicState.return() <br>noError          | systemConfigModule.MusicState.toggle()                                                                                                          |         systemConfigModule.hasChanges <br>noError         |       -       |     -     |      -       |
| FR-1.3.5 | configAiView         |                        -                        |           isConfigView <br>systemConfigModule.AIState.return() <br>noError            | systemConfigModule.AIState.toggle()                                                                                                             |         systemConfigModule.hasChanges <br>noError         |       -       |     -     |      -       |
| FR-1.3.6 | configExtendedView   |                        -                        |        isConfigView <br>systemConfigModule.ExtendedState.return() <br>noError         | systemConfigModule.ExtendedState.toggle()                                                                                                       |         systemConfigModule.hasChanges <br>noError         |       -       |     -     |      -       |
| FR-1.3.7 | configExitBtn        |                        -                        |                               isConfigView <br>noError                                | systemConfigModule.saveFile() <br>viewChangeModule.mainView()                                                                                   | isMenuView <br>systemConfigModule.hasNoChange <br>noError |       -       |     -     |      -       |
| FR-1.4   | scoreView            |                        -                        | viewChangeModule.scoreView() <br> systemSaveModule.Leaderboard.refresh() <br> noError | ScoreView() <br>ScoreView.refresh()                                                                                                             |                  isScoreView <br>noError                  |       -       |     -     |      -       |
| FR-1.4.1 | scoreTopScoresView   |                        -                        |          isScoreView <br> systemLeaderboardModule.returnScores() <br>noError          | systemLeaderboardModule.returnScores()                                                                                                          |                 scoresVisible <br>noError                 |       -       |     -     |      -       |
| FR-1.4.2 | scoreTopNamesView    |                        -                        |          isScoreView <br> systemLeaderboardModule.returnNames() <br>noError           | systemLeaderboardModule.returnNames()                                                                                                           |                 namesVisible <br>noError                  |       -       |     -     |      -       |
| FR-1.4.3 | scoreExitBtn         |                        -                        |                                isScoreView <br>noError                                | viewChangeModule.mainView()                                                                                                                     |                  isMainView <br>noError                   |       -       |     -     |      -       |
| FR-1.5   | splashView           |                        -                        |                                viewChangeModule.init()                                | SplashView() <br>SplashView.refresh() <br>splashView.wait() <br>viewChangeModule.mainView()                                                     |                  isMainView <br>noError                   |       -       |     -     |      -       |
| FR-1.5.1 | splashCodeView       |                        -                        |                               isSplashView <br>noError                                | SplashView.returnCode()                                                                                                                         |               splashCodeVisible <br>noError               |       -       |     -     |      -       |
| FR-1.5.2 | splashAuthorView     |                        -                        |                               isSplashView <br>noError                                | SplashView.returnAuthor()                                                                                                                       |              splashAuthorVisible <br>noError              |       -       |     -     |      -       |
| FR-1.5.3 | splashTitleView      |                        -                        |                               isSplashView <br>noError                                | splashView.returnTitle()                                                                                                                        |              splashTitleVisible <br>noError               |       -       |     -     |      -       |
| FR-1.5.4 | splashPositionView   |                        -                        |                               isSplashView <br>noError                                | SplashView.center()                                                                                                                             |             splashPositionCenter <br>noError              |       -       |     -     |      -       |

### PM-2.2 Player Controls

#### FR-2 Keyboard Inputs

| FR-ID  | Item Name          | Dependencies | Precondition | Function Calls | Postcondition | Test Case ID | 
|:-------|:-------------------|:------------:|:------------:|:--------------:|:-------------:|:------------:|
| FR-2.1 | keyUpInput         |      -       |              |                |       -       |      -       |  
| FR-2.2 | keyDownInput       |      -       |              |                |       -       |      -       |  
| FR-2.3 | keyLeftInput       |      -       |              |                |       -       |      -       |  
| FR-2.4 | keyRightInput      |      -       |              |                |       -       |      -       |  
| FR-2.5 | keyPInput          |      -       |              |                |       -       |      -       | 
| FR-2.6 | keyEscInput        |      -       |              |                |       -       |      -       |   
| FR-2.7 | keyboardScoreInput |      -       |              |                |       -       |      -       |   

### PM-2.3 Application Behaviour

#### PM-2.3.1 System Behaviour

##### FR-3 System Module

| FR-ID  | Item Name                 | Dependencies | Precondition | Function Calls | Postcondition | Test Case ID |
|:-------|:--------------------------|:------------:|:------------:|:--------------:|:-------------:|:------------:|
| FR-3.1 | systemSaveModule          |      -       |              |                |       -       |      -       |     
| FR-3.2 | systemDisplayGameModule   |      -       |              |                |       -       |      -       |    
| FR-3.3 | systemConfigurationModule |      -       |              |                |       -       |      -       |     
| FR-3.4 | systemLeaderboardModule   |      -       |              |                |       -       |      -       |    
| FR-3.5 | viewChangeModule          |      -       |              |                |       -       |      -       |    

#### PM-2.3.2 Game Behaviour

##### FR-4 Game Modules

| FR-ID    | Item Name                | Dependencies | Precondition | Function Calls | Postcondition | Test Case ID |
|:---------|:-------------------------|:------------:|:------------:|:--------------:|:-------------:|:------------:|
| FR-4.1   | gameConfigModule         |      -       |              |                |       -       |      -       |    
| FR-4.2   | gameRenderModule         |      -       |              |                |       -       |      -       |      
| FR-4.3   | gameShapeSelectionModule |      -       |              |                |       -       |      -       |     
| FR-4.4   | gameShapeMovementModule  |      -       |              |                |       -       |      -       |     
| FR-4.5   | gameStateModule          |      -       |              |                |       -       |      -       |     
| FR-4.5.1 | gamePauseState           |      -       |              |                |       -       |      -       |     
| FR-4.5.2 | gameOverState            |      -       |              |                |       -       |      -       |    
| FR-4.5.3 | gameNewScoreState        |      -       |              |                |       -       |      -       |     
| FR-4.6   | gameRowUpdate            |      -       |              |                |       -       |      -       |     
| FR-4.6.1 | gameSingleUpdate         |      -       |              |                |       -       |      -       |     
| FR-4.6.2 | gameDoubleUpdate         |      -       |              |                |       -       |      -       |     
| FR-4.6.3 | gameTripleUpdate         |      -       |              |                |       -       |      -       |     
| FR-4.6.4 | gameTetrisUpdate         |      -       |              |                |       -       |      -       |      

---

## Non-Functional Requirements (NFR)

| NFR-ID  | Category    | Requirement Description                                                          | Pass Criteria                                                                        |
|:--------|-------------|----------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|
| NFR-USA | Usability   | The system must be intuitive for first time players with a consistent interface. | Players can start the game with a single menu click. Menu items are clearly labeled. | 
| NFR-REL | Reliability | Score and state consistency across app focus changes.                            | No piece duplication or score/timer changes when app focus changes.                  | 
| NFR-PER | Performance | Stable frame rate.                                                               | Frame rate stays within 5 frames of set value.                                       | 
| NFR-SEC | Security    | No unhandled exceptions.                                                         | 0 unhandled exceptions crash the app.                                                | 
| NFR-LEG | Legal       | Only permitted assets with propper licensing.                                    | License file included.                                                               |
| NFR-COM | Compliance  | Meets assignment rubric, uni policies, and target runtime.                       | Rubric checklist complete, academic integrity check complete. Builds to spec.        |

## Role Descriptions (TM)

| TM-ID  |     Role / Title     |                                   Responsibilities                                   | Assigned To | 
|:-------|:--------------------:|:------------------------------------------------------------------------------------:|:-----------:| 
| TM-01  |   Project Manager    |    Define the project scope, assumptions, constraints, and manage scope changes.     |      -      |
| TM-02  | Requirements Analyst | Identify functional and non-functional requirements based on the initial demo video. |      -      |
| TM-03  |     UML Designer     |                  Create UML documentation (use case, activity etc.)                  |      -      |     
| TM-04  |      Developer       |                Write and integrate Java code following PM standards.                 |      -      |
| TM-05  |   Demo Coordinator   |                         Record and deliver final demo video.                         |      -      |    

---

### Peer Review Process

#### PM-4.1 Self Reviews

    What was your primary role in this milestone?
    
    What specific tasks or deliverables did you complete?
    
    What challenges, if any, did you face while contributing to the project?
    
    What is one aspect of your contribution that you would like to improve in the next milestone?
    
    On a scale of 1 to 10, how would you rate your overall contribution to the team?

#### PM-4.2 Team Member Reviews

| Date  | Review Of  | Key Contributions | Professionalism | Suggestion | Submitted By |
|:------|:----------:|:-----------------:|:---------------:|:----------:|:------------:|
| -     |     -      |         -         |        -        |     -      |      -       |
|       |            |                   |                 |            |              |

#### PM-4.3 Self Reflection

    Was the peer review process fair and helpful in identifying your contributions and role within the team?
    
    Did the feedback you received help you identify any areas for personal or team improvement?
    
    On a scale of 1 to 10, how useful was this peer review cycle for your learning and team development?

---

## Software Architecture (UML)

### PM-5.1 Class Diagram

### PM-5.2 Activity Diagram

### PM-5.3 User Case Diagram

---

## Implementation

### PM-6.1 File Structure

### PM-6.2 Code Snippets

## PM-7 Final Submission Link
