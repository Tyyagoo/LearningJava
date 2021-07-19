package flashcards;

import flashcards.cards.Card;
import flashcards.cards.CardFactory;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Card card = CardFactory.createCardFromConsole(scanner);

        String answer = scanner.nextLine();

        System.out.print("Your answer is ");
        System.out.println(card.getDefinition().equals(answer) ? "right!" : "wrong...");
    }
}
