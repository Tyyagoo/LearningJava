package server.commands.list;

import server.commands.ICommand;
import server.database.Database;

public class SetCommand implements ICommand {
    private final int position;
    private final String text;
    private final Database database;

    public SetCommand(Database db, int pos, String txt) {
        this.database = db;
        this.text = txt;
        this.position = pos;
    }

    @Override
    public void execute() {
        database.set(position, text);
    }

    @Override
    public String getResult() {
        return "OK";
    }
}
