package banking.ui.commands;

public class Callback<T> {
    private final T callback;

    public Callback(T callback) {
        this.callback = callback;
    }

    public T getResult() {
        return callback;
    }
}
