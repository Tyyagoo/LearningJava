package battleship.Exceptions;

public class InvalidShipException extends RuntimeException {

    public InvalidShipException(String shipName) {
        super(String.format("'%s' isn't a valid ship.", shipName), null);
    }
}
