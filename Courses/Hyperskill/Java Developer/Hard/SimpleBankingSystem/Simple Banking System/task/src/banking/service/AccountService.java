package banking.service;

import banking.exceptions.InsufficientBalanceException;
import banking.exceptions.InvalidCredentialsException;
import banking.system.Bank;

import java.math.BigDecimal;


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

    public static Account getAccountByNumber(String number) throws InvalidCredentialsException {
        try {
            return Bank.getAccount(number);
        } catch (Exception e) {
            throw new InvalidCredentialsException();
        }
    }

    public static void makeTransaction(Transaction transaction, BigDecimal value) throws InsufficientBalanceException {
        transaction.setValue(value);
        Account sender = transaction.getSender();
        Account receiver = transaction.getReceiver();
        sender.withdraw(transaction.getValue()); // can throw InsufficientBalanceException
        receiver.deposit(transaction.getValue());
    }

}
