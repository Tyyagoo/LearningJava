package banking.ui.commands;

import java.util.Scanner;

public interface ICommand<T> {
    Callback<T> execute(Scanner scanner, Object... args);
}

