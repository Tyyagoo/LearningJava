package banking.ui.commands;

import banking.Bank;
import banking.exceptions.InvalidCredentialsException;
import banking.service.Account;
import banking.service.AccountService;
import java.util.Scanner;

public class LoginAccount implements ICommand<Account> {

    @Override
    public Callback<Account> execute(Scanner scanner, Object... args) {
        String number = "";
        String pin = "";
        System.out.println("Enter your card number:");
        while (number.length() != Bank.numberLength) {
            number = scanner.nextLine();
        }
        System.out.println("Enter your PIN:");
        while (pin.length() != Bank.pinLength) {
            pin = scanner.nextLine();
        }

        try {
            Account acc = AccountService.logIntoAccount(number, pin);
            System.out.println("You have successfully logged in!");
            return new Callback<>(acc);
        } catch (InvalidCredentialsException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
