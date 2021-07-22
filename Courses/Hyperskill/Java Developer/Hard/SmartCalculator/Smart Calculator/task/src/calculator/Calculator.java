package calculator;

import calculator.logic.Calculation;

public class Calculator {

    public static void calculate(String expression) {
        Calculation calc = new Calculation();
        System.out.println(calc.evaluate(expression));
    }
}
