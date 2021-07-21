package calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String inputLine = scanner.nextLine();
            if (inputLine.equals("/exit")) break;
            if (inputLine.length() == 0) continue;
            int sum = 0;
            for(String n: inputLine.split(" ")) {
                sum += Integer.parseInt(n);
            }
            System.out.println(sum);
        }
        System.out.println("Bye!");
    }
}
