package banking.service;

import banking.system.Bank;
import banking.service.cards.Card;
import banking.service.cards.CardFactory;

import java.math.BigDecimal;

public class Account {

    private final String number;
    private final Card card;


    public Account() {
        number = Bank.generateAccountNumber();
        System.out.println("Your card number:");
        System.out.printf("%s%n", number);
        this.card = CardFactory.createCard(number);
    }

    public Account(String number, String pin, BigDecimal balance) {
        this.number = number;
        this.card = CardFactory.createCard(number, pin, balance);
    }

    public void deposit(BigDecimal value) {
        card.incrementBalance(value);
        Bank.updateAccountBalance(this);
    }

    public void withdraw(BigDecimal value) {
        card.decrementBalance(value);
        Bank.updateAccountBalance(this);
    }

    public String getNumber() {
        return number;
    }

    public Card getCard() {
        return card;
    }

    public BigDecimal getBalance() {
        return card.getBalance();
    }
}
