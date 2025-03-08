package com.theko.mazesolver;

import java.util.Random;

import javax.swing.JFrame;

public class MainApp {
    public static void main(String[] args) throws InterruptedException {
        //System.out.println(maze.toString());

        JFrame frame = new JFrame("Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (!Thread.currentThread().isInterrupted()) {
            MazeGenerator mazeGenerator = new DFSMazeGenerator(60);
            Maze maze = mazeGenerator.generate(new Random().nextLong());
            MazePanel mazePanel = new MazePanel(maze, 8);
            frame.add(mazePanel);
            frame.pack();
            frame.setResizable(false);
            frame.setVisible(true);

            MazeSolver solver = new MazeSolver(maze);
            while (!solver.isExitFound()) {
                solver.tick();

                int[][] currentGrid = solver.getCurrentGrid();
                mazePanel.updateSolverGrid(currentGrid);

                Thread.sleep(17);
            }
            Thread.sleep(5000);
        }
    }
}