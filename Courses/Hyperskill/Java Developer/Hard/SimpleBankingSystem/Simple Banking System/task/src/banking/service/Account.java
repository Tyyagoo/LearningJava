package banking.service;

import banking.Bank;
import banking.service.cards.Card;
import banking.service.cards.CardFactory;

import java.util.Random;

public class Account {

    private final char[] number = new char[Bank.numberLength - Bank.binLength];
    private Card card;


    public Account() {
        Random randomizer = new Random();

        for (int i = 0; i < number.length; i++) {
            number[i] = (char) randomizer.nextInt(10);
        }

        System.out.println("Your card number:");
        for (char n: Bank.bankIdentificationNumber) {
            System.out.printf("%d", (int) n);
        }
        for (char n: number) {
            System.out.printf("%d", (int) n);
        }
        System.out.println();
        this.card = CardFactory.createNewCard(number);
    }

    public char[] getNumber() {
        return number;
    }

    public Card getCard() {
        return card;
    }
}
