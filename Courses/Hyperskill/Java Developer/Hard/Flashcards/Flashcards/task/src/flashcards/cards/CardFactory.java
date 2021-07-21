package flashcards.cards;

import flashcards.ConsoleLogger;

public class CardFactory {

    public static Card createCardFromConsole() {
        String term, definition;
        ConsoleLogger.println("The card:");
        term = ConsoleLogger.getNextLine();
        if (CardManager.containsTerm(term)) {
            ConsoleLogger.printf("The card \"%s\" already exists. %n", term);
            return null;
        }

        ConsoleLogger.println("The definition of the card.");
        definition = ConsoleLogger.getNextLine();
        if (CardManager.containsDefinition(definition)) {
            ConsoleLogger.printf("The definition \"%s\" already exists. Try again:%n", definition);
            return null;
        }
        return new Card(term, definition, 0);
    }

    public static Card createCardFromString(String cardInfo) {
        String term = cardInfo.split(":")[0];
        String definition = cardInfo.split(":")[1];
        int mistakes = Integer.parseInt(cardInfo.split(":")[2]);
        return new Card(term, definition, mistakes);
    }
}
