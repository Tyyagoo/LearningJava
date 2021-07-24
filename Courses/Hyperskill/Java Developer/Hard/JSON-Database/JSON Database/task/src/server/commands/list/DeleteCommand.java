package server.commands.list;

import exceptions.InvalidDatabaseAccessException;
import server.commands.ICommand;
import server.database.Database;

public class DeleteCommand implements ICommand {
    private final String key;
    private final Database database;
    private String result = "";

    public DeleteCommand(Database db, String key) {
        this.database = db;
        this.key = key;
    }

    @Override
    public void execute() {
        try {
            database.delete(key);
            result = "{\"response\":\"OK\"}";
        } catch (InvalidDatabaseAccessException e) {
            result = "{ \"response\": \"ERROR\", \"reason\": \"No such key\" }";
        }
    }

    @Override
    public String getResult() {
        return result;
    }
}
