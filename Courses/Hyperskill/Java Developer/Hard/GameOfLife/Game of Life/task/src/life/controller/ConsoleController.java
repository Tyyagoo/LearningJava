package life.controller;

import life.model.Game;
import life.viewer.ConsoleViewer;

import java.util.Scanner;

public class ConsoleController {

    public static final Scanner scanner = new Scanner(System.in);
    private static Game gameInstance;


    public static void initialize() {
        int n = scanner.nextInt();
        int s = scanner.nextInt();
        int numberOfGens = scanner.nextInt();
        gameInstance = new Game(n, s);

        for (int i = 0; i < numberOfGens; i++) {
            gameInstance.evolve();
        }
        ConsoleViewer.showMap(gameInstance);
    }

    public static void makeStep() {
        gameInstance.evolve();
    }

    public static Game getGame() {
        return gameInstance;
    }

}
