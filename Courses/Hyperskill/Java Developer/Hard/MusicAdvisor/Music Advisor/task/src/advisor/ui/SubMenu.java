package advisor.ui;

import advisor.utils.Console;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.LinkedList;

public class SubMenu {
    public static int pageThreshold;
    private LinkedList<String> pages;
    private int position;

    private SubMenu() {
        this.pages = new LinkedList<>();
        this.position = 1;
    }

    private void addPage(String page) {
        pages.add(page);
    }

    public void drawPage() {
        Console.println(pages.get(position - 1));
        Console.print("---PAGE %d OF %d---", position, pages.size());
    }

    public void nextPage() {
        if (position + 1 <= pages.size()) {
            position++;
            drawPage();
            return;
        }
        Console.println("No more pages.");
    }

    public void previousPage() {
        if (position - 1 > 0) {
            position--;
            drawPage();
            return;
        }
        Console.println("No more pages.");
    }

    public static SubMenu getNewsPage(JsonArray items) {
        SubMenu newsMenu = new SubMenu();
        int itemCount = 1;
        StringBuilder pageBuilder = new StringBuilder();
        for (JsonElement n: items) {
            if (itemCount > pageThreshold) {
                itemCount = 1;
                newsMenu.addPage(pageBuilder.toString());
                pageBuilder = new StringBuilder();
            }
            JsonObject newObject = n.getAsJsonObject();
            pageBuilder.append(newObject.get("name").getAsString());
            pageBuilder.append("\n");
            pageBuilder.append("[");
            for (JsonElement artist: newObject.get("artists").getAsJsonArray()) {
                pageBuilder.append(artist.getAsJsonObject().get("name").getAsString());
                pageBuilder.append(", ");
            }
            pageBuilder.deleteCharAt(pageBuilder.length() - 1);
            pageBuilder.deleteCharAt(pageBuilder.length() - 1);
            pageBuilder.append("]\n");
            pageBuilder.append(newObject.get("external_urls").getAsJsonObject().get("spotify").getAsString());
            pageBuilder.append("\n\n");
            itemCount++;
        }
        newsMenu.addPage(pageBuilder.toString());
        return newsMenu;
    }

    public static SubMenu getFeaturedPage(JsonArray items) {
        SubMenu featuredMenu = new SubMenu();
        int itemCount = 1;
        StringBuilder pageBuilder = new StringBuilder();
        for (JsonElement i: items) {
            if (itemCount > pageThreshold) {
                itemCount = 1;
                featuredMenu.addPage(pageBuilder.toString());
                pageBuilder = new StringBuilder();
            }
            JsonObject playlist = i.getAsJsonObject();
            pageBuilder.append(playlist.get("name").getAsString());
            pageBuilder.append("\n");
            pageBuilder.append(playlist.get("external_urls").getAsJsonObject().get("spotify").getAsString());
            pageBuilder.append("\n\n");
            itemCount++;
        }
        featuredMenu.addPage(pageBuilder.toString());
        return featuredMenu;
    }

    public static SubMenu getCategoriesPage(JsonArray items) {
        SubMenu categoriesMenu = new SubMenu();
        int itemCount = 1;
        StringBuilder pageBuilder = new StringBuilder();
        for (JsonElement i: items) {
            if (itemCount > pageThreshold) {
                itemCount = 1;
                categoriesMenu.addPage(pageBuilder.toString());
                pageBuilder = new StringBuilder();
            }
            JsonObject obj = i.getAsJsonObject();
            pageBuilder.append(obj.get("name").getAsString());
            pageBuilder.append("\n");
            itemCount++;
        }
        categoriesMenu.addPage(pageBuilder.toString());
        return categoriesMenu;
    }

    public static SubMenu getPlaylistsPage(JsonArray items) {
        SubMenu playlistsMenu = new SubMenu();
        int itemCount = 1;
        StringBuilder pageBuilder = new StringBuilder();

        for (JsonElement pl: items) {
            if (itemCount > pageThreshold) {
                itemCount = 1;
                playlistsMenu.addPage(pageBuilder.toString());
                pageBuilder = new StringBuilder();
            }
            JsonObject playlist = pl.getAsJsonObject();
            pageBuilder.append(playlist.get("name").getAsString());
            pageBuilder.append("\n");
            pageBuilder.append(playlist.get("external_urls").getAsJsonObject().get("spotify").getAsString());
            pageBuilder.append("\n");
            itemCount++;
        }
        playlistsMenu.addPage(pageBuilder.toString());
        return playlistsMenu;
    }
}
