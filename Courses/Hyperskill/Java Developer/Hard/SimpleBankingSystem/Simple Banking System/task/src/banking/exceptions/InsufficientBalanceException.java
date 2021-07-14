package banking.exceptions;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(){
        super("Not enough money!", null);
    }
}
