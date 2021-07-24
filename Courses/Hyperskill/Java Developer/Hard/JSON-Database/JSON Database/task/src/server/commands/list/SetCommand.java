package server.commands.list;

import exceptions.InvalidDatabaseAccessException;
import server.commands.ICommand;
import server.database.Database;

public class SetCommand implements ICommand {
    private final String key;
    private final String text;
    private final Database database;

    public SetCommand(Database db, String key, String txt) {
        this.database = db;
        this.text = txt;
        this.key = key;
    }

    @Override
    public void execute() {
        database.set(key, text);
    }

    @Override
    public String getResult() {
        return "{\"response\":\"OK\"}";
    }
}
