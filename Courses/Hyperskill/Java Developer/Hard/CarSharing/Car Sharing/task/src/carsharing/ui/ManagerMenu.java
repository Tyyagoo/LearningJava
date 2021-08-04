package carsharing.ui;

import carsharing.core.model.Company;
import carsharing.core.Facade;

import java.util.List;

public class ManagerMenu extends AbstractMenu {

    private final ICommand showCompanyList = () -> {
        List<Company> companyList = Facade.getInstance().getDatabase().getAllCompanies();
        if (companyList.size() != 0) {
            System.out.println("Choose the company:");
            for (int i = 0; i < companyList.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, companyList.get(i).getName());
            }
            System.out.println("0. Back");
            int option = readIntegerLine();
            System.out.println();

            switch (option) {
                case 0:
                    break;
                default:
                    AbstractMenu companyMenu = new CompanyMenu(companyList.get(option - 1));
                    while (companyMenu.isRunning() && companyMenu.isInvokable()) {
                        companyMenu.invoke();
                    }
                    break;
            }
        } else {
            System.out.println("The company list is empty!\n");
        }
    };

    private final ICommand createCompany = () -> {
        System.out.println("Enter the company name:");
        String name = scanner.nextLine();
        Company company = new Company.Builder()
                .setName(name)
                .build();
        Facade.getInstance().getDatabase().insertCompany(company);
        System.out.println("The company was created!\n");
    };

    @Override
    public void invoke() {
        System.out.println("1. Company list\n" +
                "2. Create a company\n" +
                "0. Back");
        int option = readIntegerLine();
        System.out.println();
        switch (option) {
            case 1:
                showCompanyList.execute();
                break;
            case 2:
                createCompany.execute();
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
