package battleship.Exceptions;

public class ShipSankedException extends RuntimeException {

    public ShipSankedException() {
        super("You sank a ship!", null);
    }
}
