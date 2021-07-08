package battleship.Exceptions;

public class InvalidShipSizeException  extends RuntimeException{
    public InvalidShipSizeException(String shipName) {
        super(String.format("Error! Wrong length of the %s! Try again:", shipName), null);
    }
}
