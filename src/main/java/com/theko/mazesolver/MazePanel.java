package com.theko.mazesolver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MazePanel extends JPanel {
    private final Maze maze;
    private int[][] solverGrid;
    private int blockSize;

    public MazePanel(Maze maze, int blockSize) {
        if (maze == null || blockSize <= 0) {
            throw new IllegalArgumentException("Invalid arguments.");
        }
        this.maze = maze;
        this.blockSize = blockSize;
        setPreferredSize(new Dimension(maze.width * blockSize, maze.height * blockSize));
        setBackground(Color.BLACK);
    }

    public void setBlockSize(int blockSize) {
        if (blockSize <= 0) {
            throw new IllegalArgumentException("Invalid block size.");
        }
        this.blockSize = blockSize;
        super.repaint();
    }

    public void updateSolverGrid(int[][] solverGrid) {
        if (solverGrid == null || (solverGrid.length != maze.height || solverGrid[0].length != maze.width)) {
            throw new IllegalArgumentException("Invalid size of the solver grid.");
        }
        this.solverGrid = solverGrid;
        super.repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int y = 0; y < maze.height; y++) {
            for (int x = 0; x < maze.width; x++) {
                if (maze.grid[y][x]) {
                    g.setColor(Color.WHITE);
                    g.fillRect(x * blockSize, y * blockSize, blockSize, blockSize);
                }

                if (solverGrid != null) {
                    switch (solverGrid[y][x]) {
                        case 1: g.setColor(Color.RED); break;
                        case 2: g.setColor(new Color(255, 69, 0)); break;
                        case 3: g.setColor(Color.GREEN);break;
                    }

                    if (solverGrid[y][x] > 0) {
                        g.fillRect(x * blockSize + blockSize / 4, y * blockSize + blockSize / 4, blockSize / 2, blockSize / 2);
                    }
                }
            }
        }
    }
}
