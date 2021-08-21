import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        String string = scanner.nextLine();
        int k = scanner.nextInt();

        int[][] ranges = new int[k][2];

        for (int a = 0; a < k; a++) {
            ranges[a][0] = scanner.nextInt();
            ranges[a][1] = scanner.nextInt();
        }

        StringBuilder builder = new StringBuilder();
        for (int j = 1; j <= string.length(); j++) {
            builder.append(calculateSubstringHash(string, 0, j));
            builder.append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        System.out.println(builder);

        builder = new StringBuilder();
        for (int a = 0; a < k; a++) {
            long fHash = calculateSubstringHash(string, 0, ranges[a][1]); // 0 to j
            long lHash = calculateSubstringHash(string, 0, ranges[a][0]); // 0 to i
            long hash = (fHash - lHash) % (1_000_000_000 + 9);
            hash = hash >= 0 ? hash : fHash + 1_000_000_009 - lHash;
            builder.append(hash);
            builder.append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        System.out.println(builder);
    }

    public static long castCharToLong(char c) {
        return c - 'A' + 1;
    }

    public static long calculateSubstringHash(String str, int i, int j) {
        long a = 53;
        long m = 1_000_000_000 + 9;

        long hash = 0;
        long pow = 1;
        for (int c = i; c < j; c++) {
            long ascii = castCharToLong(str.charAt(c));
            hash += ascii * pow;
            pow = pow * a % m;
        }
        return hash % m;
    }
}
