package banking.service.cards;

import banking.exceptions.InsufficientBalanceException;
import banking.system.Bank;

import java.math.BigDecimal;
import java.util.Random;

public class Card {

    private final String number;
    private final String pin;
    private BigDecimal balance = new BigDecimal(0);

    Card(String number) { // for create new cards
        this.number = number;

        Random randomizer = new Random();
        StringBuilder pinBuilder = new StringBuilder();
        for (int i = 0; i < Bank.pinLength; i++) {
            pinBuilder.append(randomizer.nextInt(10));
        }
        pin = pinBuilder.toString();
        // Show pin only on the creation of account.
        System.out.printf("Your card PIN:%n%s%n", pin);
    }

    Card(String number, String pin, BigDecimal balance) { // for load existing cards
        this.number = number;
        this.pin = pin;
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public String getPin() { return pin; }

    public boolean isValidPin(String credential) {
        return pin.equals(credential);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void incrementBalance(BigDecimal quantity) {
        balance = balance.add(quantity);
    }

    public void decrementBalance(BigDecimal quantity) throws InsufficientBalanceException {
        if (balance.compareTo(quantity) < 0) throw new InsufficientBalanceException();
        balance = balance.subtract(quantity);
    }
}
