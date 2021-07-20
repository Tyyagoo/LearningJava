package flashcards.cards;

import java.util.*;

public class CardManager {

    private static final Map<Integer, Card> map = new TreeMap<>();

    public static int getCurrentId() {
        return map.size();
    }

    public static void storeCard(Card card) {
        int cardId = getCurrentId() + 1;
        map.put(cardId, card);
    }

    public static Card getCardById(int id) {
        return map.get(id);
    }

    public static Card getCardByDefinition(String definition) {
        for (Card card: map.values()) {
            if (card.getDefinition().equals(definition)) return card;
        }
        return null;
    }

    public static Card getCardByTerm(String term) {
        for (Card card: map.values()) {
            if (card.getTerm().equals(term)) return card;
        }
        return null;
    }

    public static boolean containsTerm(String term) {
        return getCardByTerm(term) != null;
    }

    public static boolean containsDefinition(String definition) {
        return getCardByDefinition(definition) != null;
    }
}
