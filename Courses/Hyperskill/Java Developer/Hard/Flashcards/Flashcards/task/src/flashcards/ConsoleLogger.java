package flashcards;

import java.io.*;
import java.util.*;

public class ConsoleLogger {

    private static final List<String> log = new LinkedList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void println(String str) {
        System.out.println(str);
        log.add(str);
    }

    public static void printf(String str, Object ... args) {
        String formatted = String.format(str, args);
        System.out.print(formatted);
        log.add(formatted);
    }

    public static String getNextLine() {
        String line = scanner.nextLine();
        log.add(String.format("> %s", line));
        return line;
    }

    public static int getNextInt() {
        int n = scanner.nextInt();
        scanner.nextLine();
        log.add(String.format("> %d", n));
        return n;
    }

    public static List<String> getLog() {
        return log;
    }
}
