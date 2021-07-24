package server.commands;

import server.commands.list.*;
import server.database.Database;

public class Controller {

    private ICommand command;
    private final Database database;

    public Controller(Database db) {
        this.database = db;
    }

    public String invoke(String cmd) {
        fetch(cmd);
        executeCommand();
        return getCommandResult();
    }

    public void fetch(String cmd) {
        String[] tokens = cmd.split(" ");
        switch (tokens[0]) {
            case "get":
                this.command = new GetCommand(database, Integer.parseInt(tokens[1]));
                break;
            case "delete":
                this.command = new DeleteCommand(database, Integer.parseInt(tokens[1]));
                break;
            case "set":
                StringBuilder textBuilder = new StringBuilder();
                for (int i = 2; i < tokens.length; i++) {
                    textBuilder.append(tokens[i]);
                    textBuilder.append(" ");
                }
                this.command = new SetCommand(database, Integer.parseInt(tokens[1]), textBuilder.toString());
                break;
            default:
                this.command = new ExitCommand();
        }
    }

    public void executeCommand() {
        command.execute();
    }

    public String getCommandResult() {
        return command.getResult();
    }
}
