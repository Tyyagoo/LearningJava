package banking.ui;

import banking.service.Account;
import banking.ui.commands.Callback;
import banking.ui.commands.ICommand;
import banking.ui.commands.ShowBalance;

import java.util.Scanner;

public class AccountMenu extends AbstractUserInterface {

    private final Account account;
    private static final ICommand<Account> optionOne = new ShowBalance();

    AccountMenu(Scanner scanner, Account account) {
        super(scanner);
        this.account = account;
    }

    @Override
    public void invoke() {
        showOptions();
        Callback callback = getUserInput();
        if (callback.getResult() == ExitContext.EXIT_PROGRAM) {
            finishProgram = true;
            return;
        }
        if (callback.getResult() == ExitContext.EXIT_MENU) {
            finishMenu = true;
            return;
        }
    }

    @Override
    protected void showOptions() {
        System.out.println("1. Balance");
        System.out.println("2. Log out");
        System.out.println("0. Exit");
    }

    @Override
    protected Callback getUserInput() {
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                return optionOne.execute(scanner, account);
            case 2:
                System.out.println("You have successfully logged out!");
                return backCommand.execute(scanner);
            default:
                return exitCommand.execute(scanner);
        }
    }
}
