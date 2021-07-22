package calculator.exceptions;

public class UnknownCommandException extends RuntimeException {
    public UnknownCommandException() {
        super("Unknown Command.", null);
    }
}
