package banking.service;

import banking.system.Bank;
import banking.exceptions.InvalidCredentialsException;


public class AccountService {

    public static Account createNewAccount() {
        System.out.println("Your card has been created");
        Account account = new Account();
        registryAccount(account);
        return account;
    }

    private static void registryAccount(Account account) {
        Bank.registryAccount(account);
    }

    public static Account logIntoAccount(String number, String pin) throws InvalidCredentialsException {
        if(number.length() != Bank.numberLength || pin.length() != Bank.pinLength) throw new InvalidCredentialsException();
        if (!Bank.checkNumber(number)) throw new InvalidCredentialsException(); // invalid Luhn
        Account acc = getAccountByNumber(number); // can throw InvalidCredentialsException
        if (acc.getCard().isValidPin(pin)) return acc;
        throw new InvalidCredentialsException(); // if pin isn't the same, throw InvalidCredentials
    }

    private static Account getAccountByNumber(String number) throws InvalidCredentialsException {
        try {
            return Bank.getAccount(number);
        } catch (Exception e) {
            throw new InvalidCredentialsException();
        }
    }

    private static boolean numbersAreEqual(String n1, String n2) {
        return n1.equals(n2);
    }


}
