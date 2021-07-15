package life.viewer;

import life.model.Game;
import life.model.Generation;
import life.model.Universe;

import java.io.IOException;

public class ConsoleViewer {

    public static void showMap(Game game) {
        Generation gen = game.getGeneration();
        showMap(gen);
    }

    public static void showMap(Generation gen) {
        int len = gen.getSize();
        System.out.printf("Generation #%d%n", gen.getId());
        System.out.printf("Alive: %d%n", gen.getPopulation());

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                System.out.printf("%s", (gen.getUniverse().getCellAt(i, j) == Universe.Cell.ALIVE) ? "O" : " ");
            }
            System.out.println();
        }
    }

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        }
        catch (IOException | InterruptedException e) {
            System.out.println("Error on trying to clear screen.");
        }
    }
}
