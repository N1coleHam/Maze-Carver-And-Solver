# Maze Carver and Solver

## Overview  
This project implements a maze generator and solver with a graphical user interface (GUI). Users can create mazes with different base shapes and generation algorithms, then find the shortest path through the maze.

## Features

### GUI Layout  
- **Maze Display Area:** Large top region showing the current maze visually.  
- **Control Panel:** Bottom region with options and buttons for customizing maze generation and solving.

### Maze Base Shape  
- **Grid:** Default option creating mazes with rectangular rooms.  
- **Voronoi:** Creates irregular, more organic maze patterns.

### Maze Generation Options  
- **Do not delete any walls:** Keeps the maze fully enclosed.  
- **Delete random walls:** Creates random openings for maze paths but can result in overly easy or unsolvable mazes.  
- **Run (randomized) Kruskal:**  
  - Generates complex, always solvable mazes.  
  - Outperforms simple random wall deletion by ensuring solvability.

### Maze Solving  
- **Find shortest path:** Finds the shortest path between two endpoints in the maze.

## How It Works

- **Maze Carving:** Uses Kruskal's algorithm to carve the maze by selectively removing walls without breaking solvability.  
- **Maze Solving:** Uses Dijkstra's algorithm to find the shortest route from start to finish.

## Usage

1. Select the maze base shape from the dropdown.  
2. Click "Generate new maze" to create a fresh maze with the selected shape.  
3. Choose the maze generator method (e.g., Kruskal) to carve the maze.  
4. Use "Find shortest path" to solve the maze and visualize the solution.

## Technologies  
- Java Swing - JFrame
- Kruskal’s algorithm for maze carving  
- Dijkstra’s algorithm for maze solving
