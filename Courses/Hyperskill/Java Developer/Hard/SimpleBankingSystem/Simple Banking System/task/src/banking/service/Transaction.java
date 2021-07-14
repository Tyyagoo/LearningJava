package banking.service;

import banking.exceptions.InvalidCredentialsException;
import banking.exceptions.TransactionToSameAccountException;
import banking.system.Bank;

import java.math.BigDecimal;

public class Transaction {

    private final Account sender;
    private final String receiver;
    private BigDecimal value;
    private boolean status = false;

    public Transaction(Account s, String r) {
        this.sender = s;
        this.receiver = r;
        this.value = new BigDecimal(0);
    }

    public void check() throws InvalidCredentialsException, TransactionToSameAccountException {
        if (!Bank.checkNumber(receiver))
            throw new InvalidCredentialsException("Probably you made a mistake in the card number. Please try again!");

        if (sender.getNumber().equals(receiver))
            throw new TransactionToSameAccountException();

        try {
            AccountService.getAccountByNumber(receiver);
        } catch (InvalidCredentialsException e) {
            throw new InvalidCredentialsException("Such a card does not exist.");
        }

        status = true;
    }

    public Account getReceiver() {
        if (!status) return null;
        return AccountService.getAccountByNumber(receiver);
    }

    public Account getSender() {
        return sender;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) { this.value = value; }
}
