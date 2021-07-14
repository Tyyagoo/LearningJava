package banking.service.cards;

import java.math.BigDecimal;

public class CardFactory {


    public static Card createCard(String number) {
        // use Card(number);
        return new Card(number);
    }

    public static Card createCard(String number, String pin, BigDecimal balance) {
        return new Card(number, pin, balance);
    }
}
