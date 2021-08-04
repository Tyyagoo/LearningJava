package carsharing;

import carsharing.core.Database;
import carsharing.core.Facade;

import java.util.Map;
import java.util.HashMap;

public class Main {
    private static final String FILEPATH_TEST_ENVIRONMENT = System.getProperty("user.dir") + "/src/carsharing/db/";
    private static final String FILEPATH_LOCAL_ENVIRONMENT = System.getProperty("user.dir") + "/Car Sharing/task/src/carsharing/db/";
    private static final String filepath = FILEPATH_TEST_ENVIRONMENT;
    private static final Map<String, String> configurations = new HashMap<>();

    public static void main(String[] args) {
        if (args.length % 2 != 0) throw new IllegalArgumentException();
        for (int i = 0; i < args.length; i++) {
            configurations.put(args[i], args[++i]);
        }

        Database database = new Database(filepath +
                configurations.getOrDefault("-databaseFileName", "test_db"));

        Facade.createInstance(database);
        Facade.getInstance().start();
    }
}