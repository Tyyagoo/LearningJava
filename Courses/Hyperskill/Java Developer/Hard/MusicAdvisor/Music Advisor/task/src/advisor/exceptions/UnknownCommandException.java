package advisor.exceptions;

public class UnknownCommandException extends RuntimeException {
    public UnknownCommandException() {
        super("Unknown command.", null);
    }
}
