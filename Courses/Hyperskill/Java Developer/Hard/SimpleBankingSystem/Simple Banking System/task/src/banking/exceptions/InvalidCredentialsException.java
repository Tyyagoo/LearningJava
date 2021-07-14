package banking.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String ... message) {

        super((message.length == 0) ? "Wrong card number or PIN!" : message[0], null);
    }
}
