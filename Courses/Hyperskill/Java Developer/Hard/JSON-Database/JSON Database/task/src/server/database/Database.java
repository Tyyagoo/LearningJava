package server.database;

import exceptions.InvalidDatabaseAccessException;

import java.util.*;

public class Database {
    private Map<String, String> db;

    public Database() {
        this.db =  new HashMap<>();
    }

    public void set(String key, String content) {
        db.put(key, content);
    }

    public String get(String key) {
        if (!db.containsKey(key)) throw new InvalidDatabaseAccessException();
        return db.get(key);
    }
    
    public void delete(String key) {
        if (!db.containsKey(key)) throw new InvalidDatabaseAccessException();
        db.remove(key);
    }
}
