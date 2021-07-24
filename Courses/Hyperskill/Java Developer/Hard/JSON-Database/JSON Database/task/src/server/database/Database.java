package server.database;

import exceptions.InvalidDatabaseAccessException;

import java.util.Arrays;

public class Database {
    private String[] db;

    public Database() {
        this.db =  new String[1000];
        Arrays.fill(db, "");
    }

    public void set(int position, String content) {
        checkPosition(position);
        db[position - 1] = content;
    }

    public String get(int position) {
        checkPosition(position);
        if ("".equals(db[position - 1])) throw new InvalidDatabaseAccessException();
        return db[position - 1];
    }
    
    public void delete(int position) {
        checkPosition(position);
        db[position - 1] = "";
    }

    private void checkPosition(int position) {
        if (position < 1 || position > 100) throw new InvalidDatabaseAccessException();
    }

}
