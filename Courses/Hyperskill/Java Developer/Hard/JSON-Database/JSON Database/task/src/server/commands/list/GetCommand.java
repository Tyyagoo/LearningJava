package server.commands.list;

import server.commands.ICommand;
import server.database.Database;

public class GetCommand implements ICommand {
    private final int position;
    private final Database database;
    private String result = "unknown";

    public GetCommand(Database db, int pos) {
        this.database = db;
        this.position = pos;
    }

    @Override
    public void execute() {
        result = database.get(position);
    }

    @Override
    public String getResult() {
        return result;
    }
}

