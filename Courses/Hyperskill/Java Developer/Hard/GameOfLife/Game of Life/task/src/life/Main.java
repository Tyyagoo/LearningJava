package life;

import life.controller.ConsoleController;

public class Main {
    public static void main(String[] args) {
        try {
            ConsoleController.initialize();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
