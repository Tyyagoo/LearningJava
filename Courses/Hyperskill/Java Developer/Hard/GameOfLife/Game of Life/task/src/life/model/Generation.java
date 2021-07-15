package life.model;

import java.util.Random;

public class Generation {

    private final Universe universe;
    private final int size;
    private int population = 0;
    private int id = 1;

    Generation(int n, int ... id) {
        this.size = n;
        if (id.length != 0) this.id = id[0];
        this.universe = new Universe(size);
    }

    public Universe getUniverse() {
        return universe;
    }

    public int getSize() {
        return size;
    }

    public int getPopulation() {
        return population;
    }

    public int getId() { return id; }

    public Generation getNextGeneration() {
        /*
        A live cell survives if it has two or three live neighbors; otherwise, it dies of boredom (<2) or overpopulation (>3).
        A dead cell is reborn if it has exactly three live neighbors.
         */
        Generation gen = new Generation(size, id+1);
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
                    if (!(neighbors[i][j] < 2 || neighbors[i][j] > 3)) {
                        gen.universe.setCellAt(Universe.Cell.ALIVE, i, j);
                        gen.population++;
                    }
                } else {
                    if (neighbors[i][j] == 3) {
                        gen.universe.setCellAt(Universe.Cell.ALIVE, i, j);
                        gen.population++;
                    }
                }
            }
        }

        return gen;
    }

    public static Generation getInitialGeneration(int n) {
        Generation gen = new Generation(n);
        Random randomizer = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Universe.Cell cell = randomizer.nextBoolean() ? Universe.Cell.ALIVE : Universe.Cell.DEAD;
                gen.getUniverse().setCellAt(cell, i, j);
                if (cell == Universe.Cell.ALIVE) gen.population++;
            }
        }
        return gen;
    }
}
