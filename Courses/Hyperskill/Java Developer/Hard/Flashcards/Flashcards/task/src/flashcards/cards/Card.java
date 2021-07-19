package flashcards.cards;

public class Card {

    private final String term;
    private final String definition;

    Card(String t, String d) {
        this.term = t;
        this.definition = d;
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }
}
