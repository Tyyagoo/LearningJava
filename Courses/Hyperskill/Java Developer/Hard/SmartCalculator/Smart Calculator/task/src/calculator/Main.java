package calculator;

import calculator.exceptions.UnbalancedExpressionException;
import calculator.exceptions.UnknownCommandException;
import calculator.exceptions.UnsupportedOperationException;
import calculator.ui.Menu;

public class Main {

    public static void main(String[] args) {
        while (Menu.isRunning()) {
            try {
                Menu.invoke();
            } catch (UnsupportedOperationException | UnknownCommandException | UnbalancedExpressionException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
