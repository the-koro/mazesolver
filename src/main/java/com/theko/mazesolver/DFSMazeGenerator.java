package com.theko.mazesolver;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class DFSMazeGenerator implements MazeGenerator {
    private final int width, height;
    private final boolean[][] grid;
    private Random random;

    private static final List<int[]> DIRECTIONS = List.of(
        new int[]{0, -2}, new int[]{0, 2}, 
        new int[]{-2, 0}, new int[]{2, 0}
    );

    public DFSMazeGenerator(int width, int height) {
        this.width = (width % 2 == 0 ? width + 1 : width);
        this.height = (height % 2 == 0 ? height + 1 : height);
        this.grid = new boolean[this.height][this.width];
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                grid[y][x] = true;
            }
        }
    }

    public DFSMazeGenerator(int size) {
        this(size, size);
    }

    private void dfs(int x, int y) {
        grid[y][x] = false;
        List<int[]> directions = new ArrayList<>(DIRECTIONS);
        Collections.shuffle(directions, random);

        for (int[] dir : directions) {
            int nx = x + dir[0], ny = y + dir[1];
            if (isValid(nx, ny)) {
                grid[y + dir[1] / 2][x + dir[0] / 2] = false;
                dfs(nx, ny);
            }
        }
    }

    private boolean isValid(int x, int y) {
        return x > 0 && y > 0 && x < width - 1 && y < height - 1 && grid[y][x];
    }

    private void placeOpening(int x, int y, int dy) {
        while (y > 0 && y < height - 1 && grid[y][x]) {
            y += dy;
        }
        if (grid[y][x - 1] && grid[y][x + 1]) {
            grid[y][x + (x > 1 ? -1 : 1)] = false;
        }
        line(grid, x, y, x, (dy == -1 ? 0 : height - 1), false);
    }

    @Override
    public Maze generate(long seed) {
        this.random = (seed == -1) ? new Random() : new Random(seed);

        dfs(1, 1);

        placeOpening(1, 1, -1);
        placeOpening(width - 2, height - 2, 1);

        return new Maze(width, height, grid);
    }
}
