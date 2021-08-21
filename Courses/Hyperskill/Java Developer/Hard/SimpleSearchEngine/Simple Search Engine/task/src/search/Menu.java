package search;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Menu {
    private static boolean running = true;
    private static List<String> data = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    private static final Command exit = () -> {
        running = false;
        System.out.println("Bye!");
    };

    private static final Command searchInfo = () -> {
        System.out.println("Enter a name or email to search all suitable people.");
        String pattern = scanner.nextLine().toLowerCase();
        Searcher.indexMapSearch.find(data, pattern);
        System.out.println();
    };

    private static final Command printAllData = () -> {
        System.out.println("=== List of people ===");
        data.forEach(System.out::println);
        System.out.println();
    };

    public static void initialize(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            data = reader.lines().collect(Collectors.toList());
        }
        Searcher.buildInvertedIndexMap(data);
    }

    public static void invoke() {
        System.out.println("=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
        int option = Integer.parseInt(scanner.nextLine());
        System.out.println();

        switch (option) {
            case 1:
                searchInfo.execute();
                break;
            case 2:
                printAllData.execute();
                break;
            case 0:
                exit.execute();
                break;
            default:
                System.out.println("Incorrect option! Try again.\n");
                break;
        }
    }

    public static boolean isRunning() {
        return running;
    }
}
