package flashcards.cards;

import java.util.Scanner;

public class CardFactory {

    public static Card createCardFromConsole(Scanner scanner) {
        String term, definition;
        System.out.println("The card:");
        term = scanner.nextLine();
        if (CardManager.containsTerm(term)) {
            System.out.printf("The card \"%s\" already exists. %n", term);
            return null;
        }

        System.out.println("The definition of the card.");
        definition = scanner.nextLine();
        if (CardManager.containsDefinition(definition)) {
            System.out.printf("The definition \"%s\" already exists. Try again:%n", definition);
            return null;
        }
        return new Card(term, definition);
    }

    public static Card createCardFromString(String cardInfo) {
        String term = cardInfo.split(":")[0];
        String definition = cardInfo.split(":")[1];
        return new Card(term, definition);
    }
}
