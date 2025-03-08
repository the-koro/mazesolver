package com.theko.mazesolver;

public interface MazeGenerator {
    Maze generate(long seed);
    
    default void line(boolean[][] grid, int x1, int y1, int x2, int y2, boolean wall) {
        int dx = Integer.compare(x2, x1);
        int dy = Integer.compare(y2, y1);

        while (x1 != x2 || y1 != y2) {
            grid[y1][x1] = wall;
            x1 += dx;
            y1 += dy;
        }
        grid[y2][x2] = wall;
    }
}
