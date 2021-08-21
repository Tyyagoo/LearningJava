package search;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Menu.initialize(args[1]);
        while (Menu.isRunning()) {
            Menu.invoke();
        }
    }
}
