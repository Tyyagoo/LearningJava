package battleship;

import battleship.Exceptions.InvalidShipSizeException;
import battleship.Ships.*;
import battleship.Exceptions.InvalidShipException;


public class ShipFactory {

    public static AbstractShip makeShip(String shipName, Vector2 from, Vector2 to) throws InvalidShipException{
        AbstractShip ship;
        Position position = new Position(from, to);
        switch (shipName) {
            case AircraftCarrier.name:
                ship = new AircraftCarrier(position);
                break;
            case Battleship.name:
                ship = new Battleship(position);
                break;
            case Submarine.name:
                ship = new Submarine(position);
                break;
            case Cruiser.name:
                ship = new Cruiser(position);
                break;
            case Destroyer.name:
                ship = new Destroyer(position);
                break;
            default:
                throw new InvalidShipException(shipName);
        }
        return ship;
    }

    public static int getShipSizeByName(String shipName) throws InvalidShipException {
        int size = 0;
        switch (shipName) {
            case AircraftCarrier.name:
                size = AircraftCarrier.size;
                break;
            case Battleship.name:
                size = Battleship.size;
                break;
            case Submarine.name:
                size = Submarine.size;
                break;
            case Cruiser.name:
                size = Cruiser.size;
                break;
            case Destroyer.name:
                size = Destroyer.size;
                break;
            default:
                throw new InvalidShipException(shipName);
        }
        return size;
    }
}
