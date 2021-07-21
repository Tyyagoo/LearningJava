package flashcards.cards;

import java.util.*;

public class CardManager {

    private static final Map<String, Card> map = new TreeMap<>();

    public static void storeCard(Card card) {
        map.put(card.getTerm(), card);
    }

    public static Card getCardByTerm(String term) {
        return map.get(term);
    }

    public static Card getCardByDefinition(String definition) {
        for (Card card: map.values()) {
            if (card.getDefinition().equals(definition)) return card;
        }
        return null;
    }

    public static Map<String, Card> getAllCards() {
        return map;
    }

    public static void removeCardByTerm(String term) {
        map.remove(term);
    }

    public static void removeCardByDefinition(String definition) {
        try {
            map.remove(getCardByDefinition(definition).getTerm());
        } catch (NullPointerException ignore) {

        }

    }

    public static boolean containsTerm(String term) {
        return getCardByTerm(term) != null;
    }

    public static boolean containsDefinition(String definition) {
        return getCardByDefinition(definition) != null;
    }
}
