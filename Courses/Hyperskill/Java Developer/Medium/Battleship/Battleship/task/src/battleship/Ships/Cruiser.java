package battleship.Ships;

import battleship.Position;
import battleship.Vector2;

public class Cruiser extends AbstractShip {

    public static final String name = "Cruiser";
    public static final int size = 3;

    public Cruiser(Position position) {
        super(position);
    }

    public Cruiser(Vector2 from, Vector2 to) {
        super(from, to);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        return size;
    }
}
