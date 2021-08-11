package analyzer.sp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class NaiveStrategy implements Strategy {

    private byte[] str;
    private String pattern;
    private String type;

    public NaiveStrategy(byte[] str, String pattern, String type) {
        this.str = str;
        this.pattern = pattern;
        this.type = type;
    }

    @Override
    public String execute() {
        for (int i = 0; i < str.length; i++) {
            boolean result = searchForPattern(str, pattern, i);
            if (result) {
                return type;
            }
        }
        return unknownType;
    }

    private boolean searchForPattern(byte[] bytes, String pattern, int initialPosition) {
        for (int i = initialPosition; i < initialPosition + pattern.length(); i++) {
            if (bytes[i] != pattern.charAt(i - initialPosition)) return false;
        }
        return true;
    }

}
