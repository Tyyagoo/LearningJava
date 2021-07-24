package server;

import server.console.Console;
import server.console.UserInterface;
import server.database.Database;

public class Main {

    public static void main(String[] args) {
        Database database = new Database();
        UserInterface.setDatabase(database);

        while (UserInterface.isRunning()) {
            try {
                UserInterface.invoke();
            } catch (Exception e) {
                Console.println(e.getMessage());
            }

        }
    }
}
