package calculator;

import calculator.exceptions.InvalidAssignmentException;
import calculator.exceptions.InvalidIdentifierException;
import calculator.exceptions.UnknownVariableException;
import calculator.logic.Calculation;
import calculator.ui.Menu;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.*;

public class Calculator {

    private static final Map<String, BigInteger> memory = new HashMap<>();

    public static void process(String line) throws UnknownVariableException {
        if (line.contains("=")) {
            doAssignment(line);
            return;
        }

        Pattern hasVariables = Pattern.compile("[a-zA-Z]");
        if (hasVariables.matcher(line).find()) {
            String[] possibleVariables = line.split(" ");
            for (int i = 0; i < possibleVariables.length; i++) {
                String w = possibleVariables[i].trim();
                if (w.matches("[a-zA-Z]+")) {
                    possibleVariables[i] = String.valueOf(getMemoryValue(w));
                }
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (String str: possibleVariables) {
                stringBuilder.append(str).append(" ");
            }
            line = stringBuilder.toString().trim();
        }

        line = Menu.formatInput(line);
        calculate(line);
    }

    private static void doAssignment(String assignment) throws InvalidAssignmentException {
        String[] keyValue = assignment.replace(" ", "").split("=");
        if (keyValue.length != 2) throw new InvalidAssignmentException();

        if (!keyValue[0].matches("[a-zA-Z]+")) throw new InvalidIdentifierException();
        if (!keyValue[1].matches("([a-zA-Z]+|[\\+\\-]?\\d+)")) throw new InvalidAssignmentException();

        Pattern isNumber = Pattern.compile("[\\+\\-]?\\d+");
        Matcher checkNumber = isNumber.matcher(keyValue[1]);
        memory.put(keyValue[0], checkNumber.matches() ? new BigInteger(keyValue[1]) : getMemoryValue(keyValue[1]));
    }

    private static BigInteger getMemoryValue(String key) throws UnknownVariableException {
        if (memory.containsKey(key)) {
            return memory.get(key);
        }
        throw new UnknownVariableException();
    }

    private static void calculate(String expression) {
        Calculation calc = new Calculation();
        System.out.println(calc.evaluate(expression).toString());
    }
}
