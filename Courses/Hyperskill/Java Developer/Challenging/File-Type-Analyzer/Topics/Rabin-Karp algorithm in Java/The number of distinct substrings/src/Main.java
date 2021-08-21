import java.util.*;

public class Main {
    public static void main(String[] args) {
        String text = new Scanner(System.in).nextLine();
        long result = ((long) text.length() * (text.length() + 1)) / 2 + 1;    //all patterns + ""
        long[] hashes = new long[text.length()];

        for (int len = 1; len < text.length(); len++) {                 //length of patterns
            for (int pos = 0; pos <= text.length() - len; pos++) {      //position of patterns
                hashes[pos] = (hashes[pos]  * 53 + (long) text.charAt(pos + len - 1) - 64) % 1_000_000_009;
                for (int prev = 0; prev < pos; prev++) {                //search doubles in prev positions
                    if (hashes[pos] == hashes[prev]) {
                        result--;
                        break;
                    }
                }
            }
        }
        System.out.println(result);
    }
}