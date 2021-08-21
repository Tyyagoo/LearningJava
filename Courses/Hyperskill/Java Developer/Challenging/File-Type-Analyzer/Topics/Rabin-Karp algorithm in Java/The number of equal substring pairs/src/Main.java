import java.util.*;

public class Main {

    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        String str = scanner.nextLine();
        int t = scanner.nextInt();
        List<Pair<Pair<Integer>>> pairs = new ArrayList<>();
        for (int c = 0; c < t; c++) {
            Pair<Integer> pair1 = new Pair<>(scanner.nextInt(), scanner.nextInt());
            Pair<Integer> pair2 = new Pair<>(scanner.nextInt(), scanner.nextInt());
            pairs.add(new Pair<>(pair1, pair2));
        }

        Hasher hasher = new Hasher(str);
        hasher.calculate();
        int count = 0;
        for (var pair: pairs) {
            Pair<Integer> leftPair;
            Pair<Integer> rightPair;

            if (pair.getLeft().getLeft() > pair.getRight().getLeft()) {
                leftPair = pair.getRight();
                rightPair = pair.getLeft();
            } else {
                leftPair = pair.getLeft();
                rightPair = pair.getRight();
            }

            if (hasher.compareSubstrings(leftPair.getLeft(), leftPair.getRight(),
                    rightPair.getLeft(), rightPair.getRight())) {
                count++;
            }
        }
        System.out.println(count);
    }
}


class Pair<T> {
    final T left;
    final T right;

    Pair (T left, T right) {
        this.left = left;
        this.right = right;
    }

    public T getLeft() {
        return left;
    }

    public T getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?> pair = (Pair<?>) o;

        if (!Objects.equals(left, pair.left)) return false;
        return Objects.equals(right, pair.right);
    }

    @Override
    public int hashCode() {
        int result = left != null ? left.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        return result;
    }
}


class Hasher {
    private String string;
    private long[] prefixes;
    private long[] powers;

    private final int A = 53;
    private final long M = (long) Math.pow(2, 31) - 1; // 1_000_000_000 + 9;

    public Hasher(String string) {
        this.string = string;
        prefixes = new long[string.length()];
        powers = new long[string.length()];
    }

    public void calculate() {
        long hash = 0;
        long power = 1;

        for (int i = 0; i < string.length(); i++) {
            hash += charToLong(string.charAt(i)) * power;
            hash %= M;

            prefixes[i] = hash;
            powers[i] = power;

            power = power * A % M;
        }
    }

    private long hashSubstring(int i, int j) {
        if (i == 0) return prefixes[j - 1]; // just return the prefix hash of 0~j

        long leftHash = prefixes[i - 1];
        long rightHash = prefixes[j - 1];

        return (rightHash - leftHash + M) % M;
    }

    public boolean compareSubstrings(int i, int j, int k, int n) {
        return hashSubstring(i, j) * powers[k - i] % M == hashSubstring(k, n);
    }

    private long charToLong(char ch) {
        return ch - 'A' + 1;
    }
}
