package battleship.Exceptions;

public class InvalidShipLocationException extends RuntimeException{
    public InvalidShipLocationException() {
        super("Error! Wrong ship location! Try again:", null);
    }
}
