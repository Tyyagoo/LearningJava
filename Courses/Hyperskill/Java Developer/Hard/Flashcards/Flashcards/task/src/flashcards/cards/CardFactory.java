package flashcards.cards;

import java.util.Scanner;

public class CardFactory {

    public static Card createCardFromConsole(Scanner scanner) {
        String term = scanner.nextLine();
        String definition = scanner.nextLine();
        return new Card(term, definition);
    }

    public static Card createCardFromConsole(Scanner scanner, int cardId) {
        System.out.printf("Card #%d:%n", cardId);
        String term = scanner.nextLine();
        System.out.printf("The definition for card #%d:%n", cardId);
        String definition = scanner.nextLine();
        return new Card(term, definition);
    }
}
