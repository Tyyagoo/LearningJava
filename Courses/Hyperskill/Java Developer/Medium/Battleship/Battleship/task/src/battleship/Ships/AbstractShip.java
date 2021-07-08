package battleship.Ships;

import battleship.Position;
import battleship.Vector2;

public abstract class AbstractShip {

    private Position position;

    public abstract String getName();
    public abstract int getSize();

    public AbstractShip(Position position) {
        this.position = position;
    }

    public AbstractShip(Vector2 from, Vector2 to) {
        this.position = new Position(from, to);
    }

    public Position getPosition() {
        return position;
    }
}
