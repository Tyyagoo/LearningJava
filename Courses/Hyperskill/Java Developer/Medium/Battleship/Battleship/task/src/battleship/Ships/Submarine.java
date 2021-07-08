package battleship.Ships;

import battleship.Position;
import battleship.Vector2;

public class Submarine extends AbstractShip{

    public static final String name = "Submarine";
    public static final int size = 3;

    public Submarine(Position position) {
        super(position);
    }

    public Submarine(Vector2 from, Vector2 to) {
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
