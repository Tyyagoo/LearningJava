package analyzer.sp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class KmpStrategy implements Strategy {
    byte[] str;
    String pattern;
    String type;

    public KmpStrategy(byte[] str, String pattern, String type) {
        this.str = str;
        this.pattern = pattern;
        this.type = type;
    }

    @Override
    public String execute() {
        char[] p = pattern.toCharArray();
        int[] lps = getPrefixFunction(p);
        if (searchPattern(this.str, p, lps)) {
            return type;
        }
        return unknownType;
    }

    public int[] getPrefixFunction(char[] s) {
        int[] p = new int[s.length];
        int j = 0;
        for (int i = 1; i < p.length; i++) {
            while (j > 0 && s[j] != s[i]) {
                j = p[j - 1];
            }

            if (s[j] == s[i]) {
                j++;
            }

            p[i] = j;
        }

        return p;
    }

    public boolean searchPattern(byte[] str, char[] pattern, int[] lps) {
        int i = 0, j = 0;

        while (i < str.length) {
            while (str[i] == pattern[j]) {
                i++;
                j++;
                if (j == pattern.length) {
                    return true;
                }
            }
            // if str[i] != pattern[j], reset j
            if (j > 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }
        return false;
    }

}
