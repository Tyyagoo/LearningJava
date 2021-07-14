package banking.service;

import banking.system.Bank;
import banking.service.cards.Card;
import banking.service.cards.CardFactory;

import java.math.BigDecimal;

public class Account {

    private final String number;
    private Card card;


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
