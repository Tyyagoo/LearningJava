package life.model;

public class Game {

    private Generation currentGeneration;

    public Game(int mapSize) {
        currentGeneration = Generation.getInitialGeneration(mapSize);
    }

    public Universe getUniverse() {
        return currentGeneration.getUniverse();
    }

    public Generation getGeneration() {
        return currentGeneration;
    }

    public void evolve() {
        currentGeneration = currentGeneration.getNextGeneration();
    }

}
