package battleship.Ships;

import battleship.Position;
import battleship.Vector2;

public class Battleship extends AbstractShip{

    public static final String name = "Battleship";
    public static final int size = 4;

    public Battleship(Position position) {
        super(position);
    }

    public Battleship(Vector2 from, Vector2 to) {
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
