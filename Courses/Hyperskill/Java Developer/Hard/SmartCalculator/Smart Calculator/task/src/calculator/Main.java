package calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String inputLine = scanner.nextLine();
            if (inputLine.length() == 0) continue;

            if (inputLine.equals("/exit")) {
                System.out.println("Bye!");
                break;
            }

            if (inputLine.equals("/help")) {
                System.out.println("The program calculates the sum of numbers");
                continue;
            }

            int sum = 0;
            for(String n: inputLine.split(" ")) {
                sum += Integer.parseInt(n);
            }
            System.out.println(sum);
        }
    }
}
