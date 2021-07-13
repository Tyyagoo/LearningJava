package banking.service;

import banking.Bank;
import banking.service.cards.Card;
import banking.service.cards.CardFactory;

import java.math.BigDecimal;
import java.util.Random;

public class Account {

    private final String number;
    private Card card;


    public Account() {
        number = Bank.generateAccountNumber().substring(Bank.binLength);
        System.out.println("Your card number:");
        System.out.printf("%s%s%n", String.valueOf(Bank.bankIdentificationNumber), number);
        this.card = CardFactory.createNewCard(number);
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
