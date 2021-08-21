import java.util.*;

public class Main {
    public static void main(String[] args) {
        // put your code here
        String string = new Scanner(System.in).nextLine();
        System.out.println(bsearch(string));
    }

    static int bsearch(String s) {
        int l = 1;
        int r = s.length() - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (check(s, mid)) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return l - 1;
    }

    static boolean check(String s, int size) {
        /*
        I DON'T KNOW HOW THIS WORKS, BUT WORKS.
        I ONLY COPY-PASTED THIS BECAUSE ROLLING HASH JUST DOESN'T WORKS.
         */
        int length = s.length();
        int capacity = length - size + 1;
        Map<Long, Long> hashMap = new HashMap<>(capacity);

        long hash1 = 0;
        long hash2 = 0;
        long pow1 = 1;
        long pow2 = 1;

        int a1 = 59;
        int a2 = 53;
        long m1 = 1_000_000_000L + 7;
        long m2 = 1_000_000_000L + 9;

        for (int i = 0; i < size; i++) {
            hash1 += s.charAt(capacity - 1 + i) * pow1;
            hash1 %= m1;

            hash2 += s.charAt(capacity - 1 + i) * pow2;
            hash2 %= m2;

            if (i != size - 1) {
                pow1 = pow1 * a1 % m1;
                pow2 = pow2 * a2 % m2;
            }
        }
        hashMap.put(hash1, hash2);

        for (int i = length; i > size; i--) {
            hash1 = (hash1 - s.charAt(i - 1) * pow1 % m1 + m1) * a1 % m1;
            hash1 = (hash1 + s.charAt(i - size - 1)) % m1;

            hash2 = (hash2 - s.charAt(i - 1) * pow2 % m2 + m2) * a2 % m2;
            hash2 = (hash2 + s.charAt(i - size - 1)) % m2;

            Long hash = hashMap.put(hash1, hash2);
            if (hash != null && hash.equals(hash2)) {
                return true;
            }
        }

        return false;
    }
}
