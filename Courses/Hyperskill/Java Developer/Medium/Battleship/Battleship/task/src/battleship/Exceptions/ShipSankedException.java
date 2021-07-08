package battleship.Exceptions;

public class ShipSankedException extends RuntimeException {

    public ShipSankedException() {
        super("You sank a ship! Specify a new target:", null);
    }
}
