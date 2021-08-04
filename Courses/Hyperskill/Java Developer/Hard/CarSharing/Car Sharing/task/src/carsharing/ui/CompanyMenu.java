package carsharing.ui;

import carsharing.core.Facade;
import carsharing.core.model.Car;
import carsharing.core.model.Company;

import java.util.List;

public class CompanyMenu extends AbstractMenu {

    private Company company;

    private final ICommand showCarList = () -> {
        List<Car> cars = Facade.getInstance().getDatabase().getAllCarsOfCompany(company);

        if (cars.size() != 0) {
            System.out.println("Car list:");

            for (int i = 0; i < cars.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, cars.get(i).getName());
            }

            System.out.println();
        } else {
            System.out.println("The car list is empty!\n");
        }
    };

    private final ICommand createCar = () -> {
        System.out.println("Enter the car name:");
        String name = scanner.nextLine();

        Car car = new Car.Builder()
                .setName(name)
                .setCompanyId(company.getId())
                .build();

        Facade.getInstance().getDatabase().insertCar(car);
        System.out.println("The car was added!\n");
    };

    CompanyMenu (Company company) {
        this.company = company;
    }

    @Override
    public void invoke() {
        System.out.printf("'%s' company:%n", company.getName());
        System.out.println("1. Car list\n" +
                "2. Create a car\n" +
                "0. Back");
        int option = readIntegerLine();
        System.out.println();

        switch (option) {
            case 1:
                showCarList.execute();
                break;
            case 2:
                createCar.execute();
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
