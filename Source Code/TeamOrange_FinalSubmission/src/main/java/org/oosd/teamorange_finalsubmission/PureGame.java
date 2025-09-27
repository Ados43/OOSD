package org.oosd.teamorange_finalsubmission;

import java.util.Arrays;

public class PureGame {
    private int width;
    private int height;
    private int[][] cells;        // [rows][cols] => [y][x], 0 empty, 1 filled
    private int[][] currentShape; // minimal bounding box, 0/1
    private int[][] nextShape;    // minimal bounding box, 0/1

    public PureGame() {
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[][] getCells() {
        return cells;
    }

    public void setCells(int[][] cells) {
        this.cells = cells;
    }

    public int[][] getCurrentShape() {
        return currentShape;
    }

    public void setCurrentShape(int[][] currentShape) {
        this.currentShape = currentShape;
    }

    public int[][] getNextShape() {
        return nextShape;
    }

    public void setNextShape(int[][] nextShape) {
        this.nextShape = nextShape;
    }

    @Override
    public String toString() {
        return "PureGame{" +
                "width=" + width +
                ", height=" + height +
                ", cells=" + Arrays.deepToString(cells) +
                ", currentShape=" + Arrays.deepToString(currentShape) +
                ", nextShape=" + Arrays.deepToString(nextShape) +
                '}';
    }
}
