package life.controller;

import life.model.Game;
import life.viewer.ConsoleViewer;

import java.util.Scanner;

public class ConsoleController {

    public static final Scanner scanner = new Scanner(System.in);
    private static Game gameInstance;


    public static void initialize() throws InterruptedException {
        int n = scanner.nextInt();
        gameInstance = new Game(n);
        ConsoleViewer.clearConsole();
        ConsoleViewer.showMap(gameInstance);
        Thread.sleep(1000);

        for (int i = 0; i < 10; i++) {
            ConsoleViewer.clearConsole();
            gameInstance.evolve();
            ConsoleViewer.showMap(gameInstance);
            Thread.sleep(1000);
        }
    }

}
