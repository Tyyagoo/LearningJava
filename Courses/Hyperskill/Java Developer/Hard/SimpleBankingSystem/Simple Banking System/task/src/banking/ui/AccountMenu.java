package banking.ui;

import banking.exceptions.InsufficientBalanceException;
import banking.exceptions.InvalidCredentialsException;
import banking.exceptions.NonExistentAccountException;
import banking.exceptions.TransactionToSameAccountException;
import banking.service.Account;
import banking.service.AccountService;
import banking.service.Transaction;
import banking.system.Bank;
import banking.ui.commands.Callback;
import banking.ui.commands.ICommand;
import banking.ui.commands.ShowBalance;

import java.math.BigDecimal;
import java.util.Scanner;

public class AccountMenu extends AbstractUserInterface {

    private final Account account;
    private static final ICommand<Account> optionOne = new ShowBalance();

    private static final ICommand<Account> optionTwo = (scanner, args) -> {
        if (args.length != 0) {
            try {
                Account acc = (Account) args[0];

                System.out.println("Enter income:");
                int value = scanner.nextInt();
                acc.deposit(new BigDecimal(value));
                System.out.println("Income was added!");
                return new Callback<>(acc);

            } catch (Exception e) {
                throw new NonExistentAccountException();
            }
        }
        throw new NonExistentAccountException();
    };
    private static final ICommand<Account> optionThree = (scanner, args) -> {
        if (args.length != 0) {
            try {
                Account acc = (Account) args[0];
                System.out.println("Transfer");
                System.out.println("Enter card number:");
                String receiverNumber = "";
                while (receiverNumber.length() < 1) {
                    receiverNumber = scanner.nextLine();
                }

                Transaction transaction = new Transaction(acc, receiverNumber);
                try {
                    transaction.check();
                    System.out.println("Enter how much money you want to transfer:");
                    int money = scanner.nextInt();
                    AccountService.makeTransaction(transaction, new BigDecimal(money));
                    System.out.println("Success!");
                } catch (InvalidCredentialsException |TransactionToSameAccountException |
                        InsufficientBalanceException e) {
                    System.out.println(e.getMessage());
                }
                return new Callback<>(acc);

            } catch (Exception e) {
                throw new NonExistentAccountException();
            }
        }
        throw new NonExistentAccountException();
    };
    private static final ICommand<ExitContext> optionFour = (scanner, args) -> {
        if (args.length != 0) {
            try {
                Account acc = (Account) args[0];
                Bank.closeAccount(acc);
                System.out.println("The account has been closed!");
                return new Callback<>(ExitContext.EXIT_MENU);
            } catch (Exception e) {
                throw new NonExistentAccountException();
            }
        }
        throw new NonExistentAccountException();
    };

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
        }
    }

    @Override
    protected void showOptions() {
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
    }

    @Override
    protected Callback getUserInput() {
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                return optionOne.execute(scanner, account);
            case 2:
                return optionTwo.execute(scanner, account);
            case 3:
                return optionThree.execute(scanner, account);
            case 4:
                return optionFour.execute(scanner, account);
            case 5:
                System.out.println("You have successfully logged out!");
                return backCommand.execute(scanner);
            default:
                return exitCommand.execute(scanner);
        }
    }
}
