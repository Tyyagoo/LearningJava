package banking.ui.commands;


import banking.service.Account;
import banking.service.AccountService;
import java.util.Scanner;

public class CreateAccount implements ICommand<Account> {

    @Override
    public Callback<Account> execute(Scanner scanner, Object... args) {
        Account account = AccountService.createNewAccount();
        return new Callback<>(account);
    }
}
