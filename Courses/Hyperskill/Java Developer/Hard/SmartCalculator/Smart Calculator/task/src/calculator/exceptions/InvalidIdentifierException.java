package calculator.exceptions;

public class InvalidIdentifierException extends RuntimeException {
    public InvalidIdentifierException() {
        super("Invalid identifier", null);
    }
}
