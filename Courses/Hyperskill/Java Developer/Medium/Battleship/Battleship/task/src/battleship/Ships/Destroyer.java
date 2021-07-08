package battleship.Ships;

import battleship.Position;
import battleship.Vector2;

public class Destroyer extends AbstractShip {

    public static final String name = "Destroyer";
    public static final int size = 2;

    public Destroyer(Position position) {
        super(position);
    }

    public Destroyer(Vector2 from, Vector2 to) {
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
