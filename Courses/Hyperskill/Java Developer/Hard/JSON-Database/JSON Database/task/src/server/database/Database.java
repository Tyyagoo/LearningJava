package server.database;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import exceptions.InvalidDatabaseAccessException;

import java.io.*;
import java.util.*;
import java.util.concurrent.locks.*;

public class Database {
    private static final String FILEPATH_TEST_ENVIRONMENT = System.getProperty("user.dir") + "/src/server/data/";
    private static final String FILEPATH_LOCAL_ENVIRONMENT = System.getProperty("user.dir") + "/JSON Database/task/src/server/data/";
    private static final String filepath = FILEPATH_TEST_ENVIRONMENT;
    private final String filename;

    private JsonObject db;
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(true);
    private final ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
    private final Gson gson = new Gson();

    public Database(String filename) {
        this.filename = filename;
        try {
            initializeDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeDatabase() {
        writeLock.lock();
        File file = new File(filepath + filename);
        if (!file.isFile()) {
            try {
                createDatabase(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            db = loadDatabase(file).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    private void createDatabase(File file) throws IOException {
        file.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            String emptyJson = "{}";
            writer.write(emptyJson);
        }
    }

    private JsonElement loadDatabase(File from) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(from))) {
            return JsonParser.parseReader(reader);
        }
    }

    public void set(String key, String object) {
        writeLock.lock();
        try {
            JsonElement jsonKey = JsonParser.parseString(key);
            JsonElement value = JsonParser.parseString(object);
            if (jsonKey.isJsonPrimitive()) { // ex: set "people"
                set(jsonKey.getAsString(), value);
            } else if (jsonKey.isJsonArray()) { // ex: set "age" of "people"
                set(jsonKey.getAsJsonArray(), value);
            }
            updateFile();
        } catch (IOException ignored) {
        } finally {
            writeLock.unlock();
        }
    }

    private void set(String key, JsonElement value) {
        db.add(key, value);
    }

    private void set(JsonArray keys, JsonElement value) {
        JsonElement currentJson = db;

        for (int i = 0; i < keys.size(); i++) {
            if (currentJson.isJsonObject()) {
                JsonElement currentValue = currentJson.getAsJsonObject().get(keys.get(i).getAsString());
                if (currentValue.isJsonObject()) {
                    currentJson = currentValue;
                    continue;
                }
                currentJson.getAsJsonObject().remove(keys.get(i).getAsString());
                currentJson.getAsJsonObject().add(keys.get(i).getAsString(), value);
            }
        }
    }

    public String get(String key) {
        readLock.lock();
        try {
            JsonElement jsonKey = JsonParser.parseString(key);
            if (jsonKey.isJsonArray()) {
                JsonElement value = get(jsonKey.getAsJsonArray());
                if (value != null) {
                    Gson gson = new Gson();
                    return gson.toJson(value);
                }
            }
        } finally {
            readLock.unlock();
        }
        throw new InvalidDatabaseAccessException();
    }

    private JsonElement get(JsonPrimitive key) {
        return db.get(key.getAsString());
    }

    private JsonElement get(JsonArray keys) {
        JsonElement currentJson = db;
        for (JsonElement key: keys) {
            JsonElement currentValue = currentJson.getAsJsonObject().get(key.getAsString());
            if (currentValue.isJsonObject()) {
                currentJson = currentValue.getAsJsonObject();
                continue;
            }
            if (currentValue.isJsonPrimitive()) {
                currentJson = currentValue;
            }
        }
        return currentJson;
    }

    public void delete(String key) {
        writeLock.lock();
        try {
            JsonElement jsonKey = JsonParser.parseString(key);
            if (jsonKey.isJsonPrimitive()) { // ex: get "people"
                JsonElement value = delete(jsonKey.getAsJsonPrimitive());
                if (value != null) return;
            } else if (jsonKey.isJsonArray()) { // ex: get "age" of "people"
                JsonElement value = delete(jsonKey.getAsJsonArray());
                if (value != null) return;
            }
            updateFile();
        } catch (IOException ignored) {
        } finally {
            writeLock.unlock();
        }
        throw new InvalidDatabaseAccessException();
    }

    private JsonElement delete(JsonPrimitive key) {
        return db.remove(key.getAsString());
    }

    private JsonElement delete(JsonArray keys) {
        JsonElement currentJson = db;
        JsonElement removedValue = null;
        for (JsonElement key: keys) {
            JsonElement currentValue = currentJson.getAsJsonObject().get(key.getAsString());
            if (currentValue.isJsonObject()) {
                currentJson = currentValue.getAsJsonObject();
                continue;
            }
            if (currentValue.isJsonPrimitive() || currentValue.isJsonArray() || currentValue.isJsonNull()) {
                removedValue = currentJson.getAsJsonObject().remove(key.getAsString());
            }
        }
        return removedValue;
    }

    private void updateFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath + filename))) {
            String json = gson.toJson(db);
            writer.write(json);
        }
    }
}
