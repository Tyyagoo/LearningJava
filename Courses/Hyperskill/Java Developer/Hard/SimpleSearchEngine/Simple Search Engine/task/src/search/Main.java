package search;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of people:");
        int numberOfLines = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter all people:");
        String[] data = new String[numberOfLines];
        for (int i = 0; i < numberOfLines; i++) {
            data[i] = scanner.nextLine();
        }
        System.out.println();
        System.out.println("Enter the number of search queries:");
        int numberOfQueries = Integer.parseInt(scanner.nextLine());
        System.out.println();

        for (int c = 0; c < numberOfQueries; c++) {
            System.out.println("Enter data to search people:");
            String pattern = scanner.nextLine().toLowerCase();
            List<String> list = findDataByPattern(data, pattern);

            if (list.size() == 0) {
                System.out.println("No matching people found.\n");
            } else {
                System.out.println("\nFound people:");
                list.forEach(System.out::println);
                System.out.println();
            }
        }
    }

    public static List<String> findDataByPattern(String[] data, String pattern) {
        List<String> list = new ArrayList<>();
        for (String line: data) {
            if (line.toLowerCase().contains(pattern)) {
                list.add(line);
            }
        }
        return list;
    }
}
