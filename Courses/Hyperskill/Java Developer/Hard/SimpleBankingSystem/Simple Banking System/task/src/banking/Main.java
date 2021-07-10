package banking;

import banking.service.Account;
import banking.service.AccountService;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Account acc = AccountService.createNewAccount();
        }
    }
}