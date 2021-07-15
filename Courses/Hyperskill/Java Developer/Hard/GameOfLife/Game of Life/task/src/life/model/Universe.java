package life.model;

import life.exceptions.UniverseOutOfIndex;

public class Universe {

    public enum Cell { DEAD, ALIVE };
    private Cell[][] state;
    private int n;

    Universe(int n) {
        this.n = n;
        state = new Cell[n][n];
    }

    public int getNeighborsCount(int i, int j) {
        checkIndex(i, j);
        int count = 0;
        int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1},{1,1}, {1,-1}, {-1,1}, {-1,-1}};

        for (int[] dir: directions) {
            int desiredX = i + dir[0];
            int desiredY = j + dir[1];

            int xIndex = (desiredX >= 0) ? ((desiredX < n) ? desiredX : (n - desiredX) ) : (n + desiredX);
            int yIndex = (desiredY >= 0) ? ((desiredY < n) ? desiredY : (n - desiredY) ) : (n + desiredY);
            if (getCellAt(xIndex, yIndex) == Cell.ALIVE) count++;
        }

        return count;
    }

    private void checkIndex(int i, int j) throws UniverseOutOfIndex {
        if ((i < n && j < n) && (i >= 0 && j >= 0)) return;
        throw new UniverseOutOfIndex(String.format("Oops, this is an finite universe. [%d, %d]", i, j));
    }

    public Cell getCellAt(int i, int j) throws UniverseOutOfIndex {
        checkIndex(i, j);
        return state[i][j];
    }

    public void setCellAt(Cell cell, int i, int j) throws UniverseOutOfIndex {
        checkIndex(i, j);
        state[i][j] = cell;
    }

    public void thanosAll() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                state[i][j] = Cell.DEAD;
            }
        }
    }
}
