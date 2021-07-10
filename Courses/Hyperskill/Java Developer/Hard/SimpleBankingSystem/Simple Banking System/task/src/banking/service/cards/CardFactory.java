package banking.service.cards;

public class CardFactory {


    public static Card createNewCard(char[] number) {
        // use Card(number);
        return new Card(number);
    }

    /*
    private static Card loadCardOnSystem() {
        // use Card(number, pin)
    }
     */
}
