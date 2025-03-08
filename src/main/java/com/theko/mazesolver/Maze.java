package com.theko.mazesolver;

public class Maze {
    public final int width, height;
    public final boolean[][] grid;

    public Maze(int width, int height, boolean[][] grid) {
        this.width = width;
        this.height = height;
        this.grid = grid;
    }

    public void drawLine(int x1, int y1, int x2, int y2, boolean wall) {
        int dx = Integer.compare(x2, x1);
        int dy = Integer.compare(y2, y1);

        while (x1 != x2 || y1 != y2) {
            grid[y1][x1] = wall;
            x1 += dx;
            y1 += dy;
        }
        grid[y2][x2] = wall;
    }

    public void drawRectangle(int x1, int y1, int x2, int y2, boolean wall) {
        for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
            for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                grid[y][x] = wall;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder maze = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                maze.append(grid[y][x] ? '█' : ' ');
            }
            maze.append('\n');
        }
        return maze.toString();
    }
}
