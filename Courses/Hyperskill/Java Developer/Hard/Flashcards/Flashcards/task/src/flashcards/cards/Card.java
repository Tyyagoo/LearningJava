package flashcards.cards;

public class Card {

    private final String term;
    private final String definition;
    private int mistakeCount;

    Card(String t, String d, int mistakes) {
        this.term = t;
        this.definition = d;
        this.mistakeCount = mistakes;
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }

    public int getMistakeCount() { return mistakeCount; }

    public void incMistakeCount() { mistakeCount++; }

    public void resetMistakeCount() { mistakeCount = 0; }

    @Override
    public String toString() {
        return String.format("(\"%s\":\"%s\")", term, definition);
    }
}
