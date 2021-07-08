package battleship.Ships;

import battleship.Position;
import battleship.Vector2;

public class AircraftCarrier extends AbstractShip{

    public static final String name = "Aircraft Carrier";
    public static final int size = 5;

    public AircraftCarrier(Position position) {
        super(position);
    }

    public AircraftCarrier(Vector2 from, Vector2 to) {
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
