package carsharing.core;

import carsharing.ui.AbstractMenu;
import carsharing.ui.LoginMenu;

public class Facade {
    private static Facade instance;
    private final Database database;


    private Facade(Database database) {
        this.database = database;
    }

    public void start() {
        database.initialize();
        AbstractMenu rootMenu = new LoginMenu();
        while (rootMenu.isRunning() && rootMenu.isInvokable()) {
            rootMenu.invoke();
        }
    }

    public Database getDatabase() {
        return database;
    }

    public static void createInstance(Database database) {
        if (instance == null) {
            instance = new Facade(database);
        }
    }

    public static Facade getInstance() {
        return instance;
    }
}
