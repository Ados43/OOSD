/// OpMove.java
// JSON-friendly record returned by the external server: (opX, opRotate).
// - opX: target column (0 = leftmost).
// - opRotate: number of clockwise rotations from spawn (0..3).
// Deserialized by Jackson in ExternalClient and consumed by TetrisGame.

package org.oosd.teamorange_finalsubmission;

// External move record
public record OpMove(int opX, int opRotate) {
}
