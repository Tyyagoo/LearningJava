package carsharing.ui;

public class LoginMenu extends AbstractMenu {

    private final ICommand loginCommand = () -> {
        AbstractMenu loggedMenu = new ManagerMenu();
        while (loggedMenu.isInvokable() && loggedMenu.isRunning()) {
            loggedMenu.invoke();
        }
    };

    @Override
    public void invoke() {
        System.out.println("1. Log in as a manager");
        System.out.println("0. Exit");
        int option = readIntegerLine();
        System.out.println();
        switch (option) {
            case 1:
                loginCommand.execute();
                break;
            default:
                exitCommand.execute();
        }
    }
}
