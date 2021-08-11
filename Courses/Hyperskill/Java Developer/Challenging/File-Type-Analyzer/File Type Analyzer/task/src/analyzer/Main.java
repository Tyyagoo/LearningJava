package analyzer;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.exit(0);
        }

        String filename = args[0];
        Map<String, String> pattern = new HashMap<>();
        pattern.put(args[1], args[2]);

        try {
            byte[] allBytes = Files.readAllBytes(Paths.get(filename));

            for (int i = 0; i < allBytes.length; i++) {

                for (var patternEntry: pattern.entrySet()) {
                    boolean result = searchForPattern(allBytes, patternEntry.getKey(), i);
                    if (result) {
                        System.out.println(patternEntry.getValue());
                        System.exit(0);
                    }
                }

            }

            // file pattern hasn't found
            System.out.println("Unknown file type");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean searchForPattern(byte[] bytes, String pattern, int initialPosition) {
        for (int i = initialPosition; i < initialPosition + pattern.length(); i++) {
            if (bytes[i] != pattern.charAt(i - initialPosition)) return false;
        }
        return true;
    }
}
