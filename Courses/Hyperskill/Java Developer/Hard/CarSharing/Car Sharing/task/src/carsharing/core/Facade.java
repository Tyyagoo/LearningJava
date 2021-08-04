package carsharing.core;

import carsharing.core.model.Car;
import carsharing.ui.AbstractMenu;
import carsharing.ui.LoginMenu;

import java.util.List;
import java.util.ArrayList;

public class Facade {
    private static Facade instance;
    private final Database database;

    // sorry, now i'm lazy xD
    private final List<Car> rentedCars = new ArrayList<>();

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

    public void addRentedCar(Car car) {
        rentedCars.add(car);
    }

    public void removeRentedCarById(int id) {
        List<Car> removeList = new ArrayList<>();
        for (Car car: rentedCars) {
            if (car.getId() == id) {
                removeList.add(car);
            }
        }
        rentedCars.removeAll(removeList);
    }

    public List<Car> getRentedCars() {
        return rentedCars;
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
