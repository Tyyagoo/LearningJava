package flashcards.ui;

import flashcards.CommandLineWrapper;
import flashcards.ConsoleLogger;
import flashcards.cards.Card;
import flashcards.cards.CardFactory;
import flashcards.cards.CardManager;

import java.io.*;
import java.util.*;


public class Menu {

    private static boolean running = true;

    private static final ICommand addCommand = (ignore) -> {
        Card card = CardFactory.createCardFromConsole();
        if (card != null) {
            CardManager.storeCard(card);
            ConsoleLogger.printf("The pair %s has been added%n", card);
        }
    };

    private static final ICommand removeCommand = (ignore) -> {
        ConsoleLogger.println("Which card?");
        String term = ConsoleLogger.getNextLine();

        if (CardManager.containsTerm(term)) {
            CardManager.removeCardByTerm(term);
            ConsoleLogger.println("The card has been removed.");
        } else {
            ConsoleLogger.printf("Can't remove \"%s\": there is no such card.%n", term);
        }
    };

    private static final ICommand importCommand = (consoleFileName) -> {
        String fileName;

        if (consoleFileName.length > 0) {
            fileName = consoleFileName[0];
        } else {
            ConsoleLogger.println("File name:");
            fileName = ConsoleLogger.getNextLine();
        }

        File file = new File(fileName);
        try (Scanner fileReader = new Scanner(file)) {
            int loadedCards = 0;
            while (fileReader.hasNextLine()) {
                String cardInfo = fileReader.nextLine().replace("(", "").replace(")", "").replace("\"", "");
                CardManager.storeCard(CardFactory.createCardFromString(cardInfo));
                loadedCards++;
            }
            ConsoleLogger.printf("%d cards have been loaded.%n", loadedCards);
        } catch (FileNotFoundException e) {
            ConsoleLogger.println("File not found.");
        }
    };

    private static final ICommand exportCommand = (consoleFileName) -> {
        String fileName;

        if (consoleFileName.length > 0) {
            fileName = consoleFileName[0];
        } else {
            ConsoleLogger.println("File name:");
            fileName = ConsoleLogger.getNextLine();
        }

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            for (var entrySet: CardManager.getAllCards().entrySet()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(entrySet.getValue().toString());
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                stringBuilder.append(":");
                stringBuilder.append(entrySet.getValue().getMistakeCount());
                stringBuilder.append(")");
                String content = String.format("%s%n",stringBuilder);
                fileWriter.write(content);
            }
            ConsoleLogger.printf("%d cards have been saved.%n", CardManager.getAllCards().size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    private static final ICommand askCommand = (ignore) -> {
        ConsoleLogger.println("How many times to ask?");
        int times = ConsoleLogger.getNextInt();

        Random randomizer = new Random();
        List<String> askKeys = new ArrayList<>(CardManager.getAllCards().keySet());

        for (int c = 0; c < times; c++) {
            int index = randomizer.nextInt(askKeys.size());
            String term = askKeys.get(index);
            Card card = CardManager.getCardByTerm(term);
            ConsoleLogger.printf("Print the definition of \"%s\":%n", term);
            String answer = ConsoleLogger.getNextLine();
            if (answer.equals(card.getDefinition())) {
                ConsoleLogger.println("Correct!");
            } else {
                card.incMistakeCount();
                ConsoleLogger.printf("Wrong. The right answer is \"%s\"", card.getDefinition());
                if (CardManager.containsDefinition(answer)) {
                    String correctTerm = CardManager.getCardByDefinition(answer).getTerm();
                    ConsoleLogger.printf(", but your definition is correct for \"%s\"", correctTerm);
                }
                ConsoleLogger.println(".");
            }

            askKeys.remove(index);
        }
    };

    private static final ICommand exitCommand = (ignore) -> {
        running = false;
        ConsoleLogger.println("Bye bye!");
        CommandLineWrapper.onExit();
    };

    private static final ICommand logCommand = (ignore) -> {
        ConsoleLogger.println("File name:");
        String fileName = ConsoleLogger.getNextLine();
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            ConsoleLogger.getLog().forEach(line -> {
                try {
                    fileWriter.write(String.format("%s%n", line));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConsoleLogger.println("The log has been saved.");
    };

    private static final ICommand hardestCardCommand = (ignore) -> {
        int biggestNumberOfMistakes = 0;
        List<Card> cardList = new ArrayList<>();

        for (var entrySet: CardManager.getAllCards().entrySet()) {
            Card card = entrySet.getValue();

            if (card.getMistakeCount() > biggestNumberOfMistakes) {
                cardList.clear();
                cardList.add(card);
                biggestNumberOfMistakes = card.getMistakeCount();
                continue;
            }

            if (card.getMistakeCount() == biggestNumberOfMistakes) {
                cardList.add(card);
            }
        }

        if (biggestNumberOfMistakes == 0) {
            ConsoleLogger.println("There are no cards with errors.");
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            if (cardList.size() == 1) {
                Card card = cardList.get(0);
                stringBuilder.append(String.format("The hardest card is \"%s\". You have %d errors answering it.",
                        card.getTerm(), card.getMistakeCount()));
            } else {
                stringBuilder.append("The hardest cards are");

                for (Card card: cardList) {
                    stringBuilder.append(String.format(" \"%s\",", card.getTerm()));
                }
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                stringBuilder.append(". ");
                stringBuilder.append(String.format("You have %d errors answering them.", biggestNumberOfMistakes));
            }

            ConsoleLogger.println(stringBuilder.toString());
        }
    };

    private static final ICommand resetStatsCommand = (ignore) -> {
        CardManager.getAllCards().forEach((term, card) -> card.resetMistakeCount());
        ConsoleLogger.println("Card statistics have been reset.");
    };

    public static void invoke() {
        ConsoleLogger.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
        String option = ConsoleLogger.getNextLine();

        switch (option) {
            case "add":
                addCommand.execute();
                break;
            case "remove":
                removeCommand.execute();
                break;
            case "import":
                importCommand.execute();
                break;
            case "export":
                exportCommand.execute();
                break;
            case "ask":
                askCommand.execute();
                break;
            case "log":
                logCommand.execute();
                break;
            case "hardest card":
                hardestCardCommand.execute();
                break;
            case "reset stats":
                resetStatsCommand.execute();
                break;
            case "exit":
                exitCommand.execute();
                break;
        }
    }

    public static void forceImport(String fileName) {
        importCommand.execute(fileName);
    }

    public static void forceExport(String fileName) {
        exportCommand.execute(fileName);
    }

    public static boolean isRunning() {
        return running;
    }
}
