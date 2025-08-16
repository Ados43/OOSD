# Java: Tetris Assignment
2006ICT Object-Oriented Software Development

## Installation
1. Prerequisite software and environment setup.  
2. Repository download or clone procedure.  
3. Project build and compilation steps.  

## How to Run
1. Execution method from compiled classes or packaged file.  
2. Command-line execution process.  

## How to Play
1. Objective of the game.  
2. Game controls and interactions.  
3. Scoring and progression mechanics.  

---

# Table of Contents

- [Project Plan](#project-plan)
    - [PM-1 Scope Management](#pm-1-scope-management)
        - [PM-1.1 Documentation Scope](#pm-11-documentation-scope-project-management)
        - [PM-1.2 Project Scope](#pm-12-project-scope)
        - [PM-1.3 Project Milestones](#pm-13-project-milestones)
        - [PM-1.4 Work Breakdown (WBS)](#pm-14-work-breakdown-wbs)
        - [PM-1.5 Activity List](#pm-15-activity-list-work-products)
        - [PM-1.6 Verification (Acceptance Criteria)](#pm-16-verification-acceptance-criteria)
    - [PM-2 Stakeholder Analysis](#pm-2-stakeholder-analysis)
        - [PM-2.1 Product Analysis](#pm-21-product-analysis)
        - [PM-2.1 Stakeholder Influences](#pm-21-stakeholder-influences-interestsconflicts)
        - [PM-2.2 Stakeholder Register](#pm-22-stakeholder-register-stakeholders)
        - [PM-2.4 Detailed Product Description](#pm-24-detailed-product-description)
        - [PM-2.5 High-Level Requirements](#pm-25-high-level-requirements)
        - [PM-2.6 User Stories](#pm-26-user-stories)
    - [PM-3 Functional Requirements](#pm-3-functional-requirements)
        - [PM-3.1 Application GUI](#pm-31-application-gui)
        - [PM-3.2 Player Controls](#pm-32-player-controls)
        - [PM-3.3 Application Behaviour](#pm-33-application-behaviour)
        - [FR-4 Game Modules](#fr-4-game-modules)
    - [PM-4 Non-Functional Requirements](#pm-4-non-functional-requirements)
        - [PM-4.1 Usability](#pm-41-usability)
        - [PM-4.2 Reliability](#pm-42-reliability)
        - [PM-4.3 Performance](#pm-43-performance)
        - [PM-4.4 Security](#pm-44-security)
        - [PM-4.5 Legal](#pm-45-legal)
        - [PM-4.6 Compliance](#pm-46-compliance)
        - [PM-4.7 Quality Metrics](#pm-47-quality-metrics)
    - [PM-5 Time Management](#pm-5-time-management)
        - [PM-5.1 Agile Methodology](#pm-51-agile-methodology)
        - [PM-5.2 Scheduling Process](#pm-52-scheduling-process-sequencing)
        - [PM-5.3 Schedule Network Diagrams](#pm-53-schedule-network-diagrams-critical-path)
        - [PM-5.4 Resource Methodology](#pm-54-resource-methodology-resource-estimations)
        - [PM-5.5 Project Schedule](#pm-55-project-schedule-complete-task-list)
        - [PM-5.6 Gantt Chart](#pm-56-gantt-chart)
    - [PM-6 HR Management](#pm-6-hr-management)
        - [PM-6.1 Project Hierarchy](#pm-61-project-hierarchy)
        - [PM-6.2 Role Descriptions](#pm-62-role-descriptions)
        - [PM-6.3 Peer Review Process](#pm-63-peer-review-process)
    - [PM-7 Risk Management](#pm-7-risk-management)
        - [PM-7.1 Categories](#pm-71-categories-overall-exposure)
        - [PM-7.2 Risk Assessment Matrix](#pm-72-risk-assessment-matrix)
        - [PM-7.3 Risk Register](#pm-73-risk-register-review-process)
    - [PM-8 Software Architecture (UML)](#pm-8-software-architecture-uml)
        - [PM-8.1 Structural Diagrams](#pm-81-structural-diagrams)
        - [PM-8.2 Behavioural Diagrams](#pm-82-behavioural-diagrams)
    - [PM-9 Implementation](#pm-9-implementation)
    - [PM-10 Testing](#pm-10-testing)
        - [PM-10.1 Test Cases](#pm-101-test-cases)
        - [PM-10.2 Test Results](#pm-102-test-results)
    - [PM-11 Final Submission PDF](#pm-11-final-submission-pdf)
        - [PM-11.1 Demo Video Link](#pm-111-demo-video-link)
- [Document History](#document-history)

# Project Plan

---

## PM-1 Scope Management

### PM-1.1 Documentation Scope (Project Management)

|   PM-ID   | Section Title                                | Content Description |                                         PM-Dependencies                                          | Assigned TM-ID / Initial |      Status | 
|:---------:|:---------------------------------------------|:-------------------:|:------------------------------------------------------------------------------------------------:|:------------------------:|------------:|
| PM-0.0.0  | -                                            |          -          |                                                -                                                 |            -             |           - |
|   PM-1    | Scope Management                             |                     |                                                -                                                 |                          |             |
|  PM-1.1   | Documentation Scope (Project Management)     |                     |                                                                                                  |            FC            | In Progress |
|  PM-1.2   | Project Scope                                |                     |                                              PM-1.1                                              |                          |             |
|  PM-1.3   | Project Milestones                           |                     |                                              PM-1.2                                              |                          |             |
|  PM-1.4   | Work Breakdown (WBS)                         |                     |                                        PM-1.3 <br> PM-1.2                                        |                          |             |
|  PM-1.5   | Activity List (Work Products)                |                     |                                              PM-1.4                                              |                          |             |
|  PM-1.6   | Verification (Acceptance Criteria)           |                     |                                              PM-1.5                                              |                          |             |
|   PM-2    | Product Analysis                             |                     |                                                                                                  |                          |             |
|  PM-2.1   | Stakeholder Influences (Interests/Conflicts) |                     |                                              PM-1.2                                              |                          |             |
|  PM-2.2   | Stakeholder Register (Stakeholders)          |                     |                                              PM-2.1                                              |                          |             |
|  PM-2.3   | Detailed Product Description                 |                     |                                        PM-1.2 <br> PM-2.2                                        |                          |             |
|  PM-2.4   | High-Level Requirements                      |                     |                                              PM-2.3                                              |                          |             |
|  PM-2.5   | User Stories                                 |                     |                                              PM-2.4                                              |                          |             |
|   PM-3    | Functional Requirements                      |                     |                                              PM-2.5                                              |                          |             |
|  PM-3.1   | Application                                  |                     |                                                                                                  |                          |             |
|  PM-3.2   | Player Controls                              |                     |                                                                                                  |                          |             |
|  PM-3.3   | Application Behaviour                        |                     |                                                                                                  |                          |             |
|   PM-4    | Non-functional Requirements                  |                     |                                         PM-2.4 <br> PM-3                                         |                          |             |
|  PM-4.1   | Usability                                    |                     |                                                                                                  |                          |             |
|  PM-4.2   | Reliability                                  |                     |                                                                                                  |                          |             |
|  PM-4.3   | Performance                                  |                     |                                                                                                  |                          |             |
|  PM-4.4   | Security                                     |                     |                                                                                                  |                          |             |
|  PM-4.5   | Legal                                        |                     |                                                                                                  |                          |             |
|  PM-4.6   | Compliance                                   |                     |                                                                                                  |                          |             |
|  PM-4.7   | Quality Metrics                              |                     |                                                                                                  |                          |             |
| PM-4.7.1  | Cohesion Maximization Metric                 |                     |                                                                                                  |                          |             |
| PM-4.7.2  | Coupling Minimization Metric                 |                     |                                                                                                  |                          |             |
|   PM-5    | Time Management                              |                     |                                                                                                  |                          |             |
|  PM-5.1   | Agile Methodology                            |                     |                                                                                                  |                          |             |
|  PM-5.2   | Scheduling Process (Sequencing)              |                     |                                              PM-1.5                                              |                          |             |
|  PM-5.3   | Schedule Network Diagrams (Critical Path)    |                     |                                              PM-5.2                                              |                          |             |
|  PM-5.4   | Resource Methodology (Resource Estimations)  |                     |                                                                                                  |                          |             |
| PM-5.4.1  | Time Unit Model                              |                     |                                                                                                  |                          |             |
| PM-5.4.2  | Time Cost Estimations (Activity Estimations) |                     |                                             PM-5.4.1                                             |                          |             |
|  PM-5.5   | Project Schedule (Complete Task List)        |                     |                                             PM-5.4.2                                             |                          |             |
|  PM-5.6   | Gantt Chart                                  |                     |                                              PM-5.5                                              |                          |             |
|   PM-6    | HR Management                                |                     |                                                                                                  |                          |             |
|  PM-6.1   | Project Hierarchy                            |                     |                                              PM-1.2                                              |                          |             |
|  PM-6.2   | Role Descriptions                            |                     |                                              PM-6.1                                              |                          |             |
|  PM-6.3   | Peer Review Process                          |                     |                                                                                                  |                          |             |
| PM-6.3.1  | Self Reviews                                 |                     |                                                                                                  |                          |             |
| PM-6.3.2  | Team Member Reviews                          |                     |                                                                                                  |                          |             |
| PM-6.3.3  | Self Reflection                              |                     |                                                                                                  |                          |             |
|   PM-7    | Risk Management                              |                     |                                                                                                  |                          |             |
|  PM-7.1   | Categories (Overall Exposure)                |                     |                                              PM-1.2                                              |                          |             |
|  PM-7.2   | Risk Assessment Matrix                       |                     |                                              PM-7.1                                              |                          |             |
|  PM-7.3   | Risk Register (Review Process)               |                     |                                        PM-2.2 <br> PM-7.2                                        |                          |             |
|  PM-8.1   | Structural Diagrams                          |                     |                                               PM-3                                               |                          |             |
| PM-8.1.1  | Class Diagram                                |                     |                                                                                                  |                          |             |
| PM-8.1.2  | Object Diagram                               |                     |                                                                                                  |                          |             |
| PM-8.1.3  | Component Diagram                            |                     |                                                                                                  |                          |             |
| PM-8.1.4  | Deployment Diagram                           |                     |                                                                                                  |                          |             |
| PM-8.1.5  | Composite Structure Diagram                  |                     |                                                                                                  |                          |             |
| PM-8.1.6  | Package Diagram                              |                     |                                                                                                  |                          |             |
| PM-8.1.7  | Profile Diagram                              |                     |                                                                                                  |                          |             |
|  PM-8.2.  | Behavioural Diagrams                         |                     |                                               PM-3                                               |                          |             |
| PM-8.2.1  | Use Case Interaction Diagram                 |                     |                                                                                                  |                          |             |
| PM-8.2.2  | Sequence Diagram                             |                     |                                                                                                  |                          |             |
| PM-8.2.3  | Activity Diagram                             |                     |                                                                                                  |                          |             |
| PM-8.2.4  | State Machine Diagram                        |                     |                                                                                                  |                          |             |
| PM-8.2.5  | Communication Diagram                        |                     |                                                                                                  |                          |             |
| PM-8.2.6  | Interaction Overview Diagram                 |                     |                                                                                                  |                          |             |
| PM-8.2.7  | Use Case Dependency Diagram                  |                     |                                                                                                  |                          |             |
|   PM-9    | Implementation                               |                     |                                               PM-8                                               |                          |             |
|  PM-9.1   | GitHub                                       |                     |                                                                                                  |                          |             |
|  PM-9.2   | File Structure                               |                     |                                                                                                  |                          |             |
|  PM-9.3   | Code Snippets                                |                     |                                                                                                  |                          |             |
|   PM-10   | Testing                                      |                     |                                               PM-9                                               |                          |             |
|  PM-10.1  | Test Cases                                   |                     |                                                                                                  |                          |             |
| PM-10.1.1 | Unit Tests                                   |                     |                                                                                                  |                          |             |
| PM-10.1.2 | Integration Tests                            |                     |                                                                                                  |                          |             |
| PM-10.1.3 | System Tests                                 |                     |                                                                                                  |                          |             |
| PM-10.1.4 | Acceptance Tests                             |                     |                                                                                                  |                          |             |
|  PM-10.2  | Test Results                                 |                     |                                        PM-9 <br> PM-10.1                                         |                          |             |
|   PM-11   | Final Submission PDF                         |                     | PM-1 <br> PM-2 <br> PM-3 <br> PM-4 <br> PM-5 <br> PM-6 <br> PM-7 <br> PM-8 <br> PM-9 <br> PM-10  |                          |             |
|  PM-11.1  | Demo Video Link                              |                     |                                                                                                  |                          |             |

### PM-1.2 Project Scope

Brief definition of scope control process.

### PM-1.3 Project Milestones

Brief definition of project milestones.    

| M-ID | Milestone Name    | Deliverables              | Description                                               | Deadline | Status      |
|:-----|-------------------|---------------------------|-----------------------------------------------------------|----------|-------------|
| M-00 | -                 | -                         | -                                                         | -        | -           |
| M-01 | PM-Documentation  | PM-1, 2, 3, 4, 5, 6, 7, 8 | Finalize the project plan and project management process. | 18/6/25  | In-Progress |
| M-02 | Implementation    | PM-9                      | Push the full pre-testing implementation to GitHub.       | -        | -           |
| M-03 | Testing           | PM-10                     | Push test functions and logs to GitHub.                   | -        | -           |
| M-04 | Final Submission  | PM-11                     | Finalize PDF and demo video.                              | -        | -           |

### PM-1.4 Work Breakdown (WBS)

| D-ID | Deliverable                               | High-Level Description | Output Work Products                                                                         | Dependencies                                           | Milestone        | Status |  
|:-----|-------------------------------------------|------------------------|----------------------------------------------------------------------------------------------|--------------------------------------------------------|------------------|--------|
| D-00 | -                                         | -                      | -                                                                                            | -                                                      |                  |        |
| D-01 | Project Scope Description                 | -                      | PM-1.2                                                                                       | PM-1.1                                                 | PM-Documentation |        |
| D-02 | Project Milestone List                    | -                      | PM-1.3                                                                                       | D-01                                                   | PM-Documentation |        |
| D-03 | Complete Work Breakdown Structure         | -                      | PM-1.4                                                                                       | PM-1.1 <br> D-01                                       | PM-Documentation |        |
| D-04 | Approved Activity List                    | -                      | PM-1.5                                                                                       | D-03                                                   | PM-Documentation |        |
| D-05 | Final Acceptance Criteria                 | -                      | PM-1.6                                                                                       | D-04                                                   | PM-Documentation |        |
| D-06 | Stakeholder Analysis                      | -                      | PM-2.1                                                                                       | D-01                                                   | PM-Documentation |        |
| D-07 | Stakeholder Register                      | -                      | PM-2.2                                                                                       | D-07                                                   | PM-Documentation |        |
| D-08 | User Stories                              | -                      | PM-2.5                                                                                       | D-07 <br> PM-2.3 <br> PM-2.4                           | PM-Documentation |        |
| D-09 | Functional Requirements Specification     | -                      | PM-3.1 <br>PM-3.2  <br>PM-3.3                                                                | D-08                                                   | PM-Documentation |        |
| D-10 | Non-Functional Requirements Specification | -                      | PM-4.1 <br>PM-4.2 <br>PM-4.2 <br>PM-4.3                                                      | D-11                                                   | PM-Documentation |        |
| D-11 | Quality Metrics                           | -                      | PM-4.7                                                                                       | -                                                      | PM-Documentation |        |
| D-12 | Time Management Plan                      | -                      | PM-5.1 <br> PM-5.2 <br> PM-5.3 <br>PM-5.4                                                    | D-04                                                   | PM-Documentation |        |
| D-13 | Project Schedule                          | -                      | PM-5.5                                                                                       | D-12                                                   | PM-Documentation |        |
| D-14 | Gantt Chart                               | -                      | PM-5.6                                                                                       | D-13                                                   | PM-Documentation |        |
| D-15 | HR Management Plan                        | -                      | PM-6.1 PM-6.2                                                                                | -                                                      | PM-Documentation |        |
| D-16 | Peer Reviews                              | -                      | PM-6.3.1 <br> PM-6.3.2 <br> PM-6.3.3                                                         | -                                                      | PM-Documentation |        |
| D-17 | Risk Management Plan                      | -                      | PM-7.1 <br> PM-7.1 <br> PM-7.1                                                               | D-07  <br> D-12                                        | PM-Documentation |        |
| D-18 | UML Structural Diagrams                   | -                      | PM-8.1.1 <br> PM-8.1.2 <br> PM-8.1.3 <br> PM-8.1.4 <br> PM-8.1.5 <br> PM-8.1.6 <br> PM-8.1.7 | -                                                      | PM-Documentation |        |
| D-19 | UML Behavioural Diagrams                  | -                      | PM-8.2.1 <br> PM-8.2.2 <br> PM-8.2.3 <br> PM-8.2.4 <br> PM-8.2.5 <br> PM-8.2.6 <br> PM-8.2.7 | -                                                      | PM-Documentation |        |
| D-20 | GitHub File Structure                     | -                      | PM-9.2                                                                                       | D-18, PM-9.1                                           | Implementation   |        |
| D-21 | Codebase                                  | -                      |                                                                                              | M-01                                                   | Implementation   |        |
| D-22 | Test Functions                            | -                      | PM-10.1                                                                                      | PM-10.1.1 <br> PM-10.1.2 <br> PM-10.1.3 <br> PM-10.1.4 | Testing          |        |
| D-23 | Testing Results                           | -                      | PM-10.2                                                                                      | D-22                                                   | Testing          |        |
| D-24 | Demo Video                                | -                      | PM-11.1                                                                                      | M-03                                                   | Final Submission |        |
| D-25 | Final Submission PDF                      | -                      | PM-11                                                                                        | D-24                                                   | Final Submission |        |

### PM-1.5 Activity List (Work Products)

| WP-ID | Work Product Name | Work Description | PM/FR/TC-Link | Dependencies | AC-ID |
|:------|-------------------|------------------|---------------|--------------|-------|
| WP-00 | -                 | -                | -             | -            | -     |

### PM-1.6 Verification (Acceptance Criteria)

| AC-ID | WP-ID | NFR-Link | Risk Category | Priority | Pass Criteria | Status |
|:------|-------|----------|---------------|----------|---------------|--------|
| AC-00 | -     | -        | -             | -        | -             | -      |

---

## PM-2 Stakeholder Analysis

### PM-2.1 Product Analysis

A few sentences on the overall and far-reaching impact of the project and product. Mentioning all applicable stakeholders and relevant desires.

### PM-2.1 Stakeholder Influences (Interests/Conflicts)

| SI-ID | Identified Influence | Interest or Conflict | Possible Impact | Desired Outcome |
|:------|----------------------|----------------------|-----------------|-----------------|
| SI-00 | -                    | -                    | -               | -               |

### PM-2.2 Stakeholder Register (Stakeholders)

| ST-ID | Identified Stakeholder | Assigned Role | Associated Influences | Risk Category |
|:------|------------------------|---------------|-----------------------|---------------|
| ST-00 | -                      | -             | -                     | -             |

### PM-2.4 Detailed Product Description

Write a detailed description of the final deliverable. Mention high level requirements and general user flow of the final program.

#### PM-2.5 High-Level Requirements

Briefly state how the high level requirements are derived from the product description and separate user stories groupings.

| PM-ID    | Requirement Name      | High-Level Detailed Description | Deliverable ID | FR-Link |
|:---------|-----------------------|---------------------------------|----------------|---------|
| PM-3.1   | Application GUI       | -                               | -              | FR-1    |
| PM-3.2   | Player Controls       | -                               | -              | FR-2    |
| PM-3.3   | Application Behaviour | -                               | -              | FR-3    |

#### PM-2.6 User Stories

| US-ID | Item Name | User Story | PM/FR-Link | Status |
|:------|-----------|------------|------------|--------|
| US-00 | -         | -          | -          | -      |

---

## PM-3 Functional Requirements

### PM-3.1 Application GUI

#### FR-1 Screen Views 

| FR-ID    | Item Name            | User Story | Translated Requirement | Task List |                  Dependencies                   | Output Task / Work Product | Test Case ID | 
|:---------|:---------------------|:----------:|:----------------------:|:---------:|:-----------------------------------------------:|:--------------------------:|:------------:|
| FR-00    | -                    |     -      |           -            |           |                        -                        |             -              |      -       |
| FR-1.1   | mainView             |     -      |           -            |           | FR-1.1.1 <br>FR-1.1.2 <br>FR-1.1.3 <br>FR-1.1.4 |           WP-ID            |      -       |
| FR-1.1.1 | mainStartBtn         |     -      |           -            |           |                        -                        |             -              |      -       |
| FR-1.1.2 | mainConfigBtn        |     -      |           -            |           |                        -                        |             -              |      -       |
| FR-1.1.3 | mainScoreBtn         |     -      |           -            |           |                        -                        |             -              |      -       |
| FR-1.1.4 | mainExitBtn          |     -      |           -            |           |                        -                        |             -              |      -       |
| FR-1.2   | playView             |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.2.1 | playGameView         |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.2.2 | playScoreView        |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.2.3 | playExitBtn          |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.2.4 | playExitDialog       |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.2.5 | playNewScoreDialog   |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.3   | configView           |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.3.1 | configWidthView      |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.3.2 | configHeightView     |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.3.3 | configDifficultyView |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.3.4 | configMusicView      |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.3.5 | configAiView         |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.3.6 | configExtendedView   |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.3.7 | configExitBtn        |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.4   | scoreView            |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.4.1 | scoreTopScoresView   |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.4.2 | scoreTopNamesView    |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.4.3 | scoreExitBtn         |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.5   | splashView           |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.5.1 | splashCodeView       |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.5.2 | splashAuthorView     |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.5.3 | splashTitleView      |     -      |                        |           |                        -                        |             -              |      -       |
| FR-1.5.4 | splashPositionView   |     -      |                        |           |                        -                        |             -              |      -       |

### PM-3.2 Player Controls

#### FR-2 Keyboard Inputs

| FR-ID    | Item Name          | User Story | Translated Requirement | Task List | Dependencies | Output Task / Work Product | Test Case ID | 
|:---------|:-------------------|:----------:|:----------------------:|:---------:|:------------:|:--------------------------:|:------------:|
| FR-00    | -                  |     -      |           -            |           |      -       |             -              |      -       |
| FR-2.1   | keyUpInput         |     -      |                        |           |      -       |             -              |      -       |
| FR-2.2   | keyDownInput       |     -      |                        |           |      -       |             -              |      -       |
| FR-2.3   | keyLeftInput       |     -      |                        |           |      -       |             -              |      -       |
| FR-2.4   | keyRightInput      |     -      |                        |           |      -       |             -              |      -       |
| FR-2.5   | keyPInput          |     -      |                        |           |      -       |             -              |      -       |
| FR-2.6   | keyEscInput        |     -      |                        |           |      -       |             -              |      -       |
| FR-2.7   | keyboardScoreInput |     -      |                        |           |      -       |             -              |      -       |

### PM-3.3 Application Behaviour

#### FR-3 System Modules

| FR-ID  | Item Name                 | User Story | Translated Requirement | Task List | Dependencies | Output Task / Work Product | Test Case ID | 
|:-------|:--------------------------|:----------:|:----------------------:|:---------:|:------------:|:--------------------------:|:------------:|
| FR-00  | -                         |     -      |           -            |           |      -       |             -              |      -       |
| FR-3.1 | systemSaveModule          |     -      |                        |           |      -       |             -              |      -       |
| FR-3.2 | systemRenderGameModule    |     -      |                        |           |      -       |             -              |      -       |
| FR-3.3 | systemConfigurationModule |     -      |                        |           |      -       |             -              |      -       |
| FR-3.4 | systemLeaderboardModule   |     -      |                        |           |      -       |             -              |      -       |
| FR-3.5 | viewChangeModule          |     -      |                        |           |      -       |             -              |      -       |

#### FR-4 Game Modules

| FR-ID    | Item Name                | User Story | Translated Requirement | Task List | Dependencies | Output Task / Work Product | Test Case ID | 
|:---------|:-------------------------|:----------:|:----------------------:|:---------:|:------------:|:--------------------------:|:------------:|
| FR-00    | -                        |     -      |           -            |           |      -       |             -              |      -       |
| FR-4.1   | gameConfigModule         |     -      |                        |           |      -       |             -              |      -       |
| FR-4.2   | gameDisplayModule        |     -      |                        |           |      -       |             -              |      -       |
| FR-4.3   | gameShapeSelectionModule |     -      |                        |           |      -       |             -              |      -       |
| FR-4.4   | gameShapeMovementModule  |     -      |                        |           |      -       |             -              |      -       |
| FR-4.5   | gameStateModule          |     -      |                        |           |      -       |             -              |      -       |
| FR-4.5.1 | gamePauseState           |     -      |                        |           |      -       |             -              |      -       |
| FR-4.5.2 | gameOverState            |     -      |                        |           |      -       |             -              |      -       |
| FR-4.5.3 | gameNewScoreState        |     -      |                        |           |      -       |             -              |      -       |
| FR-4.6   | gameRowUpdate            |     -      |                        |           |      -       |             -              |      -       |
| FR-4.6.1 | gameSingleUpdate         |     -      |                        |           |      -       |             -              |      -       |
| FR-4.6.2 | gameDoubleUpdate         |     -      |                        |           |      -       |             -              |      -       |
| FR-4.6.3 | gameTripleUpdate         |     -      |                        |           |      -       |             -              |      -       |
| FR-4.6.4 | gameTetrisUpdate         |     -      |                        |           |      -       |             -              |      -       |


---

## PM-4 Non-Functional Requirements

#### PM-4.1 Usability

| NFR-USA-ID | Usability Requirement Description | Pass Criteria | PM/WP-Link | Status |
|:-----------|-----------------------------------|---------------|------------|--------|
| NFR-USA-00 | -                                 | -             | -          | -      |

#### PM-4.2 Reliability

| NFR-REL-ID | Reliability Requirement Description | Pass Criteria | PM/WP-Link | Status |
|:-----------|-------------------------------------|---------------|------------|--------|
| NFR-REL-00 | -                                   | -             | -          | -      |

#### PM-4.3 Performance

| NFR-PER-ID | Performance Requirement Description | Pass Criteria | PM/WP-Link | Status |
|:-----------|-------------------------------------|---------------|------------|--------|
| NFR-PER-00 | -                                   | -             | -          | -      |

#### PM-4.4 Security

| NFR-SEC-ID | Security Requirement Description | Pass Criteria | PM/WP-Link | Status |
|:-----------|----------------------------------|---------------|------------|--------|
| NFR-SEC-00 | -                                | -             | -          | -      |

#### PM-4.5 Legal

| NFR-LEG-ID | Legal Requirement Description | Pass Criteria | PM/WP-Link | Status |
|:-----------|-------------------------------|---------------|------------|--------|
| NFR-LEG-00 | -                             | -             | -          | -      |

#### PM-4.6 Compliance

| NFR-COM-ID | Compliance Requirement Description | Pass Criteria | PM/WP-Link | Status |
|:-----------|------------------------------------|---------------|------------|--------|
| NFR-COM-00 | -                                  | -             | -          | -      |

### PM-4.7 Quality Metrics

#### PM-4.7.1 Cohesion Maximization 

#### PM-4.7.2 Coupling Minimization

---

## PM-5 Time Management

### PM-5.1 Agile Methodology

Paragraph on agile workflows linking to NFRs.

### PM-5.2 Scheduling Process (Sequencing)

| SEQ-ID | Work Product ID | Start-Start | Start-Finish | Finish-Start | Finish-Finish |
|:-------|-----------------|-------------|--------------|--------------|---------------|
| SEQ-00 | -               | -           | -            | -            | -             |

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

```md
[Insert Image]
```

### PM-6.2 Role Descriptions

| TM-ID | Title | Description | Responsibilities | Assigned Party |
|:------|:-----:|:-----------:|:----------------:|:--------------:|
| TM-00 |   -   |      -      |        -         |       -        |

### PM-6.3 Peer Review Process

#### PM-6.3.1 Self Reviews

```md
Add self review questions.
```

#### PM-6.3.2 Team Member Reviews

```md
Add team review questions.
```

#### PM-6.3.3 Self Reflection

```md
Add reflection questions.
```

---

## PM-7 Risk Management

### PM-7.1 Categories (Overall Exposure)

Short overview of categories and overall exposure.

| CA-ID | Name | Description | Likelihood | Affected Stakeholders |
|:------|:----:|:-----------:|:----------:|:---------------------:|
| CA-00 |  -   |      -      |     -      |           -           |

### PM-7.2 Risk Assessment Matrix

| RA-ID | Risk Name | Description | Identified Factors | Probability | Impact Severity |
|:------|:---------:|:-----------:|:------------------:|:-----------:|:---------------:|
| RA-00 |     -     |      -      |         -          |      -      |        -        |

### PM-7.3 Risk Register (Review Process)

| RI-ID | Name | Category | Work Products at Risk | Risk Assessment | Mitigation Steps | Responsible Party |  
|:------|:----:|:--------:|:---------------------:|:---------------:|:----------------:|:-----------------:|
| RI-00 |  -   |    -     |           -           |        -        |        -         |                   |

---

## PM-8. Software Architecture (UML)

### PM-8.1 Structural Diagrams

#### PM-8.1.1 Class Diagram
Shows the static structure of the system, including classes, attributes, methods, and
relationships.

```md
[Insert Image]
```

#### PM-8.1.2 Object Diagram
Represents a snapshot of the objects in a system at a specific point in time.

```md
[Insert Image]
```

#### PM-8.1.3 Component Diagram
Depicts the organization and dependencies among software components.

```md
[Insert Image]
```

#### PM-8.1.4 Deployment Diagram
Illustrates the internal structure of a class and the collaborations that this structure makes
possible.

```md
[Insert Image]
```

#### PM-8.1.5 Composite Structure Diagram
Groups related classes into packages.

```md
[Insert Image]
```

#### PM-8.1.6 Package Diagram
Defines custom stereotypes, tagged values, and constraints.

```md
[Insert Image]
```

#### PM-8.1.7 Profile Diagram

```md
[Insert Image]
```

### PM-8.2 Behavioural Diagrams

#### PM-8.2.1 Use Case Interaction Diagram
Captures the functional requirements of the system by showing actors and their interactions
with use cases.

```md
[Insert Image]
```

#### PM-8.2.2 Sequence Diagram
Represents object interactions arranged in a time sequence.

```md
[Insert Image]
```

#### PM-8.2.3 Activity Diagram
Shows the flow of control or object flow with emphasis on the sequence and conditions of the
flow.

```md
[Insert Image]
```

#### PM-8.2.4 State Machine Diagram
Describes the states of an object and the transitions between these states.

```md
[Insert Image]
```

#### PM-8.2.5 Communication Diagram
Focuses on the interactions between objects and the sequence of messages.

```md
[Insert Image]
```

#### PM-8.2.6 Interaction Overview Diagram
Combines elements of activity and sequence diagrams to show the control flow between
different interactions.

```md
[Insert Image]
```

#### PM-8.2.7 Timing Diagram
Shows interactions when the primary purpose is to depict time constraints

```md
[Insert Image]
```

#### PM-8.2.7 Use Case Dependency Diagram
Represents the functionality provided by a system in terms of actors, their goals (represented
as use cases), and any dependencies between those use cases.

```md
[Insert Image]
```

## PM-9 Implementation

### PM-9.1 GitHub

### PM-9.2 File Structure

### PM-9.3 Code Snippets

## PM-10 Testing

### PM-10.1 Test Cases

### PM-10.1 Unit Tests

#### PM-10.2 Integration Tests

#### PM-10.3 System Tests

#### PM-10.4 Acceptance Testing

## PM-10.2 Test Results

## PM-11 Final Submission PDF

### PM-11.1 Demo Video Link





---

## Document History

| Change Request ID | Date     | Changed PM-Sections | sDescription of Changes                                                                                 | Submitted By |
|-------------------|----------|---------------------|---------------------------------------------------------------------------------------------------------|--------------|
| CR-00             | -        | -                   | -                                                                                                       | -            |
| CR-01             | 15/08/25 | PM-6, PM-7          | Inserted 'PM-6 Software Architecture etc.' and moved 'PM-6 Document History' to 'PM-7 Document History' | Fletcher     |
| CR-02             | 16/08/25 | PM TEMPLATE UPDATE  | Changed most PM sections.                                                                               | Fletcher     |