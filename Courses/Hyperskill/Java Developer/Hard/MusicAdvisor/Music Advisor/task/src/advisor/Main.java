package advisor;

import advisor.api.Spotify;
import advisor.ui.Menu;
import advisor.ui.SubMenu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, String> commandLineArguments = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            commandLineArguments.put(args[i], args[++i]);
        }
        try {
            SubMenu.pageThreshold = Integer.parseInt(commandLineArguments.getOrDefault("-page", "5"));
            String accessUrl = commandLineArguments.getOrDefault("-access", "https://accounts.spotify.com");
            String apiUrl = commandLineArguments.getOrDefault("-resource", "https://api.spotify.com");
            Menu.initialize(new Spotify(accessUrl, apiUrl));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        while (Menu.isRunning()) {
            Menu.invoke();
        }
        System.exit(0);
    }
}
