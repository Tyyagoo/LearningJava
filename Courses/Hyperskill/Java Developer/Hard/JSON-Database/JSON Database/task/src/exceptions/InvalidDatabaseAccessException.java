package exceptions;

public class InvalidDatabaseAccessException extends RuntimeException {
    public InvalidDatabaseAccessException() {
        super("ERROR", null);
    }
}
