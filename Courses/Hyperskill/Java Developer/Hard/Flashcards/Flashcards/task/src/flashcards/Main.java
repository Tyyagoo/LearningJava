package flashcards;

import flashcards.ui.Menu;

public class Main {
    public static void main(String[] args) {
        CommandLineWrapper.initialize(args);
        CommandLineWrapper.onStart();
        while (Menu.isRunning()) {
            Menu.invoke();
        }
    }
}
