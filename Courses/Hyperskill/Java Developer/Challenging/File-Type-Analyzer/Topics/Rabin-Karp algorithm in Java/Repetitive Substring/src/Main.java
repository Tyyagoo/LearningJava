import java.util.*;

public class Main {
    private static final int A = 53;
    private static final long M = (long) Math.pow(2, 31) - 1; // 1_000_000_000 + 9;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        int substringLength = scanner.nextInt();
        List<String> occurrences = new ArrayList<>();

        for (int i = string.length() - substringLength; i >= 0; --i) {
            occurrences = rabinKarp(string, string.substring(i, i + substringLength));

            if (occurrences.size() > 1) {
                break;
            }
        }

        if (occurrences.size() > 1) {
            System.out.println(occurrences.get(0));
        } else {
            System.out.println("does not contain");
        }
    }

    private static List<String> rabinKarp(String text, String pattern) {
        long patternHash = 0;
        long substringHash = 0;
        long pow = 1;

        for (int i = 0; i < pattern.length(); i++) {
            patternHash += charToLong(pattern.charAt(i)) * pow;
            patternHash %= M;

            substringHash += charToLong(text.charAt(text.length() - pattern.length() + i)) * pow;
            substringHash %= M;

            if (i != pattern.length() - 1) {
                pow = pow * A % M;
            }
        }

        List<String> occurrences = new ArrayList<>();

        for (int i = text.length(); i >= pattern.length(); i--) {
            if (patternHash == substringHash) {
                boolean patternIsFound = true;

                for (int j = 0; j < pattern.length(); j++) {
                    if (text.charAt(i - pattern.length() + j) != pattern.charAt(j)) {
                        patternIsFound = false;
                        break;
                    }
                }

                if (patternIsFound) {
                    occurrences.add(text.substring(i - pattern.length(), i));

                    if (occurrences.size() > 1) {
                        return occurrences;
                    }
                }
            }

            if (i > pattern.length()) {
                substringHash = (substringHash - charToLong(text.charAt(i - 1)) * pow % M + M) * A % M;
                substringHash = (substringHash + charToLong(text.charAt(i - pattern.length() - 1))) % M;
            }
        }

        return occurrences;
    }

    private static long charToLong(char ch) {
        return ch - 'A' + 1;
    }
}