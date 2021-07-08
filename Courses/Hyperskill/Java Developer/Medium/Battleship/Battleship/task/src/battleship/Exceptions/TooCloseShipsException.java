package battleship.Exceptions;


public class TooCloseShipsException extends RuntimeException {
    public TooCloseShipsException() {
        super("Error! You placed it too close to another one. Try again:", null);
    }
}
