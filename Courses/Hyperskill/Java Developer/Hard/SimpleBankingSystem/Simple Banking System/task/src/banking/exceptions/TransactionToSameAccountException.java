package banking.exceptions;

public class TransactionToSameAccountException extends RuntimeException {
    public TransactionToSameAccountException() {
        super("You can't transfer money to the same account!", null);
    }
}
