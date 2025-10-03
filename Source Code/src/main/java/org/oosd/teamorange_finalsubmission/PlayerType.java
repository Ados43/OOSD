/// PlayerType.java
// Enum of available player controllers:
// - HUMAN: keyboard control
// - AI: built-in heuristic AI (TetrisAI)
// - EXTERNAL: moves provided by the external server via ExternalClient

package org.oosd.teamorange_finalsubmission;

// Player modes
public enum PlayerType {
    HUMAN,
    AI,
    EXTERNAL
}
