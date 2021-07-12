package banking.ui.commands;

import banking.exceptions.NonExistentAccountException;
import banking.service.Account;

import java.util.Scanner;

public class ShowBalance implements ICommand<Account> {

    @Override
    public Callback<Account> execute(Scanner scanner, Object... args) {
        if (args.length != 0) {
            try {
                Account acc = (Account) args[0];
                System.out.printf("Balance: %d%n", acc.getBalance().intValue());
                return new Callback<>(acc);
            } catch (Exception e) {
                throw new NonExistentAccountException();
            }
        }
        throw new NonExistentAccountException();
    }
}
