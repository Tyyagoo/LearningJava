package banking;

import banking.system.Bank;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = "";
        if (args.length == 2) fileName = args[1];

        try {
            Bank.initialize(scanner, fileName);
        } catch (Exception e) {
            return;
        }

        Bank.process();
    }
}