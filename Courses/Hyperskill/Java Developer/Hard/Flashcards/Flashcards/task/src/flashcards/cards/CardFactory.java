package flashcards.cards;

import java.util.Scanner;

public class CardFactory {

    public static Card createCardFromConsole(Scanner scanner) {
        String term, definition;
        System.out.printf("Card #%d:%n", CardManager.getCurrentId() + 1);
        while (true) {
            term = scanner.nextLine();
            if (CardManager.containsTerm(term)) {
                System.out.printf("The term \"%s\" already exists. Try again:%n", term);
                continue;
            }
            break;
        }

        System.out.printf("The definition for card #%d:%n", CardManager.getCurrentId() + 1);
        while (true) {
            definition = scanner.nextLine();
            if (CardManager.containsDefinition(definition)) {
                System.out.printf("The definition \"%s\" already exists. Try again:%n", definition);
                continue;
            }
            break;
        }

        return new Card(term, definition);
    }
}
