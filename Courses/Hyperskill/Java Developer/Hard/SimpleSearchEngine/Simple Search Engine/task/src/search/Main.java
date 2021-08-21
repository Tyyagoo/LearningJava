package search;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] words = scanner.nextLine().split(" ");
        String pattern = scanner.nextLine();

        int index = 1;
        for (String str: words) {
            if (str.equals(pattern)) {
                System.out.println(index);
                return;
            }
            index++;
        }

        System.out.println("Not found");
    }
}
