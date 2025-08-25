# Java: Tetris Assignment
2006ICT Object-Oriented Software Development

## Installation
1. [Prerequisite software and environment setup.]
2. [Repository download or clone procedure.]  
3. [Project build and compilation steps.]  

## How to Run
1. [Execution method from compiled classes or packaged file.]  
2. [Command-line execution process.]

## How to Play
1. Objective: Arrange falling tetrominoes to complete horizontal lines and score points without letting the stack reach the top. 
2. [Game controls and interactions.]
3. [Scoring and progression mechanics.]

---

# Table of Contents

- [Project Plan](#project-plan)
    - [PM-1 Scope Management](#pm-1-scope-management)
        - [PM-1.1 Project Management Scope (PM)](#pm-11-project-management-scope-pm)
        - [PM-1.2 Project Scope](#pm-12-project-scope)
        - [PM-1.3 Project Milestones (M)](#pm-13-project-milestones-m)
        - [PM-1.4 Deliverables (D)](#pm-14-deliverables-d)
        - [PM-1.5 Work Breakdown Structure (WBS)](#pm-15-work-breakdown-structure-wbs)
        - [PM-1.6 Verification (AC)](#pm-16-verification-ac)
    - [PM-2 Product Analysis](#pm-2-product-analysis)
        - [PM-2.1 Stakeholder Influences (SI)](#pm-21-stakeholder-influences-si)
        - [PM-2.2 Stakeholder Register (ST)](#pm-22-stakeholder-register-st)
        - [PM-2.3 Detailed Product Description](#pm-23-detailed-product-description)
        - [PM-2.4 High-Level Requirements](#pm-24-high-level-requirements)
        - [PM-2.5 User Stories (US)](#pm-25-user-stories-us)
    - [PM-3 Functional Requirements (FR)](#pm-3-functional-requirements-fr)
        - [PM-3.1 Application](#pm-31-application)
        - [PM-3.2 Player Controls](#pm-32-player-controls)
        - [PM-3.3 Application Behaviour](#pm-33-application-behaviour)
            - [PM-3.3.1 System Behaviour](#pm-331-system-behaviour)
            - [PM-3.3.2 Game Behaviour](#pm-332-game-behaviour)
    - [PM-4 Non-Functional Requirements (NFR)](#pm-4-non-functional-requirements-nfr)
        - [PM-4.1 Quality Metrics (QM)](#pm-41-quality-metrics-qm)
        - [PM-4.2 NFR Passing Criteria (URPS+)](#pm-42-nfr-passing-criteria-urps)
    - [PM-5 Time Management](#pm-5-time-management)
        - [PM-5.1 Agile Methodology](#pm-51-agile-methodology)
        - [PM-5.2 Scheduling Process (Sequencing)](#pm-52-scheduling-process-sequencing)
        - [PM-5.3 Schedule Network Diagrams (Critical Path)](#pm-53-schedule-network-diagrams-critical-path)
        - [PM-5.4 Resource Methodology (Resource Estimations)](#pm-54-resource-methodology-resource-estimations)
        - [PM-5.5 Project Schedule (Complete Task List)](#pm-55-project-schedule-complete-task-list)
        - [PM-5.6 Gantt Chart](#pm-56-gantt-chart)
    - [PM-6 HR Management](#pm-6-hr-management)
        - [PM-6.1 Project Hierarchy](#pm-61-project-hierarchy)
        - [PM-6.2 Role Descriptions (TM)](#pm-62-role-descriptions-tm)
        - [PM-6.3 Peer Review Process](#pm-63-peer-review-process)
            - [PM-6.3.1 Self Reviews](#pm-631-self-reviews)
            - [PM-6.3.2 Team Member Reviews](#pm-632-team-member-reviews)
            - [PM-6.3.3 Self Reflection](#pm-633-self-reflection)
    - [PM-7 Risk Management](#pm-7-risk-management)
        - [PM-7.1 Categories](#pm-71-categories)
        - [PM-7.2 Risk Assessment Matrix (RA)](#pm-72-risk-assessment-matrix-ra)
        - [PM-7.3 Risk Register (RI)](#pm-73-risk-register-ri)
    - [PM-8 Software Architecture (UML)](#pm-8-software-architecture-uml)
        - [PM-8.1 Structural Diagrams](#pm-81-structural-diagrams)
        - [PM-8.2 Behavioural Diagrams](#pm-82-behavioural-diagrams)
    - [PM-9 Implementation](#pm-9-implementation)
        - [PM-9.1 GitHub](#pm-91-github)
        - [PM-9.2 File Structure](#pm-92-file-structure)
        - [PM-9.3 Code Snippets](#pm-93-code-snippets)
    - [PM-10 Testing](#pm-10-testing)
        - [PM-10.1 Test Cases (TC)](#pm-101-test-cases-tc)
            - [PM-10.1.1 Unit Tests](#pm-1011-unit-tests)
            - [PM-10.1.2 Integration Tests](#pm-1012-integration-tests)
            - [PM-10.1.3 System Tests](#pm-1013-system-tests)
            - [PM-10.1.4 Acceptance Testing](#pm-1014-acceptance-testing)
        - [PM-10.2 Test Results](#pm-102-test-results)
    - [PM-11 Final Submission](#pm-11-final-submission)
        - [PM-11.1 Demo Video Link](#pm-111-demo-video-link)

# Project Plan

---

## PM-1 Scope Management
### PM-1.1 Project Management Scope (PM)

|   PM-ID   | Section Title                                | Content Description                                                          |                                         PM-Dependencies                                          | Assigned | 
|:---------:|:---------------------------------------------|:-----------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------:|:--------:|
|   PM-1    | Scope Management                             | Defines the project scope, milestones, work products and acceptance criteria |                                                -                                                 |          |      
|  PM-1.1   | Project Management (PM)                      | Table of PM work products and dependencies.                                  |                                                                                                  |    FC    | 
|  PM-1.2   | Project Scope                                | Describes scope of the project.                                              |                                              PM-1.1                                              |          |             
|  PM-1.3   | Project Milestones                           | Defines high-level objectives to indicate the project's timeline.            |                                              PM-1.2                                              |          |             
|  PM-1.4   | Deliverables                                 | Table of deliverable work products and packages with linking info.           |                                        PM-1.3 <br> PM-1.2                                        |          |             
|  PM-1.5   | Work Breakdown Structure                     | Work product list with descriptions, output, and acceptance criteria link.   |                                              PM-1.4                                              |          |             
|  PM-1.6   | Verification (Acceptance Criteria)           | Table of work products and acceptance criteria.                              |                                              PM-1.5                                              |          |             
|   PM-2    | Product Analysis                             | Describes the project stakeholders and high-level requirements.              |                                                                                                  |          |             
|  PM-2.1   | Stakeholder Influences                       | Table of stakeholder influences and impacts.                                 |                                              PM-1.2                                              |          |             
|  PM-2.2   | Stakeholder Register                         | Defines stakeholder information and risk categories.                         |                                              PM-2.1                                              |          |             
|  PM-2.3   | Detailed Product Description                 | Describes the final project deliverable in detail.                           |                                        PM-1.2 <br> PM-2.2                                        |          |             
|  PM-2.4   | High-Level Requirements                      | Defines the projects high-level requirements.                                |                                              PM-2.3                                              |          |             
|  PM-2.5   | User Stories                                 | Table of user stories covering the high-level requirements.                  |                                              PM-2.4                                              |          |             
|   PM-3    | Functional Requirements                      | Table of derived functional requirements following pre-post conditions.      |                                              PM-2.5                                              |          |             
|  PM-3.1   | Application                                  | Table of application related functional requirements.                        |                                                                                                  |          |             
|  PM-3.2   | Player Controls                              | Table of input related functional requirements.                              |                                                                                                  |          |             
|  PM-3.3   | Application Behaviour                        | Defines system and game behaviour.                                           |                                                                                                  |          |             
| PM-3.3.1  | System Behaviour                             | Table of system behaviour related functional requirements.                   |                                                                                                  |          |             
| PM-3.3.2  | Game Behaviour                               | Table of game behaviour related functional requirements.                     |                                                                                                  |          |             
|   PM-4    | Non-functional Requirements                  | Table of non-functional requirements.                                        |                                         PM-2.4 <br> PM-3                                         |          |             
|  PM-4.1   | Quality Metrics                              | Defines the quality metrics.                                                 |                                                                                                  |          |             
| PM-4.1.1  | Cohesion Maximization Metric                 | Defines cohesion metric.                                                     |                                                                                                  |          |             
| PM-4.1.2  | Coupling Minimization Metric                 | Defines coupling metric.                                                     |                                                                                                  |          |             
|  PM-4.2   | NFR Passing Criteria (URPS+)                 | Table of non-functional requirements and passing criteria.                   |                                                                                                  |          |             
|   PM-5    | Time Management                              | Describes the time management process and estimations.                       |                                                                                                  |          |             
|  PM-5.1   | Agile Methodology                            | Defines the project's agile workflow.                                        |                                                                                                  |          |             
|  PM-5.2   | Scheduling Process (Sequencing)              | Table of sequence relationships.                                             |                                              PM-1.5                                              |          |             
|  PM-5.3   | Schedule Network Diagrams (Critical Path)    | Image of schedule network diagrams.                                          |                                              PM-5.2                                              |          |             
|  PM-5.4   | Resource Methodology (Resource Estimations)  | Defines the resource estimations.                                            |                                                                                                  |          |             
| PM-5.4.1  | Time Unit Model                              | Defines the time unit model.                                                 |                                                                                                  |          |             
| PM-5.4.2  | Time Cost Estimations (Activity Estimations) | Table of activity time cost estimates.                                       |                                             PM-5.4.1                                             |          |             
|  PM-5.5   | Project Schedule (Complete Task List)        | Defines the project task list.                                               |                                             PM-5.4.2                                             |          |             
|  PM-5.6   | Gantt Chart                                  | Image of project Gantt chart.                                                |                                              PM-5.5                                              |          |             
|   PM-6    | HR Management                                | Describes the HR management process.                                         |                                                                                                  |          |             
|  PM-6.1   | Project Hierarchy                            | Image of project hierarchy.                                                  |                                              PM-1.2                                              |          |             
|  PM-6.2   | Role Descriptions                            | Table of team member role descriptions.                                      |                                              PM-6.1                                              |          |             
|  PM-6.3   | Peer Review Process                          | Defines the peer review process.                                             |                                                                                                  |          |             
| PM-6.3.1  | Self Reviews                                 | Defines the self review questions.                                           |                                                                                                  |          |             
| PM-6.3.2  | Team Member Reviews                          | Defines the team member review questions.                                    |                                                                                                  |          |             
| PM-6.3.3  | Self Reflection                              | Defines the self reflection questions.                                       |                                                                                                  |          |             
|   PM-7    | Risk Management                              | Defines the risk management process.                                         |                                                                                                  |          |             
|  PM-7.1   | Categories                                   | Table of risk categories.                                                    |                                              PM-1.2                                              |          |             
|  PM-7.2   | Risk Assessment Matrix                       | Table of risk assessments.                                                   |                                              PM-7.1                                              |          |             
|  PM-7.3   | Risk Register                                | Table of identified risks and response.                                      |                                        PM-2.2 <br> PM-7.2                                        |          |             
|  PM-8.1   | Structural Diagrams                          | Describes the system's structure.                                            |                                               PM-3                                               |          |            
| PM-8.1.1  | Class Diagram                                | Static classes, attributes, operations, and their relationships.             |                                                                                                  |          |            
| PM-8.1.2  | Object Diagram                               | Snapshot of runtime instances and links at a moment in time.                 |                                                                                                  |          |            
| PM-8.1.3  | Component Diagram                            | Software components, provided/required interfaces, and dependencies.         |                                                                                                  |          |            
| PM-8.1.4  | Deployment Diagram                           | Hardware nodes/devices and where artifacts are deployed.                     |                                                                                                  |          |           
| PM-8.1.5  | Composite Structure Diagram                  | Internal parts/ports of a classifier and their connectors.                   |                                                                                                  |          |            
| PM-8.1.6  | Package Diagram                              | Package groupings and dependency relationships between them.                 |                                                                                                  |          |             
| PM-8.1.7  | Profile Diagram                              | UML extensions via stereotypes, tagged values, and constraints.              |                                                                                                  |          |             
|  PM-8.2.  | Behavioural Diagrams                         | Describes the system's behaviour.                                            |                                               PM-3                                               |          |             
| PM-8.2.1  | Use Case Interaction Diagram                 | Actors, system use cases, and interactions at the system boundary.           |                                                                                                  |          |             
| PM-8.2.2  | Sequence Diagram                             | Time-ordered messages between lifelines/objects.                             |                                                                                                  |          |             
| PM-8.2.3  | Activity Diagram                             | Workflows with actions, decisions, and parallel flows.                       |                                                                                                  |          |             
| PM-8.2.4  | State Machine Diagram                        | Object states and event-driven transitions over its lifecycle.               |                                                                                                  |          |             
| PM-8.2.5  | Communication Diagram                        | Object links emphasizing numbered message exchanges.                         |                                                                                                  |          |             
| PM-8.2.6  | Interaction Overview Diagram                 | High-level control flow linking multiple interactions.                       |                                                                                                  |          |             
| PM-8.2.7  | Timing Diagram                               | State/value changes plotted against a time axis.                             |                                                                                                  |          |             
| PM-8.2.8  | Use Case Dependency Diagram                  | Include, extend, and generalization relations among use cases.               |                                                                                                  |          |             
|   PM-9    | Implementation                               | Describes project implementation.                                            |                                               PM-8                                               |          |             
|  PM-9.1   | GitHub                                       | Defines project GitHub lin.                                                  |                                                                                                  |          |             
|  PM-9.2   | File Structure                               | Displays GitHub file structure.                                              |                                                                                                  |          |             
|  PM-9.3   | Code Snippets                                | Table of production code snippets.                                           |                                                                                                  |          |             
|   PM-10   | Testing                                      | Describes test cases and results.                                            |                                               PM-9                                               |          |             
|  PM-10.1  | Test Cases                                   | Defines test cases.                                                          |                                                                                                  |          |             
| PM-10.1.1 | Unit Tests                                   | Verify a single class/function in isolation.                                 |                                                                                                  |          |             
| PM-10.1.2 | Integration Tests                            | Verify modules work together at their boundaries.                            |                                                                                                  |          |             
| PM-10.1.3 | System Tests                                 | Validate the whole application as a black box.                               |                                                                                                  |          |             
| PM-10.1.4 | Acceptance Tests                             | Prove it meets user stories & acceptance criteria.                           |                                                                                                  |          |             
|  PM-10.2  | Test Results                                 | Table of test results.                                                       |                                        PM-9 <br> PM-10.1                                         |          |             
|   PM-11   | Final Submission PDF                         | Defines the final submission.                                                | PM-1 <br> PM-2 <br> PM-3 <br> PM-4 <br> PM-5 <br> PM-6 <br> PM-7 <br> PM-8 <br> PM-9 <br> PM-10  |          |             
|  PM-11.1  | Demo Video Link                              | Final submission demo video link.                                            |                                                                                                  |          |             

### PM-1.2 Project Scope
The project implements all functional requirements shown in the demo video for a JavaFX Tetris game and packages the result as portfolio-ready material. Work covers project planning, implementation, testing, and submission only.

### PM-1.3 Project Milestones (M)
| M-ID | Milestone Name    | Deliverables              | Description                                               | Deadline | Status      |
|:-----|-------------------|---------------------------|-----------------------------------------------------------|----------|-------------|
| M-01 | PM-Documentation  | PM-1, 2, 3, 4, 5, 6, 7, 8 | Finalize the project plan and project management process. | 18/6/25  | In-Progress |
| M-02 | Implementation    | PM-9                      | Push the full pre-testing implementation to GitHub.       | -        | -           |
| M-03 | Testing           | PM-10                     | Push test functions and logs to GitHub.                   | -        | -           |
| M-04 | Final Submission  | PM-11                     | Finalize PDF and demo video.                              | -        | -           |

### PM-1.4 Deliverables (D)
| D-ID | Deliverable                               | High-Level Description                        | Input Work Products | Dependencies | Milestone        | Status |  
|:-----|-------------------------------------------|-----------------------------------------------|---------------------|--------------|------------------|:------:|
| D-01 | Project Scope Description                 | Brief activity and impact statement.          | WP-01               |              | PM-Documentation |  Done  |
| D-02 | Project Milestone List                    | The big dates and what happens on each.       |                     | D-01         | PM-Documentation |  Done  |
| D-03 | Complete Work Breakdown Structure         | Tree of work packages/tasks.                  |                     | D-01         | PM-Documentation |        |
| D-04 | Approved Activity List                    | Final task list.                              |                     | D-03         | PM-Documentation |        |
| D-05 | Final Acceptance Criteria                 | Checklist to confirm requirements.            |                     | D-04         | PM-Documentation |        |
| D-06 | Stakeholder Analysis                      | Who cares and how they influence the project. |                     | D-01         | PM-Documentation |        |
| D-07 | Stakeholder Register                      | Contact sheet.                                |                     | D-07         | PM-Documentation |        |
| D-08 | User Stories                              | Short “As a… I want… so that…” items.         |                     | D-07         | PM-Documentation |        |
| D-09 | Functional Requirements Specification     | Detailed features and rules.                  |                     | D-08         | PM-Documentation |  Done  |
| D-10 | Non-Functional Requirements Specification | Quality goals.                                |                     | D-11         | PM-Documentation |        |
| D-11 | Quality Metrics                           | The numbers we’ll measure.                    |                     | -            | PM-Documentation |        |
| D-12 | Time Management Plan                      | Estimations and scheduling.                   |                     | D-04         | PM-Documentation |        |
| D-13 | Project Schedule                          | Calendar of tasks.                            |                     | D-12         | PM-Documentation |        |
| D-14 | Gantt Chart                               | Visual timeline of the schedule.              |                     | D-13         | PM-Documentation |        |
| D-15 | HR Management Plan                        | Team roles and responsibilities.              |                     | -            | PM-Documentation |        |
| D-16 | Peer Reviews                              | Evidence of code reviews.                     |                     | -            | PM-Documentation |        |
| D-17 | Risk Management Plan                      | Top risks, owners, responses.                 |                     | D-07         | PM-Documentation |        |
| D-18 | UML Structural Diagrams                   | Static structure.                             |                     | -            | PM-Documentation |        |
| D-19 | UML Behavioural Diagrams                  | Dynamic structure.                            |                     | -            | PM-Documentation |        |
| D-20 | GitHub File Structure                     | Repo layout.                                  |                     | D-18,        | Implementation   |        |
| D-21 | Codebase                                  | All source code.                              |                     | M-01         | Implementation   |        |
| D-22 | Test Functions                            | JUnit tests.                                  |                     |              | Testing          |        |
| D-23 | Testing Results                           | Reports showing pass/fail.                    |                     | D-22         | Testing          |        |
| D-24 | Demo Video                                | Short video proving features work.            |                     | M-03         | Final Submission |        |
| D-25 | Final Submission PDF                      | One PDF.                                      |                     | D-24         | Final Submission |        |

### PM-1.5 Work Breakdown Structure (WBS)
| WP-ID | Work Product Name        | Description                            |  Output  |  Deliverable  |  Acceptance Criteria  | 
|:------|--------------------------|----------------------------------------|:--------:|:-------------:|:---------------------:|
| WP-00 | -                        | -                                      |    -     |       -       |           -           |
| WP-01 | Project Management Table | Summarizes scope of the project plan.  |  PM-1.1  |     D-01      |         AC-01         |
| WP-   |                          |                                        |          |               |                       |

### PM-1.6 Verification (AC)
| AC-ID |  WP-Link  | Description                                             | Acceptance Checklist                                                                 |  Priority  | Risk Score |  Status  |
|:------|:---------:|---------------------------------------------------------|--------------------------------------------------------------------------------------|:----------:|:----------:|:--------:|
| AC-00 |     -     | -                                                       | -                                                                                    |     -      |     -      |    -     |
| AC-01 |   WP-01   | Project management table displays relevant information. | a. PM-ID <br> b. Title <br> c. Description <br> d. Dependencies <br> e. Completed By |    Low     |            |          |
| AC-   |           |                                                         |                                                                                      |            |            |          |

---

## PM-2 Product Analysis
### PM-2.1 Stakeholder Influences (SI)
| SI-ID | Identified Influence | Interest or Conflict | Possible Impact | Desired Outcome |
|:------|----------------------|----------------------|-----------------|-----------------|
| SI-00 | -                    | -                    | -               | -               |

### PM-2.2 Stakeholder Register (ST)
| ST-ID | Identified Stakeholder | Assigned Role | Associated Influences | Risk Category |
|:------|------------------------|---------------|-----------------------|---------------|
| ST-00 | -                      | -             | -                     | -             |

### PM-2.3 Detailed Product Description
Write a detailed description of the final deliverable. Mention high level requirements and general user flow of the final program.

#### PM-2.4 High-Level Requirements
| PM-ID    |    Requirement Name     |  High-Level Detailed Description  |  Deliverable ID  |  FR-Link  |
|:---------|:-----------------------:|:---------------------------------:|:----------------:|:---------:|
| PM-3.1   |     Application GUI     |                 -                 |        -         |   FR-1    |
| PM-3.2   |     Player Controls     |                 -                 |        -         |   FR-2    |
| PM-3.3.1 |    System Behaviour     |                 -                 |        -         |   FR-3    |
| PM-3.3.2 |     Game Behaviour      |                 -                 |        -         |   FR-4    |

#### PM-2.5 User Stories (US)
| US-ID | Item Name | User Story | Translated Requirement | FR-Link | Status |
|:------|-----------|------------|------------------------|---------|--------|
| US-00 | -         | -          | -                      | -       |        |

---

## PM-3 Functional Requirements (FR)
### PM-3.1 Application
#### FR-1 Screen Views
| FR-ID    | Item Name            |                  Dependencies                   |                                     Precondition                                      | Function Calls                                                                                                                                  |                       Postcondition                       | Test Case ID  | 
|:---------|:---------------------|:-----------------------------------------------:|:-------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------------------------------------------|:---------------------------------------------------------:|:-------------:|
| FR-00    | -                    |                        -                        |                                           -                                           |                                                                                                                                                 |                                                           |               |      -       |
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

### PM-3.2 Player Controls
#### FR-2 Keyboard Inputs
| FR-ID  | Item Name          | Dependencies | Precondition | Function Calls | Postcondition | Test Case ID | 
|:-------|:-------------------|:------------:|:------------:|:--------------:|:-------------:|:------------:|
| FR-00  | -                  |      -       |      -       |                |       -       |      -       | 
| FR-2.1 | keyUpInput         |      -       |              |                |       -       |      -       |  
| FR-2.2 | keyDownInput       |      -       |              |                |       -       |      -       |  
| FR-2.3 | keyLeftInput       |      -       |              |                |       -       |      -       |  
| FR-2.4 | keyRightInput      |      -       |              |                |       -       |      -       |  
| FR-2.5 | keyPInput          |      -       |              |                |       -       |      -       | 
| FR-2.6 | keyEscInput        |      -       |              |                |       -       |      -       |   
| FR-2.7 | keyboardScoreInput |      -       |              |                |       -       |      -       |   

### PM-3.3 Application Behaviour
#### PM-3.3.1 System Behaviour
##### FR-3 System Module
| FR-ID  | Item Name                 | Dependencies | Precondition | Function Calls | Postcondition | Test Case ID |
|:-------|:--------------------------|:------------:|:------------:|:--------------:|:-------------:|:------------:|
| FR-00  | -                         |      -       |      -       |                |       -       |      -       |    
| FR-3.1 | systemSaveModule          |      -       |              |                |       -       |      -       |     
| FR-3.2 | systemDisplayGameModule   |      -       |              |                |       -       |      -       |    
| FR-3.3 | systemConfigurationModule |      -       |              |                |       -       |      -       |     
| FR-3.4 | systemLeaderboardModule   |      -       |              |                |       -       |      -       |    
| FR-3.5 | viewChangeModule          |      -       |              |                |       -       |      -       |    

#### PM-3.3.2 Game Behaviour
##### FR-4 Game Modules
| FR-ID    | Item Name                | Dependencies | Precondition | Function Calls | Postcondition | Test Case ID |
|:---------|:-------------------------|:------------:|:------------:|:--------------:|:-------------:|:------------:|
| FR-00    | -                        |      -       |      -       |                |       -       |      -       |     
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

## PM-4 Non-Functional Requirements (NFR)
### PM-4.1 Quality Metrics (QM)
#### PM-4.1.1 Cohesion Maximization Metric
| QM-ID  | Calculation                                                                    | Passing Criteria | Status |
|:------:|--------------------------------------------------------------------------------|------------------|--------|
| QM-COH | Percent Cohesive Classes = 100 * (# of classes with LCOM4 = 1) / total classes | > 90%            |        |

#### PM-4.1.2 Coupling Minimization Metric
| QM-ID  | Calculation                                                                    | Passing Criteria | Status |
|:------:|--------------------------------------------------------------------------------|------------------|--------|
| QM-COU | Imports Per Class = # of project-internal imports outside the class's package. | Avg < 5, Max < 9 |        |

### PM-4.2 NFR Passing Criteria (URPS+)
| NFR-ID  | Category    | Requirement Description                                                          | Pass Criteria                                                                        |
|:--------|-------------|----------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|
| NFR-USA | Usability   | The system must be intuitive for first time players with a consistent interface. | Players can start the game with a single menu click. Menu items are clearly labeled. | 
| NFR-REL | Reliability | Score and state consistency across app focus changes.                            | No piece duplication or score/timer changes when app focus changes.                  | 
| NFR-PER | Performance | Stable frame rate.                                                               | Frame rate stays within 5 frames of set value.                                       | 
| NFR-SEC | Security    | No unhandled exceptions.                                                         | 0 unhandled exceptions crash the app.                                                | 
| NFR-LEG | Legal       | Only permitted assets with propper licensing.                                    | License file included.                                                               |
| NFR-COM | Compliance  | Meets assignment rubric, uni policies, and target runtime.                       | Rubric checklist complete, academic integrity check complete. Builds to spec.        |

---

## PM-5 Time Management
### PM-5.1 Agile Methodology
Our three-student team runs a lightweight Scrum/Kanban flow on ~10 hrs/week each. A quick Monday stand-up, Friday team meeting (review + next-sprint planning), and weekday daily tickets time-boxed from the 10-hour budget. Work is tracked on a Trello Kanban (Backlog -> Ready -> Active -> Review -> Complete) with PM and FR sections decomposed into Work Products and mapped to Trello modules.

### PM-5.2 Scheduling Process (Sequencing)
| SEQ-ID | Work Product | Start-Start | Start-Finish | Finish-Start | Finish-Finish |
|:-------|--------------|-------------|--------------|--------------|---------------|
| SEQ-00 | -            | -           | -            | -            | -             |

### PM-5.3 Schedule Network Diagrams (Critical Path)
```md
[Insert Image]
```

### PM-5.4 Resource Methodology (Resource Estimations)
#### PM-5.4.1 Time Unit Model
| Unit Type   | Time Cost |  Unit Composition   | Units / Student | Units / Team / Week (3 students) | Work Minutes / Student | Break Minutes / Student |
|:------------|:---------:|:-------------------:|:---------------:|:--------------------------------:|:----------------------:|:-----------------------:|
| Pomodoro-30 |  30 min   | 25 study + 5 break  |       20        |                60                |        500 min         |         100 min         |
| Focus-60    |  60 min   | 50 focus + 10 break |       10        |                30                |        500 min         |         100 min         |
| Deep-120    |  120 min  |  (50+10) + (50+10)  |        5        |                15                |        500 min         |         100 min         |

#### PM-5.4.2 Time Cost Estimations (Activity Estimations)
| AE-ID | Workstream                 | % | Team Hours | 30-min Units | 60-min Units |
|:------|----------------------------|---|------------|--------------|--------------|
| AE-00 | -                          | - | -          | -            | -            |
| AE-01 | Project Planning           | - | -          | -            | -            |
| AE-02 | Requirement Analysis       | - | -          | -            | -            |
| AE-03 | Time Management            | - | -          | -            | -            |
| AE-04 | HR Management              | - | -          | -            | -            |
| AE-05 | Risk Management            | - | -          | -            | -            |
| AE-06 | Implementation/Development | - | -          | -            | -            |
| AE-07 | Testing/Verification       | - | -          | -            | -            |
| AE-08 | Documentation Finalization | - | -          | -            | -            |

### PM-5.5 Project Schedule (Complete Task List)
| Task ID | Date | Planned Duration | Assigned TM | Workstream | Parent Work Product | Status |
|:--------|------|------------------|-------------|------------|---------------------|--------|
| TASK-00 | -    | -                | -           | -          | -                   |        |

### PM-5.6 Gantt Chart
```md
[Insert Image]
```

---

## PM-6 HR Management
### PM-6.1 Project Hierarchy
![project hierarchy](_temp/PM-6.1%20Project%20Hierarchy.png "Project Hierarchy")


### PM-6.2 Role Descriptions (TM)
| TM-ID  |     Role / Title     |                                   Responsibilities                                   | Assigned To | 
|:-------|:--------------------:|:------------------------------------------------------------------------------------:|:-----------:|
| TM-00  |          -           |                                          -                                           |      -      |    
| TM-01  |   Project Manager    |    Define the project scope, assumptions, constraints, and manage scope changes.     |      -      |     
| TM-02  | Stakeholder Analyst  |          Identify stakeholders, influences, conflicts, and create register.          |      -      |    
| TM-03  | Requirements Analyst | Identify functional and non-functional requirements based on the initial demo video. |      -      |    
| TM-04  |   Planning Analyst   |                Handle activity estimation, planning, and scheduling.                 |      -      |    
| TM-05  |     Risk Analyst     |       Identify, assess, and prioritize risks with mitigation recommendations.        |      -      |    
| TM-06  |     UML Designer     |                  Create UML documentation (use case, activity etc.)                  |      -      |     
| TM-06  |      Developer       |                Write and integrate Java code following PM standards.                 |      -      |     
| TM-07  |     Test Analyst     |     Write and execute test cases for functional and non-functional requirements.     |      -      |    
| TM-08  |   Demo Coordinator   |                         Record and deliver final demo video.                         |      -      |    

### PM-6.3 Peer Review Process

#### PM-6.3.1 Self Reviews
    What was your primary role in this milestone?
    
    What specific tasks or deliverables did you complete?
    
    What challenges, if any, did you face while contributing to the project?
    
    What is one aspect of your contribution that you would like to improve in the next milestone?
    
    On a scale of 1 to 10, how would you rate your overall contribution to the team?

#### PM-6.3.2 Team Member Reviews
| Date  | Review Of  | Key Contributions | Professionalism | Suggestion | Submitted By |
|:------|:----------:|:-----------------:|:---------------:|:----------:|:------------:|
| -     |     -      |         -         |        -        |     -      |      -       |
|       |            |                   |                 |            |              |


#### PM-6.3.3 Self Reflection
    Was the peer review process fair and helpful in identifying your contributions and role within the team?
    
    Did the feedback you received help you identify any areas for personal or team improvement?
    
    On a scale of 1 to 10, how useful was this peer review cycle for your learning and team development?

---

## PM-7 Risk Management

### PM-7.1 Categories
| CA-ID | Name | Description | Likelihood | Affected Stakeholders |
|:------|:----:|:-----------:|:----------:|:---------------------:|
| CA-00 |  -   |      -      |     -      |           -           |

### PM-7.2 Risk Assessment Matrix (RA)
| RA-ID | Risk Name | Description | Identified Factors | Probability | Impact Severity |
|:------|:---------:|:-----------:|:------------------:|:-----------:|:---------------:|
| RA-00 |     -     |      -      |         -          |      -      |        -        |

### PM-7.3 Risk Register (RI)
| RI-ID | Name | Category |    Work Products     | Risk Score | RA-Links | Review Process |  
|:------|:----:|:--------:|:--------------------:|:----------:|:--------:|:--------------:|
| RI-00 |  -   |    -     |          -           |     -      |    -     |                |

---

## PM-8. Software Architecture (UML)
### PM-8.1 Structural Diagrams
| PM-ID    | Diagram Name                | Description                                                                                              | GitHub-Link |
|:---------|-----------------------------|----------------------------------------------------------------------------------------------------------|-------------|
| PM-8.1.1 | Class Diagram               | Shows the static structure of the system, including classes, attributes, methods, and relationships.     | -           |
| PM-8.1.2 | Object Diagram              | Represents a snapshot of the objects in a system at a specific point in time.                            | -           |
| PM-8.1.3 | Component Diagram           | Depicts the organization and dependencies among software components.                                     | -           |
| PM-8.1.4 | Deployment Diagram          | Shows the physical deployment of artifacts on nodes (hardware).                                          | -           |
| PM-8.1.5 | Composite Structure Diagram | Illustrates the internal structure of a class and the collaborations that this structure makes possible. | -           |
| PM-8.1.6 | Package Diagram             | Groups related classes into packages.                                                                    | -           |
| PM-8.1.7 | Profile Diagram             | Defines custom stereotypes, tagged values, and constraints.                                              | -           |

### PM-8.2 Behavioural Diagrams
| PM-ID    | Diagram Name                 | Description                                                                                                                                                 | GitHub-Link |
|----------|------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------|
| PM-8.2.1 | Use Case Interaction Diagram | Captures the functional requirements of the system by showing actors and their interactions with use cases.                                                 | -           |
| PM-8.2.2 | Sequence Diagram             | Represents object interactions arranged in a time sequence.                                                                                                 | -           |
| PM-8.2.3 | Activity Diagram             | Shows the flow of control or object flow with emphasis on the sequence and conditions of the flow.                                                          | -           |
| PM-8.2.4 | State Machine Diagram        | Describes the states of an object and the transitions between these states.                                                                                 | -           |
| PM-8.2.5 | Communication Diagram        | Focuses on the interactions between objects and the sequence of messages.                                                                                   | -           |
| PM-8.2.6 | Interaction Overview Diagram | Combines elements of activity and sequence diagrams to show the control flow between different interactions.                                                | -           |
| PM-8.2.7 | Timing Diagram               | Shows interactions when the primary purpose is to depict time constraints                                                                                   | -           |
| PM-8.2.8 | Use Case Dependency Diagram  | Represents the functionality provided by a system in terms of actors, their goals (represented as use cases), and any dependencies between those use cases. | -           |

---

## PM-9 Implementation
### PM-9.1 GitHub

### PM-9.2 File Structure

### PM-9.3 Code Snippets

---

## PM-10 Testing
### PM-10.1 Test Cases (TC)
#### PM-10.1.1 Unit Tests
| TC-ID | FR-Link | Test Description | Test Name | Preconditions | Test Steps |
|:-----:|---------|------------------|-----------|---------------|------------|
| TC-00 | -       | -                |           | -             | -          |

#### PM-10.1.2 Integration Tests
| TC-ID | FR-Link | Test Description | Test Name | Preconditions | Test Steps |
|:-----:|---------|------------------|-----------|---------------|------------|
| TC-00 | -       | -                | -         | -             | -          |

#### PM-10.1.3 System Tests
| TC-ID | FR-Link | Test Description | Test Name | Preconditions | Test Steps |
|:-----:|---------|------------------|-----------|---------------|------------|
| TC-00 | -       | -                | -         | -             | -          |

#### PM-10.1.4 Acceptance Testing
| TC-ID | FR-Link | Test Description | Test Name | Preconditions | Test Steps |
|:-----:|---------|------------------|-----------|---------------|------------|
| TC-00 | -       | -                | -         | -             | -          |


### PM-10.2 Test Results
| Test-ID | Date | Test Type | TC-Link | Expected Outcome | Pass Criteria | Actual Outcome | Tested By |
|:--------|:----:|-----------|---------|------------------|---------------|----------------|-----------|
| Test-00 |  -   | -         | -       | -                | -             | -              | -         |

---

## PM-11 Final Submission
### PM-11.1 Demo Video Link

