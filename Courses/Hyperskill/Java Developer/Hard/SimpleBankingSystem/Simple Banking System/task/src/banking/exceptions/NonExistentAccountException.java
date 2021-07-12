package banking.exceptions;

public class NonExistentAccountException extends RuntimeException {
    public NonExistentAccountException() {
        super("Invalid Account.", null);
    }
}
