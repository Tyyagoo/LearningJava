package life.viewer;

import life.model.Game;
import life.model.Generation;
import life.model.Universe;

public class ConsoleViewer {

    public static void showMap(Game game) {
        Generation gen = game.getGeneration();
        showMap(gen);
    }

    public static void showMap(Generation gen) {
        int len = gen.getSize();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                System.out.printf("%s", (gen.getUniverse().getCellAt(i, j) == Universe.Cell.ALIVE) ? "O" : " ");
            }
            System.out.println();
        }
    }
}
