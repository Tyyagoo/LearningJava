package server.commands.list;

import exceptions.InvalidDatabaseAccessException;
import server.commands.ICommand;
import server.database.Database;

public class GetCommand implements ICommand {
    private final String key;
    private final Database database;
    private String result = "unknown";

    public GetCommand(Database db, String key) {
        this.database = db;
        this.key = key;
    }

    @Override
    public void execute() {
        try {
            result = String.format("{\"response\":\"OK\", \"value\": %s}", database.get(key));
        } catch (InvalidDatabaseAccessException e) {
            result = "{ \"response\": \"ERROR\", \"reason\": \"No such key\" }";
        }
    }

    @Override
    public String getResult() {
        return result;
    }
}

