package flashcards.ui;

import flashcards.cards.Card;
import flashcards.cards.CardFactory;
import flashcards.cards.CardManager;

import java.util.Scanner;

public class Menu {

    public static final Scanner scanner = new Scanner(System.in);

    public static void initialize() {
        System.out.println("Input the number of cards:");
        int numberOfCards = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numberOfCards; i++) {
            CardManager.storeCard(CardFactory.createCardFromConsole(scanner));
        }

        for (int i = 0; i < numberOfCards; i++) {
            Card card = CardManager.getCardById(i + 1);
            if (card == null) continue;
            System.out.printf("Print the definition of \"%s\":%n", card.getTerm());
            String answer = scanner.nextLine();

            if (answer.equals(card.getDefinition())) {
                System.out.println("Correct!");
            } else {
                System.out.printf("Wrong. The right answer is \"%s\"", card.getDefinition());
                if (CardManager.containsDefinition(answer)) {
                    String correctTerm = CardManager.getCardByDefinition(answer).getTerm();
                    System.out.printf(", but your definition is correct for \"%s\"", correctTerm);
                }
                System.out.println(".");
            }
        }
    }
}
