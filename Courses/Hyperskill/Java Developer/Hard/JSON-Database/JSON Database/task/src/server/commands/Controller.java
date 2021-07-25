package server.commands;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

    public void fetch(String request) {
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(request, JsonObject.class);
        String key = gson.toJson(json.get("key"));

        switch (json.get("type").getAsString()) {
            case "get":
                this.command = new GetCommand(database, key);
                break;
            case "delete":
                this.command = new DeleteCommand(database, key);
                break;
            case "set":
                String value = gson.toJson(json.get("value"));
                this.command = new SetCommand(database, key, value);
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
