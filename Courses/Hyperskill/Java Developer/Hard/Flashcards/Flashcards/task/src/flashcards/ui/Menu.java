package flashcards.ui;

import flashcards.cards.Card;
import flashcards.cards.CardFactory;
import flashcards.cards.CardManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Menu {

    public static final Scanner scanner = new Scanner(System.in);
    private static boolean running = true;

    private static final ICommand addCommand = sc -> {
        Card card = CardFactory.createCardFromConsole(sc);
        if (card != null) {
            CardManager.storeCard(card);
            System.out.printf("The pair %s has been added%n", card);
        }
    };

    private static final ICommand removeCommand = sc -> {
        System.out.println("Which card?");
        String term = scanner.nextLine();

        if (CardManager.containsTerm(term)) {
            CardManager.removeCardByTerm(term);
            System.out.println("The card has been removed.");
        } else {
            System.out.printf("Can't remove \"%s\": there is no such card.%n", term);
        }
    };

    private static final ICommand importCommand = sc -> {
        System.out.println("File name:");
        String fileName = scanner.nextLine();
        File file = new File(fileName);
        try (Scanner fileReader = new Scanner(file)) {
            int loadedCards = 0;
            while (fileReader.hasNextLine()) {
                String cardInfo = fileReader.nextLine().replace("(", "").replace(")", "").replace("\"", "");
                CardManager.storeCard(CardFactory.createCardFromString(cardInfo));
                loadedCards++;
            }
            System.out.printf("%d cards have been loaded.%n", loadedCards);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    };

    private static final ICommand exportCommand = sc -> {
        System.out.println("File name:");
        String fileName = scanner.nextLine();

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            for (var entrySet: CardManager.getAllCards().entrySet()) {
                String content = String.format("%s%n", entrySet.getValue().toString());
                fileWriter.write(content);
            }
            System.out.printf("%d cards have been saved.%n", CardManager.getAllCards().size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    private static final ICommand askCommand = sc -> {
        System.out.println("How many times to ask?");
        int times = scanner.nextInt();
        scanner.nextLine();

        Random randomizer = new Random();
        List<String> askKeys = new ArrayList<>(CardManager.getAllCards().keySet());

        for (int c = 0; c < times; c++) {
            int index = randomizer.nextInt(askKeys.size());
            String term = askKeys.get(index);
            Card card = CardManager.getCardByTerm(term);
            System.out.printf("Print the definition of \"%s\":%n", term);
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

            askKeys.remove(index);
        }
    };

    private static final ICommand exitCommand = sc -> {
        running = false;
        System.out.println("Bye bye!");
    };

    public static void invoke() {
        System.out.println("Input the action (add, remove, import, export, ask, exit): ");
        String option = scanner.nextLine();

        switch (option) {
            case "add":
                addCommand.execute(scanner);
                break;
            case "remove":
                removeCommand.execute(scanner);
                break;
            case "import":
                importCommand.execute(scanner);
                break;
            case "export":
                exportCommand.execute(scanner);
                break;
            case "ask":
                askCommand.execute(scanner);
                break;
            case "exit":
                exitCommand.execute(scanner);
                break;
        }
    }

    public static boolean isRunning() {
        return running;
    }
}
