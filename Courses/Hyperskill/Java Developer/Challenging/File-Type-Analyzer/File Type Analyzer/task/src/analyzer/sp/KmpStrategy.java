package analyzer.sp;

import analyzer.FilePattern;

public class KmpStrategy implements Strategy {
    byte[] str;
    String pattern;
    String type;

    public KmpStrategy(byte[] str, FilePattern filePattern) {
        this.str = str;
        this.pattern = filePattern.getPattern();
        this.type = filePattern.getType();
    }

    @Override
    public boolean execute() {
        char[] p = pattern.toCharArray();
        int[] lps = getPrefixFunction(p);
        return searchPattern(this.str, p, lps);
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

        if (str.length < pattern.length) {
            return false;
        }

        while (i < str.length) {
            while (str[i] == pattern[j]) {
                i++;
                j++;
                if (j == pattern.length) {
                    return true;
                }

                if (i == str.length) {
                    return false;
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
