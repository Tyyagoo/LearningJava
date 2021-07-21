package flashcards;

import flashcards.ui.Menu;

public class Main {
    public static void main(String[] args) {
        while (Menu.isRunning()) {
            Menu.invoke();
        }
    }
}
