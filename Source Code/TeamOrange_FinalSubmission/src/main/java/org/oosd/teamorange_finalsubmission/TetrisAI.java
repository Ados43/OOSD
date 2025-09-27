package org.oosd.teamorange_finalsubmission;

import java.util.*;

// Tetris AI heuristic
public class TetrisAI {

    // Best move record
    public record BestMove(int col, int rotation, int score) {
        public static BestMove of(int col, int rotation) {
            return new BestMove(col, rotation, 0);
        }
    }

    // Fields
    private final char[][] shapes;
    private final int W, H;
    private final BoardEvaluator evaluator = new BoardEvaluator();

    // Constructor
    public TetrisAI(char[][] shapes, int boardW, int boardH) {
        this.shapes = shapes;
        this.W = boardW;
        this.H = boardH;
    }

    // Find best move
    public BestMove findBestMove(char[][] board, int pieceIndex) {
        int bestScore = Integer.MIN_VALUE;
        BestMove best = null;
        int[] rotations = uniqueRotationsFor(pieceIndex);
        for (int rot : rotations) {
            Bounds b = pieceBounds(pieceIndex, rot);
            int minCol = -b.minX;
            int maxCol = (W - 1) - b.maxX;
            for (int col = minCol; col <= maxCol; col++) {
                char[][] sim = copyBoard(board);
                int dropY = getDropRow(sim, pieceIndex, rot, col);
                if (dropY < -b.minY) continue;
                place(sim, pieceIndex, rot, col, dropY);
                sim = clearFullLines(sim);
                int score = evaluator.evaluateBoard(sim);
                if (score > bestScore) {
                    bestScore = score;
                    best = new BestMove(col, rot & 3, score);
                }
            }
        }
        return best;
    }

    // Bounds holder
    private static final class Bounds {
        int minX, maxX, minY, maxY;
    }

    // Piece bounds
    private Bounds pieceBounds(int piece, int rot) {
        Bounds bb = new Bounds();
        bb.minX = 4;
        bb.minY = 4;
        bb.maxX = -1;
        bb.maxY = -1;
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                char cell = shapes[piece][rotateIndex(px, py, rot)];
                if (cell != ' ') {
                    if (px < bb.minX) bb.minX = px;
                    if (px > bb.maxX) bb.maxX = px;
                    if (py < bb.minY) bb.minY = py;
                    if (py > bb.maxY) bb.maxY = py;
                }
            }
        if (bb.maxX == -1) {
            bb.minX = bb.minY = 0;
            bb.maxX = bb.maxY = 0;
        }
        return bb;
    }

    // Drop row
    private int getDropRow(char[][] b, int piece, int rot, int col) {
        int y = 0;
        while (canPlace(b, piece, rot, col, y)) y++;
        return y - 1;
    }

    // Placement check
    private boolean canPlace(char[][] b, int piece, int rot, int col, int row) {
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                char c = shapes[piece][rotateIndex(px, py, rot)];
                if (c == ' ') continue;
                int x = col + px, y = row + py;
                if (x < 0 || x >= W || y < 0 || y >= H) return false;
                if (b[x][y] != ' ') return false;
            }
        return true;
    }

    // Place piece
    private void place(char[][] b, int piece, int rot, int col, int row) {
        for (int px = 0; px < 4; px++)
            for (int py = 0; py < 4; py++) {
                char c = shapes[piece][rotateIndex(px, py, rot)];
                if (c == ' ') continue;
                int x = col + px, y = row + py;
                if (x >= 0 && x < W && y >= 0 && y < H) b[x][y] = 'X';
            }
    }

    // Clear full lines
    private char[][] clearFullLines(char[][] b) {
        char[][] out = copyBoard(b);
        int dstY = H - 1;
        for (int y = H - 1; y >= 0; y--) {
            boolean full = true;
            for (int x = 0; x < W; x++) {
                if (out[x][y] == ' ') {
                    full = false;
                    break;
                }
            }
            if (!full) {
                if (dstY != y) {
                    for (int x = 0; x < W; x++) out[x][dstY] = out[x][y];
                }
                dstY--;
            }
        }
        for (int y = dstY; y >= 0; y--)
            for (int x = 0; x < W; x++)
                out[x][y] = ' ';
        return out;
    }

    // Copy board
    private char[][] copyBoard(char[][] src) {
        char[][] dst = new char[W][H];
        for (int x = 0; x < W; x++) System.arraycopy(src[x], 0, dst[x], 0, H);
        return dst;
    }

    // Unique rotations
    private int[] uniqueRotationsFor(int piece) {
        List<Integer> uniq = new ArrayList<>();
        Set<String> shapesSeen = new HashSet<>();
        for (int r = 0; r < 4; r++) {
            StringBuilder sb = new StringBuilder(16);
            for (int py = 0; py < 4; py++)
                for (int px = 0; px < 4; px++)
                    sb.append(shapes[piece][rotateIndex(px, py, r)]);
            if (shapesSeen.add(sb.toString())) uniq.add(r);
        }
        return uniq.stream().mapToInt(Integer::intValue).toArray();
    }

    // Rotate index
    private static int rotateIndex(int px, int py, int r) {
        return switch (r & 3) {
            case 0 -> py * 4 + px;
            case 1 -> 12 + py - (px * 4);
            case 2 -> 15 - (py * 4) - px;
            default -> 3 - py + (px * 4);
        };
    }

    // Board evaluator
    static class BoardEvaluator {
        // Evaluate board
        int evaluateBoard(char[][] board) {
            int heightScore = maxHeight(board);
            int holesScore = holes(board);
            int linesCleared = clearedLines(board);
            int bumpinessScore = bumpiness(board);
            return (-4 * heightScore) + (3 * linesCleared) - (5 * holesScore) - (2 * bumpinessScore);
        }

        // Max column height
        private int maxHeight(char[][] b) {
            int H = b[0].length, max = 0;
            for (char[] chars : b) {
                int h = 0;
                for (int y = 0; y < H; y++) {
                    if (chars[y] != ' ') {
                        h = H - y;
                        break;
                    }
                }
                if (h > max) max = h;
            }
            return max;
        }

        // Count holes
        private int holes(char[][] b) {
            int H = b[0].length, holes = 0;
            for (char[] chars : b) {
                boolean blockSeen = false;
                for (int y = 0; y < H; y++) {
                    if (chars[y] != ' ') blockSeen = true;
                    else if (blockSeen) holes++;
                }
            }
            return holes;
        }

        // Count full lines
        private int clearedLines(char[][] b) {
            int H = b[0].length, count = 0;
            for (int y = 0; y < H; y++) {
                boolean full = true;
                for (char[] chars : b) {
                    if (chars[y] == ' ') {
                        full = false;
                        break;
                    }
                }
                if (full) count++;
            }
            return count;
        }

        // Surface bumpiness
        private int bumpiness(char[][] b) {
            int W = b.length, H = b[0].length;
            int[] heights = new int[W];
            for (int x = 0; x < W; x++) {
                int h = 0;
                for (int y = 0; y < H; y++) {
                    if (b[x][y] != ' ') {
                        h = H - y;
                        break;
                    }
                }
                heights[x] = h;
            }
            int sum = 0;
            for (int x = 0; x < W - 1; x++) sum += Math.abs(heights[x] - heights[x + 1]);
            return sum;
        }
    }
}
