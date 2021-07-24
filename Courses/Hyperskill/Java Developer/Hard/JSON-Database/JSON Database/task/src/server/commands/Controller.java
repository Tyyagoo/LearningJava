package server.commands;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import server.commands.list.*;
import server.database.Database;

import java.util.*;

public class Controller {

    private ICommand command;
    private final Database database;

    public Controller(Database db) {
        this.database = db;
    }

    public String invoke(String json) {
        fetch(json);
        executeCommand();
        return getCommandResult();
    }

    public void fetch(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        HashMap<String, String> map = gson.fromJson(json, type);

        switch (map.getOrDefault("type", "exit")) {
            case "get":
                this.command = new GetCommand(database, map.get("key"));
                break;
            case "delete":
                this.command = new DeleteCommand(database, map.get("key"));
                break;
            case "set":
                this.command = new SetCommand(database, map.get("key"), map.get("value"));
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
