package server.commands.list;

import server.commands.ICommand;
import server.database.Database;

public class DeleteCommand implements ICommand {
    private final int position;
    private final Database database;

    public DeleteCommand(Database db, int pos) {
        this.database = db;
        this.position = pos;
    }

    @Override
    public void execute() {
        database.delete(position);
    }

    @Override
    public String getResult() {
        return "OK";
    }
}
