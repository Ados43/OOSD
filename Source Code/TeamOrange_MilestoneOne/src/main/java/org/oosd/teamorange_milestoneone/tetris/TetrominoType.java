package org.oosd.teamorange_milestoneone.tetris;

public enum TetrominoType {
    I('I', Color.CYAN, new char[][][] {
            { // 0°
                    {' ', ' ', ' ', ' '},
                    {'I', 'I', 'I', 'I'},
                    {' ', ' ', ' ', ' '},
                    {' ', ' ', ' ', ' '}
            },
            { // 90°
                    {' ', ' ', 'I', ' '},
                    {' ', ' ', 'I', ' '},
                    {' ', ' ', 'I', ' '},
                    {' ', ' ', 'I', ' '}
            },
            { // 180°
                    {' ', ' ', ' ', ' '},
                    {' ', ' ', ' ', ' '},
                    {'I', 'I', 'I', 'I'},
                    {' ', ' ', ' ', ' '}
            },
            { // 270°
                    {' ', 'I', ' ', ' '},
                    {' ', 'I', ' ', ' '},
                    {' ', 'I', ' ', ' '},
                    {' ', 'I', ' ', ' '}
            }
    }),

    O('O', Color.YELLOW, new char[][][] {
            { // All rotations same
                    {' ', ' ', ' ', ' '},
                    {' ', 'O', 'O', ' '},
                    {' ', 'O', 'O', ' '},
                    {' ', ' ', ' ', ' '}
            }
    }),

    J('J', Color.BLUE, new char[][][] {
            { // 0°
                    {'J', ' ', ' ', ' '},
                    {'J', 'J', 'J', ' '},
                    {' ', ' ', ' ', ' '},
                    {' ', ' ', ' ', ' '}
            },
            { // 90°
                    {' ', 'J', 'J', ' '},
                    {' ', 'J', ' ', ' '},
                    {' ', 'J', ' ', ' '},
                    {' ', ' ', ' ', ' '}
            },
            { // 180°
                    {' ', ' ', ' ', ' '},
                    {'J', 'J', 'J', ' '},
                    {' ', ' ', 'J', ' '},
                    {' ', ' ', ' ', ' '}
            },
            { // 270°
                    {' ', 'J', ' ', ' '},
                    {' ', 'J', ' ', ' '},
                    {'J', 'J', ' ', ' '},
                    {' ', ' ', ' ', ' '}
            }
    }),

    L('L', Color.ORANGE, new char[][][] {
            { // 0°
                    {' ', ' ', 'L', ' '},
                    {'L', 'L', 'L', ' '},
                    {' ', ' ', ' ', ' '},
                    {' ', ' ', ' ', ' '}
            },
            { // 90°
                    {' ', 'L', ' ', ' '},
                    {' ', 'L', ' ', ' '},
                    {' ', 'L', 'L', ' '},
                    {' ', ' ', ' ', ' '}
            },
            { // 180°
                    {' ', ' ', ' ', ' '},
                    {'L', 'L', 'L', ' '},
                    {'L', ' ', ' ', ' '},
                    {' ', ' ', ' ', ' '}
            },
            { // 270°
                    {'L', 'L', ' ', ' '},
                    {' ', 'L', ' ', ' '},
                    {' ', 'L', ' ', ' '},
                    {' ', ' ', ' ', ' '}
            }
    }),

    S('S', Color.GREEN, new char[][][] {
            { // 0°
                    {' ', 'S', 'S', ' '},
                    {'S', 'S', ' ', ' '},
                    {' ', ' ', ' ', ' '},
                    {' ', ' ', ' ', ' '}
            },
            { // 90°
                    {' ', 'S', ' ', ' '},
                    {' ', 'S', 'S', ' '},
                    {' ', ' ', 'S', ' '},
                    {' ', ' ', ' ', ' '}
            },
            { // 180°
                    {' ', ' ', ' ', ' '},
                    {' ', 'S', 'S', ' '},
                    {'S', 'S', ' ', ' '},
                    {' ', ' ', ' ', ' '}
            },
            { // 270°
                    {'S', ' ', ' ', ' '},
                    {'S', 'S', ' ', ' '},
                    {' ', 'S', ' ', ' '},
                    {' ', ' ', ' ', ' '}
            }
    }),

    T('T', Color.PURPLE, new char[][][] {
            { // 0°
                    {' ', 'T', ' ', ' '},
                    {'T', 'T', 'T', ' '},
                    {' ', ' ', ' ', ' '},
                    {' ', ' ', ' ', ' '}
            },
            { // 90°
                    {' ', 'T', ' ', ' '},
                    {' ', 'T', 'T', ' '},
                    {' ', 'T', ' ', ' '},
                    {' ', ' ', ' ', ' '}
            },
            { // 180°
                    {' ', ' ', ' ', ' '},
                    {'T', 'T', 'T', ' '},
                    {' ', 'T', ' ', ' '},
                    {' ', ' ', ' ', ' '}
            },
            { // 270°
                    {' ', 'T', ' ', ' '},
                    {'T', 'T', ' ', ' '},
                    {' ', 'T', ' ', ' '},
                    {' ', ' ', ' ', ' '}
            }
    }),

    Z('Z', Color.RED, new char[][][] {
            { // 0°
                    {'Z', 'Z', ' ', ' '},
                    {' ', 'Z', 'Z', ' '},
                    {' ', ' ', ' ', ' '},
                    {' ', ' ', ' ', ' '}
            },
            { // 90°
                    {' ', ' ', 'Z', ' '},
                    {' ', 'Z', 'Z', ' '},
                    {' ', 'Z', ' ', ' '},
                    {' ', ' ', ' ', ' '}
            },
            { // 180°
                    {' ', ' ', ' ', ' '},
                    {'Z', 'Z', ' ', ' '},
                    {' ', 'Z', 'Z', ' '},
                    {' ', ' ', ' ', ' '}
            },
            { // 270°
                    {' ', 'Z', ' ', ' '},
                    {'Z', 'Z', ' ', ' '},
                    {'Z', ' ', ' ', ' '},
                    {' ', ' ', ' ', ' '}
            }
    });

    private final char symbol;
    private final Color color;
    private final char[][][] rotations; // [rotation][row][col] - all 4x4

    TetrominoType(char symbol, Color color, char[][][] rotations) {
        this.symbol = symbol;
        this.color = color;
        this.rotations = rotations;
    }

    public char getSymbol() { return symbol; }
    public Color getColor() { return color; }

    public char[][] getRotation(int rotation) {
        return rotations[rotation % 4]; // All pieces have 4 rotations
    }

    // Get active positions for collision detection and rendering
    public List<Position> getActivePositions(int rotation) {
        char[][] shape = getRotation(rotation);
        List<Position> positions = new ArrayList<>();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (shape[row][col] != ' ') {
                    positions.add(new Position(col, row));
                }
            }
        }
        return positions;
    }

    // Check if position is filled in current rotation
    public boolean isFilledAt(int rotation, int row, int col) {
        if (row < 0 || row >= 4 || col < 0 || col >= 4) return false;
        return getRotation(rotation)[row][col] != ' ';
    }

    // For debugging/testing
    public void printRotation(int rotation) {
        char[][] shape = getRotation(rotation);
        System.out.println("Piece " + symbol + " rotation " + rotation + ":");
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                System.out.print(shape[row][col]);
            }
            System.out.println();
        }
    }
}