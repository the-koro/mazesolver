package com.theko.mazesolver;

import java.util.Stack;

public class MazeSolver {
    private final Maze maze;
    private final int[][] grid;
    private int currentX, currentY;
    private final Stack<int[]> backtrackStack;
    private boolean foundExit = false;

    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Вниз, вправо, вверх, влево

    public MazeSolver(Maze maze) {
        this.maze = maze;
        this.grid = new int[maze.height][maze.width];
        currentX = 1;
        currentY = 1;
        grid[currentY][currentX] = 1;
        backtrackStack = new Stack<>();
    }

    public void tick() {
        if (foundExit) return;

        for (int[] dir : DIRECTIONS) {
            int newX = currentX + dir[0];
            int newY = currentY + dir[1];

            if (isValidMove(newX, newY)) {
                moveTo(newX, newY);
                return;
            }
        }

        // Если нет доступных ходов, используем объединенный стек для возвращения к предыдущему повороту или позиции
        if (!backtrackStack.isEmpty()) {
            int[] lastPosition = backtrackStack.pop();
            currentX = lastPosition[0];
            currentY = lastPosition[1];
            grid[currentY][currentX] = lastPosition[2]; // Восстанавливаем тип клетки (поворот или обычная клетка)
        }
    }

    private boolean isValidMove(int x, int y) {
        return x > 0 && x < maze.width - 1 &&
               y > 0 && y < maze.height - 1 &&
               maze.grid[y][x] == false &&
               grid[y][x] == 0;
    }

    private void moveTo(int x, int y) {
        int cellType = isTurnPoint(x, y) ? 2 : 1; // 2 — поворот, 1 — обычная клетка
        backtrackStack.push(new int[]{currentX, currentY, cellType});
        currentX = x;
        currentY = y;
        grid[y][x] = cellType;

        if (x == maze.width - 2 && y == maze.height - 2) {
            foundExit = true;
            markShortestPath();
        }
    }

    private boolean isTurnPoint(int x, int y) {
        int count = 0;
        for (int[] dir : DIRECTIONS) {
            int nx = x + dir[0], ny = y + dir[1];
            if (nx >= 0 && nx < maze.width && ny >= 0 && ny < maze.height && !maze.grid[ny][nx]) {
                count++;
            }
        }
        return count > 1;
    }

    private void markShortestPath() {
        while (!backtrackStack.isEmpty()) {
            int[] step = backtrackStack.pop();
            grid[step[1]][step[0]] = 3;
        }
    }

    public int[][] getCurrentGrid() {
        return grid;
    }

    public boolean isExitFound() {
        return foundExit;
    }
}
