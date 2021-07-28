package advisor.utils;

import java.util.Scanner;

public class Console {
    public static final Scanner scanner = new Scanner(System.in);

    public static String getLine() {
        return scanner.nextLine();
    }

    public static void print(String str, Object ... args) {
        printf(str, args);
        println();
    }

    public static void printf(String str, Object ... args) {
        System.out.printf(str, args);
    }

    public static void println() {
        System.out.println();
    }

    public static void println(String str) {
        System.out.println(str);
    }
}
