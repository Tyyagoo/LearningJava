package search;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static boolean running = true;
    private static Scanner scanner = new Scanner(System.in);
    private static List<String> data = new ArrayList<>();

    private static final Command exit = () -> {
        running = false;
        System.out.println("Bye!");
    };

    private static final Command searchInfo = () -> {
        System.out.println("Enter a name or email to search all suitable people.");
        String pattern = scanner.nextLine().toLowerCase();
        List<String> result = findDataByPattern(data, pattern);

        if (result.isEmpty()) {
            System.out.println("No matching people found.");
        } else {
            result.forEach(System.out::println);
        }
        System.out.println();
    };

    private static final Command printAllData = () -> {
        System.out.println("=== List of people ===");
        data.forEach(System.out::println);
        System.out.println();
    };

    public static void initialize() {
        System.out.println("Enter the number of people:");
        int numberOfLines = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter all people:");
        for (int i = 0; i < numberOfLines; i++) {
            data.add(scanner.nextLine());
        }

        System.out.println();
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

    public static List<String> findDataByPattern(List<String> data, String pattern) {
        List<String> list = new ArrayList<>();
        for (String line: data) {
            if (line.toLowerCase().contains(pattern)) {
                list.add(line);
            }
        }
        return list;
    }

    public static boolean isRunning() {
        return running;
    }
}
