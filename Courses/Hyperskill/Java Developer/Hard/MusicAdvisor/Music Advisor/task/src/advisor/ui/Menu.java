package advisor.ui;

import advisor.exceptions.UnknownCommandException;
import advisor.utils.Console;

import java.util.Map;
import java.util.HashMap;

public class Menu {
    private static boolean running = true;

    private static final ICommand newCommand = (args) -> {
        Console.println("---NEW RELEASES---");
        Console.println("Mountains [Sia, Diplo, Labrinth]\n" +
                "Runaway [Lil Peep]\n" +
                "The Greatest Show [Panic! At The Disco]\n" +
                "All Out Life [Slipknot]");
    };

    private static final ICommand featuredCommand = (args) -> {
        Console.println("---FEATURED---");
        Console.println("Mellow Morning\n" +
                "Wake Up and Smell the Coffee\n" +
                "Monday Motivation\n" +
                "Songs to Sing in the Shower");
    };

    private static final ICommand categoriesCommand = (args) -> {
        Console.println("---CATEGORIES---");
        Console.println("Top Lists\n" +
                "Pop\n" +
                "Mood\n" +
                "Latin");
    };

    private static final ICommand playlistsCommand = (args) -> {
        String playlistName = (String) args[0];
        Console.print("---%s PLAYLISTS---", playlistName.toUpperCase());
        Console.println("Walk Like A Badass  \n" +
                "Rage Beats  \n" +
                "Arab Mood Booster  \n" +
                "Sunday Stroll");
    };

    private static final ICommand exitCommand = (args) -> {
        Console.println("---GOODBYE!---");
        running = false;
    };

    private static final Map<String, ICommand> commandMap = new HashMap<>();


    public static void initialize() {
        commandMap.put("new", newCommand);
        commandMap.put("featured", featuredCommand);
        commandMap.put("categories", categoriesCommand);
        commandMap.put("playlists", playlistsCommand);
        commandMap.put("exit", exitCommand);
    }

    public static void invoke() {
        String input = Console.getLine();
        ICommand command;
        Object[] args;
        if (input.contains(" ")) {
            String[] tokens = input.split(" ");
            command = getCommandFromName(tokens[0]);
            args = new Object[]{tokens[1]};
        } else {
            command = getCommandFromName(input);
            args = null;
        }
        command.execute(args);
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
