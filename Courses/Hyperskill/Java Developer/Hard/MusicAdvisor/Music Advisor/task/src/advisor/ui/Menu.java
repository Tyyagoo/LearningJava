package advisor.ui;

import advisor.api.Spotify;
import advisor.exceptions.UnauthorizedException;
import advisor.exceptions.UnknownCommandException;
import advisor.utils.Console;
import com.google.gson.*;

import java.util.Map;
import java.util.HashMap;

public class Menu {
    private static boolean running = true;
    private static Gson gson = new Gson();
    private static Spotify spotifyApi;
    private static SubMenu subMenu;

    private static final ICommand nextCommand = (args) -> {
        if (subMenu != null) {
            subMenu.nextPage();
            return;
        }
        Console.println("No more pages.");
    };

    private static final ICommand previousCommand = (args) -> {
        if (subMenu != null) {
            subMenu.previousPage();
            return;
        }
        Console.println("No more pages.");
    };

    private static final ICommand newCommand = (args) -> {
        JsonObject response = spotifyApi.GET("new-releases");
        if (response.has("error")) {
            Console.println(response.get("error").getAsJsonObject().get("message").getAsString());
            return;
        }
        JsonArray items = response.get("albums").getAsJsonObject().get("items").getAsJsonArray();
        subMenu = SubMenu.getNewsPage(items);
        subMenu.drawPage();
    };

    private static final ICommand featuredCommand = (args) -> {
        JsonObject response = spotifyApi.GET("featured-playlists");
        if (response.has("error")) {
            Console.println(response.get("error").getAsJsonObject().get("message").getAsString());
            return;
        }
        JsonArray items = response.get("playlists").getAsJsonObject().get("items").getAsJsonArray();
        subMenu = SubMenu.getFeaturedPage(items);
        subMenu.drawPage();
    };

    private static final ICommand categoriesCommand = (args) -> {
        JsonObject response = spotifyApi.GET("categories");
        if (response.has("error")) {
            Console.println(response.get("error").getAsJsonObject().get("message").getAsString());
            return;
        }
        JsonArray items = response.get("categories").getAsJsonObject().get("items").getAsJsonArray();
        subMenu = SubMenu.getCategoriesPage(items);
        subMenu.drawPage();
    };

    private static final ICommand playlistsCommand = (args) -> {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            stringBuilder.append((String) args[i]);
            stringBuilder.append(" ");
        }
        String categoryName = stringBuilder.toString().trim();
        JsonObject response = spotifyApi.GET("categories");
        if (response.has("error")) {
            Console.println(response.get("error").getAsJsonObject().get("message").getAsString());
            return;
        }
        JsonArray categories = response.get("categories").getAsJsonObject().get("items").getAsJsonArray();
        String categoryID = "null";
        for (JsonElement i: categories) {
            JsonObject category = i.getAsJsonObject();
            if (categoryName.equals(category.get("name").getAsString())) {
                categoryID = category.get("id").getAsString();
            }
        }

        response = spotifyApi.GET(String.format("categories/%s/playlists", categoryID));
        if (response.has("error")) {
            Console.println(response.get("error").getAsJsonObject().get("message").getAsString());
            return;
        }
        JsonArray playlists = response.get("playlists").getAsJsonObject().get("items").getAsJsonArray();
        subMenu = SubMenu.getPlaylistsPage(playlists);
        subMenu.drawPage();
    };

    private static final ICommand authCommand = (args) -> {
        spotifyApi.auth();
        if (spotifyApi.isAuthenticated()) Console.println("Success!");
    };

    private static final ICommand exitCommand = (args) -> {
        Console.println("---GOODBYE!---");
        running = false;
    };

    private static final Map<String, ICommand> commandMap = new HashMap<>();

    public static void initialize(Spotify api) {
        spotifyApi = api;
        commandMap.put("new", newCommand);
        commandMap.put("featured", featuredCommand);
        commandMap.put("categories", categoriesCommand);
        commandMap.put("playlists", playlistsCommand);
        commandMap.put("auth", authCommand);
        commandMap.put("exit", exitCommand);
        commandMap.put("next", nextCommand);
        commandMap.put("prev", previousCommand);
    }

    public static void invoke() {
        String input = Console.getLine();
        ICommand command;
        String[] tokens = input.split(" ");
        command = getCommandFromName(tokens[0]);
        Object[] args = (tokens.length != 1) ? tokens : new Object[0];
        try {
            command.execute(args);
        } catch (UnauthorizedException e) {
            Console.println(e.getMessage());
        }
    }

    public static ICommand getCommandFromName(String name) throws UnknownCommandException {
        if (commandMap.containsKey(name)) {
            return commandMap.get(name);
        }
        throw new UnknownCommandException();
    }

    public static boolean isRunning() {
        return running;
    }
}
