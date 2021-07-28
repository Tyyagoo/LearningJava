package maze;

import java.util.*;

public class Maze {
    enum Cell{
        EMPTY(' '),
        WALL('\u2588');

        private final char graphic;

        Cell(char graphic) {
            this.graphic = graphic;
        }

        public void draw() {
            System.out.printf("%s%s", graphic, graphic);
        }
    }

    private static final int minWeight = 1;
    private static final int maxWeight = 9;

    private final int x;
    private final int y;
    private Cell[][] cells;

    private int[][] adjacencyMatrix;

    public Maze(int size) {
        this(size, size);
    }

    public Maze(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                cells[i][j].draw();
            }
            System.out.println();
        }
    }

    public String saveMaze() {
        StringBuilder mazeContent = new StringBuilder();
        mazeContent.append(x);
        mazeContent.append("\n");

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                mazeContent.append(adjacencyMatrix[i][j]);
                mazeContent.append(" ");
            }
            mazeContent.append("\n");
        }
        return mazeContent.toString();
    }

    public void loadMaze(int[][] adjMatrix) {
        cells = getInitialGrid();
        adjacencyMatrix = adjMatrix;
        modifyMazeByMst(getMinimumSpanningTree(adjacencyMatrix));
    }

    public void generate() {
        cells = getInitialGrid();
        adjacencyMatrix = getAdjacencyMatrix();
        int[][] mst = getMinimumSpanningTree(adjacencyMatrix);
        modifyMazeByMst(mst);
    }

    private Cell[][] getInitialGrid() {
        Cell[][] grid = new Cell[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                grid[i][j] = Cell.WALL;
            }
        }
        return grid;
    }

    private int getRandomWeight() {
        Random randomizer = new Random();
        return randomizer.nextInt(maxWeight) + minWeight;
    }

    private int[][] getAdjacencyMatrix() {
        /*
            The maze must be surrounded by walls, so the fillable space is equal to size - 2 in both directions.
         */
        int xSize = (x % 2 != 0) ? (x - 2) / 2 + 1 : (x - 2) / 2;
        int ySize = (y % 2 != 0) ? (y - 2) / 2 + 1 : (y - 2) / 2;

        int[][] matrix = new int[xSize * ySize][xSize * ySize];

        int vertex = 0;
        int weight = 0;
        for (int i = 0; i < matrix.length - 1; i++) {
            weight = getRandomWeight();
            if (vertex + 1 < ((vertex / ySize) + 1) * ySize) {
                matrix[vertex][vertex + 1] = weight;
                matrix[vertex + 1][vertex] = weight;
            }
            weight = getRandomWeight();
            if (vertex + ySize < matrix.length) {
                matrix[vertex][vertex + ySize] = weight;
                matrix[vertex + ySize][vertex] = weight;
            }
            vertex++;
        }
        return matrix;
    }

    private int[][] getMinimumSpanningTree(int[][] adjMatrix) {
        /*
            Apply Prim's algorithm to some adjacencyMatrix to get the passages of the maze.
         */

        int[][] mst = new int[adjMatrix.length][adjMatrix.length];
        Set<Integer> addedNodes = new HashSet<>();
        addedNodes.add(0);
        int nextNode = 0;
        int currentNode = 0;
        while (addedNodes.size() < adjMatrix.length) {
            int minValues = adjMatrix.length * adjMatrix.length;
            for (int eachNode : addedNodes) {
                for (int j = 0; j < adjMatrix.length; j++) {
                    if (adjMatrix[eachNode][j] < minValues && adjMatrix[eachNode][j] > 0) {
                        if (!addedNodes.contains(j)) {
                            minValues = adjMatrix[eachNode][j];
                            currentNode = eachNode;
                            nextNode = j;
                        }
                    }
                }
            }
            addedNodes.add(nextNode);
            mst[currentNode][nextNode] = 1;
            mst[nextNode][currentNode] = 1;
        }
        return mst;
    }

    private void modifyMazeByMst(int[][] mst) {
        int xSize = (x % 2 != 0) ? (x - 2) / 2 + 1 : (x - 2) / 2;
        int ySize = (y % 2 != 0) ? (y - 2) / 2 + 1 : (y - 2) / 2;
        cells[1][0] = Cell.EMPTY;
        int currentEdge = 0;
        int mazeRow = 1;
        int mazeCol = 1;
        while (currentEdge < xSize * ySize) {
            if (currentEdge + 1 < (currentEdge / ySize + 1) * ySize && mst[currentEdge][currentEdge + 1] == 1) {
                cells[mazeRow][mazeCol] = Cell.EMPTY;
                cells[mazeRow][mazeCol + 1] = Cell.EMPTY;
                cells[mazeRow][mazeCol + 2] = Cell.EMPTY;
            }
            if (currentEdge + ySize < xSize * ySize) {
                if (mst[currentEdge][currentEdge + ySize] == 1) {
                    cells[mazeRow][mazeCol] = Cell.EMPTY;
                    cells[mazeRow + 1][mazeCol] = Cell.EMPTY;
                    cells[mazeRow + 2][mazeCol] = Cell.EMPTY;
                }
            }
            if (currentEdge + 1 <= (currentEdge / ySize + 1) * ySize - 1) {
                mazeCol += 2;
            } else {
                mazeRow += 2;
                mazeCol = 1;
            }

            currentEdge++;
            if (currentEdge == xSize * ySize) {
                cells[mazeRow - 2][y - 1] = Cell.EMPTY;
                cells[mazeRow - 2][y - 2] = Cell.EMPTY;
            }
        }
    }
}
