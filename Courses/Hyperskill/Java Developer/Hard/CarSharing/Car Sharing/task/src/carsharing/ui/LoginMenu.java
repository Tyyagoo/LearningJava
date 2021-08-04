package carsharing.ui;

import carsharing.core.Facade;
import carsharing.core.model.Customer;

import java.util.List;

public class LoginMenu extends AbstractMenu {

    private final ICommand loginManager = () -> {
        AbstractMenu loggedMenu = new ManagerMenu();
        while (loggedMenu.isInvokable() && loggedMenu.isRunning()) {
            loggedMenu.invoke();
        }
    };

    private final ICommand loginCustomer = () -> {
        List<Customer> customers = Facade.getInstance().getDatabase().getAllCustomers();

        if (customers.size() != 0) {
            System.out.println("Choose a customer:");
            for (int i = 0; i < customers.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, customers.get(i).getName());
            }
            System.out.println("0. Back");

            int option = readIntegerLine();
            if (option < 1) return;
            AbstractMenu customerMenu = new CustomerMenu(customers.get(option - 1));

            while (customerMenu.isInvokable() && customerMenu.isRunning()) {
                customerMenu.invoke();
            }

        } else {
            System.out.println("The customer list is empty!\n");
        }
    };

    private final ICommand createCustomer = () -> {
        System.out.println("Enter the customer name:");
        String name = scanner.nextLine();

        Customer customer = new Customer.Builder()
                .setName(name)
                .setCarId(null)
                .build();

        Facade.getInstance().getDatabase().insertCustomer(customer);
        System.out.println("The customer was added!\n");
    };

    @Override
    public void invoke() {
        System.out.println("1. Log in as a manager");
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
        System.out.println("0. Exit");
        int option = readIntegerLine();
        System.out.println();
        switch (option) {
            case 1:
                loginManager.execute();
                break;
            case 2:
                loginCustomer.execute();
                break;
            case 3:
                createCustomer.execute();
                break;
            default:
                exitCommand.execute();
                break;
        }
    }
}
