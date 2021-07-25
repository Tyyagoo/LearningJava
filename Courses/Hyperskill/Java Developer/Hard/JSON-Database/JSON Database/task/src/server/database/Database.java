package server.database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import exceptions.InvalidDatabaseAccessException;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Database {
    private static final String filepath = "./src/server/data/";
    private final String filename;
    private Map<String, String> db;

    public Database(String filename) {
        this.filename = filename;
        this.db =  new HashMap<>();
        try {
            loadDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDatabase() throws FileNotFoundException {
        File file = new File(filepath + filename);
        if (file.isFile()) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            db = gson.fromJson(bufferedReader, type);
        }
    }

    public synchronized void set(String key, String content) {
        db.put(key, content);
        updateFile();
    }

    public String get(String key) {
        if (!db.containsKey(key)) throw new InvalidDatabaseAccessException();
        return db.get(key);
    }
    
    public synchronized void delete(String key) {
        if (!db.containsKey(key)) throw new InvalidDatabaseAccessException();
        db.remove(key);
        updateFile();
    }

    private void updateFile() {
        try (FileWriter fileWriter = new FileWriter(filepath + filename)) {
            Gson gson = new Gson();
            String json = gson.toJson(db);
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
