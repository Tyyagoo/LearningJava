package banking.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Wrong card number or PIN!", null);
    }
}
