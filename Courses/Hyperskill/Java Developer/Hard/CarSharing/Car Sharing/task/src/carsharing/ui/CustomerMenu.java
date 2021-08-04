package carsharing.ui;

import carsharing.core.Facade;
import carsharing.core.model.Car;
import carsharing.core.model.Company;
import carsharing.core.model.Customer;

import java.util.List;

public class CustomerMenu extends AbstractMenu {
    private Customer customer;

    private final ICommand rentCar = () -> {
        if (customer.getRentedCarId() != null) {
            System.out.println("You've already rented a car!\n");
            return;
        }

        List<Company> companies = Facade.getInstance().getDatabase().getAllCompanies();
        if (companies.size() != 0) {
            System.out.println("Choose the company:");
            for (int i = 0; i < companies.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, companies.get(i).getName());
            }
            System.out.println("0. Back");
            int option = readIntegerLine();
            System.out.println();

            if (option == 0) return;
            Company company = companies.get(option - 1);
            List<Car> cars = Facade.getInstance().getDatabase().getAllCarsOfCompany(company);
            cars.removeAll(Facade.getInstance().getRentedCars());

            if (cars.size() != 0) {
                System.out.println("Choose a car:");
                for (int i = 0; i < cars.size(); i++) {
                    System.out.printf("%d. %s%n", i + 1, cars.get(i).getName());
                }
                System.out.println("0. Back");
                int choice  = readIntegerLine();
                if (choice == 0) return;
                Car rentedCar = cars.get(choice - 1);

                Customer newCustomer = new Customer.Builder()
                        .setId(customer.getId())
                        .setName(customer.getName())
                        .setCarId(rentedCar.getId())
                        .build();

                Facade.getInstance().getDatabase().updateCustomer(newCustomer);
                this.customer = newCustomer;
                System.out.printf("You rented '%s'%n%n", rentedCar.getName());
                Facade.getInstance().addRentedCar(rentedCar);
            } else {
                System.out.printf("No available cars in the '%s' company%n%n", company.getName());
            }
        } else {
            System.out.println("The company list is empty!\n");
        }
    };

    private final ICommand returnCar = () -> {
        if (customer.getRentedCarId() == null) {
            System.out.println("You didn't rent a car!\n");
            return;
        }

        Customer newCustomer = new Customer.Builder()
                .setId(customer.getId())
                .setName(customer.getName())
                .setCarId(null)
                .build();

        Facade.getInstance().getDatabase().updateCustomer(newCustomer);
        Facade.getInstance().removeRentedCarById(customer.getRentedCarId());
        this.customer = newCustomer;
        System.out.println("You've returned a rented car!\n");
    };

    private final ICommand showCar = () -> {
        if (customer.getRentedCarId() == null) {
            System.out.println("You didn't rent a car!\n");
            return;
        }

        Car car = Facade.getInstance().getDatabase().getCarById(customer.getRentedCarId());
        System.out.println("Your rented car:");
        System.out.println(car.getName());
        System.out.println("Company:");
        System.out.println(Facade.getInstance().getDatabase().getCompanyById(car.getCompanyId()).getName());
        System.out.println();
    };

    CustomerMenu (Customer customer) {
        this.customer = customer;
    }

    @Override
    public void invoke() {
        System.out.println("1. Rent a car\n" +
                "2. Return a rented car\n" +
                "3. My rented car\n" +
                "0. Back");
        int option = readIntegerLine();
        System.out.println();

        switch (option) {
            case 1:
                rentCar.execute();
                break;
            case 2:
                returnCar.execute();
                break;
            case 3:
                showCar.execute();
                break;
            case 0:
                backCommand.execute();
                break;
            default:
                exitCommand.execute();
                break;
        }
    }
}
