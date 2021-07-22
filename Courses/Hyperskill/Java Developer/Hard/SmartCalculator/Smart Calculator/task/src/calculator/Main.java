package calculator;

import calculator.ui.Menu;

public class Main {

    public static void main(String[] args) {
        while (Menu.isRunning()) {
            try {
                Menu.invoke();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
