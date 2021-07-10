package banking.service.cards;

import banking.Bank;

import java.util.Random;

public class Card {

    private final char[] number = new char[Bank.numberLength - Bank.binLength];
    private final char[] pin = new char[Bank.pinLength];

    Card(char[] number) { // for create new cards
        System.arraycopy(number, 0, this.number, 0, number.length);

        Random randomizer = new Random();

        for (int i = 0; i < pin.length; i++) {
            pin[i] = (char) randomizer.nextInt(10);
        }

        // Show pin only on the creation of account.
        System.out.println("Your card PIN:");
        for (char n: pin) {
            System.out.printf("%d", (int) n);
        }
        System.out.println();
    }

    Card(char[] number, char[] pin) { // for load existing cards
        System.arraycopy(number, 0, this.number, 0, number.length);
        System.arraycopy(pin, 0, this.pin, 0, pin.length);
    }

    public char[] getNumber() {
        return number;
    }

    public boolean isValidPin(char[] credential) {
        for (int i = 0; i < pin.length; i++) {
            if (pin[i] != credential[i]) return false;
        }
        return true;
    }
}
