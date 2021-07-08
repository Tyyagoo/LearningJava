package battleship;

public class Position {

    private Vector2 from;
    private Vector2 to;

    public Position(Vector2 from, Vector2 to) {
        this.from = from;
        this.to = to;
    }

    public Vector2 getFrom() {
        return from;
    }

    public Vector2 getTo() {
        return to;
    }
}
