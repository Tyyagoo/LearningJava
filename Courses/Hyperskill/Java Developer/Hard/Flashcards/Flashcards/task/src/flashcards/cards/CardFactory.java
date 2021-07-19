package flashcards.cards;

import java.util.Scanner;

public class CardFactory {

    public static Card createCardFromConsole(Scanner scanner) {
        String term = scanner.nextLine();
        String definition = scanner.nextLine();
        return new Card(term, definition);
    }
}
