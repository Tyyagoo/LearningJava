package banking.ui;


import banking.service.Account;
import banking.ui.commands.Callback;
import banking.ui.commands.CreateAccount;
import banking.ui.commands.ICommand;
import banking.ui.commands.LoginAccount;

import java.util.Scanner;

public class MainMenu extends AbstractUserInterface {

    private static final ICommand<Account> optionOne = new CreateAccount();
    private static final ICommand<Account> optionTwo = new LoginAccount();

    private AbstractUserInterface subMenu = null;

    public MainMenu(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void invoke() {
        showOptions();
        Callback callback = getUserInput();
        if (callback == null) return;
        if (callback.getResult() == ExitContext.EXIT_PROGRAM) {
            finishProgram = true;
            return;
        }

        if (callback.getResult() == ExitContext.EXIT_MENU) {
            subMenu = null;
            return;
        }
        Account acc = (Account) callback.getResult();
    }

    @Override
    protected void showOptions() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    @Override
    protected Callback getUserInput() {
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                return optionOne.execute(scanner);
            case 2:
                Callback<Account> callback = optionTwo.execute(scanner);
                if (callback == null) return null;
                subMenu = new AccountMenu(scanner, callback.getResult());

                while (!(subMenu.isFinished() || subMenu.isStopped())) {
                    subMenu.invoke();
                }

                if (subMenu.isStopped()) return exitCommand.execute(scanner);
                return backCommand.execute(scanner);

            default:
                return exitCommand.execute(scanner);
        }
    }
}
