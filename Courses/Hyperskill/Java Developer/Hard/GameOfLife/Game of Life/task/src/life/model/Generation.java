package life.model;

import java.util.Random;

public class Generation {

    private Universe universe;
    private int size;
    private int seed;

    Generation(int n, int s) {
        this.size = n;
        this.seed = s;
        this.universe = new Universe(size);
    }

    public Universe getUniverse() {
        return universe;
    }

    public int getSize() {
        return size;
    }

    public int getSeed() {
        return seed;
    }

    public Generation getNextGeneration() {
        /*
        A live cell survives if it has two or three live neighbors; otherwise, it dies of boredom (<2) or overpopulation (>3).
        A dead cell is reborn if it has exactly three live neighbors.
         */
        Generation gen = new Generation(size, seed);
        gen.getUniverse().thanosAll();

        int[][] neighbors = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                neighbors[i][j] = universe.getNeighborsCount(i, j);
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (universe.getCellAt(i, j) == Universe.Cell.ALIVE) {
                    if (!(neighbors[i][j] < 2 || neighbors[i][j] > 3)) gen.universe.setCellAt(Universe.Cell.ALIVE, i, j);
                } else {
                    if (neighbors[i][j] == 3) gen.universe.setCellAt(Universe.Cell.ALIVE, i, j);
                }
            }
        }


        return gen;
    }

    public static Generation getInitialGeneration(int n, int s) {
        Generation gen = new Generation(n, s);
        Random randomizer = new Random(s);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                gen.getUniverse().setCellAt((randomizer.nextBoolean() ? Universe.Cell.ALIVE : Universe.Cell.DEAD), i, j);
            }
        }
        return gen;
    }
}
