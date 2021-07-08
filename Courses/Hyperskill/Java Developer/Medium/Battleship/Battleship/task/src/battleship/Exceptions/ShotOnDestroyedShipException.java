package battleship.Exceptions;

public class ShotOnDestroyedShipException extends RuntimeException {
    public ShotOnDestroyedShipException() {
        super("", null);
    }
}
