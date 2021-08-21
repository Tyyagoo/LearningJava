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
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String matchingType = scanner.nextLine().toUpperCase();

        System.out.println("Enter a name or email to search all suitable people.");
        String pattern = scanner.nextLine().toLowerCase();
        Set<String> result = new LinkedHashSet<>();
        switch (matchingType) {
            case "ALL":
                for (String query: pattern.split("\\s+")) {
                    List<String> queryResult = Searcher.indexMapSearch.find(data, query);
                    if (result.isEmpty()) {
                        result.addAll(queryResult);
                    } else {
                        result.retainAll(queryResult);
                    }
                }
                break;
            case "ANY":
                for (String query: pattern.split("\\s+")) {
                    result.addAll(Searcher.indexMapSearch.find(data, query));
                }
                break;
            case "NONE":
                result.addAll(data);
                for (String query: pattern.split("\\s+")) {
                    Searcher.indexMapSearch.find(data, query).forEach(result::remove);
                }
                break;
            default:
                System.out.printf("The matching strategy \"%s\" doesn't exist.", matchingType);
                break;
        }

        if (result.isEmpty()) {
            System.out.println("No matching people found.");
        } else {
            System.out.printf("%d persons found:%n", result.size());
            result.forEach(System.out::println);
        }
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
