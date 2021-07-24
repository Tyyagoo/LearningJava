package server.console;

import java.util.Scanner;

public class Console {
    private static final Scanner scanner = new Scanner(System.in);

    private Console() {
        throw new UnsupportedOperationException();
    }

    /*
    INPUT
     */
    public static String getLine() {
        return scanner.nextLine();
    }

    /*
    OUTPUT
     */

    public static void println(String text) {
        System.out.println(text);
    }

    public static void printf(String text, Object ... args) {
        System.out.printf(text, args);
    }

    public static void print(String text, Object ... args) {
        printf(text + "%n", args);
    }

    /*
    GETTERS
     */

    public static Scanner getScanner() {
        return scanner;
    }
}
