package flashcards;

import flashcards.cards.Card;
import flashcards.cards.CardFactory;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input the number of cards:");
        int numberOfCards = Integer.parseInt(scanner.nextLine());
        Card[] cards = new Card[numberOfCards];
        for (int i = 0; i < numberOfCards; i++) {
            cards[i] = CardFactory.createCardFromConsole(scanner, i + 1);
        }

        for (int i = 0; i < numberOfCards; i++) {
            System.out.printf("Print the definition of \"%s\":%n", cards[i].getTerm());
            String answer = scanner.nextLine();
            String output = cards[i].getDefinition().equals(answer) ? "Correct!%n" :
                    "Wrong. The right answer is \"%s\".%n";
            System.out.printf(output, cards[i].getDefinition());
        }
    }
}
