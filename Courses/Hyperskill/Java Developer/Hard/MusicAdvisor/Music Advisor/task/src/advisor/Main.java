package advisor;

import advisor.api.Spotify;
import advisor.ui.Menu;
import advisor.utils.Console;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Menu.initialize(new Spotify(args[1]));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        while (Menu.isRunning()) {
            Menu.invoke();
        }
    }
}
