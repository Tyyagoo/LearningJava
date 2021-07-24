package server.console;

import server.database.Database;

public class UserInterface {

    private static Database database = null;
    private static boolean running = true;

    private static Command exitCommand = args -> running = false;

    private static Command setCommand = args -> {
        int position = Integer.parseInt(args[0]);
        database.set(position, args[1]);
        Console.println("OK");
    };

    private static Command getCommand = args -> {
        int position = Integer.parseInt(args[0]);
        String output = database.get(position);
        Console.println(output);
    };

    private static Command deleteCommand = args -> {
        int position = Integer.parseInt(args[0]);
        database.delete(position);
        Console.println("OK");
    };

    public static void invoke() {
        String input = Console.getLine();
        String[] args = input.split(" ");
        switch(args[0]) {
            case "get":
                getCommand.execute(args[1]);
                break;
            case "set":
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    stringBuilder.append(args[i]);
                    stringBuilder.append(" ");
                }
                setCommand.execute(args[1], stringBuilder.toString());
                break;
            case "delete":
                deleteCommand.execute(args[1]);
                break;
            default:
                exitCommand.execute();
                break;
        }
    }

    public static boolean isRunning() {
        return running;
    }

    public static void setDatabase(Database db) {
        database = db;
    }
}
