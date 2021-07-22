package calculator.logic;

import calculator.exceptions.UnbalancedExpressionException;
import calculator.exceptions.UnsupportedOperationException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@FunctionalInterface
interface Operation<T> {
    T apply(T n1, T n2);
}

public class Calculation {

    private static final Operation<Integer> sum = (n1, n2) -> n1 + n2;
    private static final Operation<Integer> sub = (n1, n2) -> n2 - n1;

    private final Map<Character, Operation<Integer>> supportedOperations = new HashMap<>();

    Stack<Integer> numbers = new Stack<>();
    Stack<Character> operators = new Stack<>();

    public Calculation() {
        supportedOperations.put('+', sum);
        supportedOperations.put('-', sub);
    }

    public int evaluate(String exp) {
        if (!isBalanced(exp)) throw new UnbalancedExpressionException();
        Pattern isNumber = Pattern.compile("\\d");
        Pattern isOperator = Pattern.compile("[\\+\\-\\*/\\(\\)]");
        String[] tokens = exp.split("");

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals(" ")) continue;

            if (isOperator.matcher(tokens[i]).matches()) {
                char s = tokens[i].toCharArray()[0];
                switch (s) {
                    case '(':
                        operators.push(s);
                        break;
                    case '+': case '-': case '*': case '/':
                        while (!operators.isEmpty() && checkPriority(s, operators.peek())) {
                            doSomeOperation();
                        }
                        operators.push(s);
                        break;
                    case ')':
                        while (operators.peek() != '(') {
                            doSomeOperation();
                        }
                        operators.pop();
                        break;
                }
                continue;
            }

            if (isNumber.matcher(tokens[i]).matches()) {
                List<Integer> nList = new LinkedList<>();
                while (isNumber.matcher(tokens[i]).matches()) {
                    nList.add(Integer.parseInt(tokens[i++]));
                    if (i == tokens.length) break;
                }
                --i;

                int position = nList.size() - 1;
                int number = 0;
                for (int n : nList) {
                    int magnitude = (int) Math.pow(10, position--);
                    number += n * magnitude;
                }
                numbers.push(number);
            }
        }

        while (!operators.isEmpty()) {
            doSomeOperation();
        }

        if (numbers.isEmpty()) throw new UnsupportedOperationException("Invalid expression");
        return numbers.pop();
    }

    private boolean isBalanced(String exp) {
        Stack<Character> parenthesesStack = new Stack<>();
        try {
            for (char token: exp.toCharArray()) {
                if (token == '(') parenthesesStack.push(token);
                if (token == ')') parenthesesStack.pop();
            }
        } catch (EmptyStackException e) {
            return false;
        }
        return parenthesesStack.isEmpty();
    }

    private void doSomeOperation() {
        try {
            numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
        } catch (EmptyStackException e) {
            throw new UnsupportedOperationException("Invalid expression");
        }
    }

    private int applyOperator(char op, int n1, int n2) {
        /*
        Calculate the desired operation.
        If this is not supported, throws an UnsupportedOperationException.
         */
        if (supportedOperations.containsKey(op)) {
            Operation<Integer> operation = supportedOperations.get(op);
            return operation.apply(n1, n2);
        }
        throw new UnsupportedOperationException();
    }

    private boolean checkPriority(char op1, char op2) {
        /*
        Checks if the first operator have arithmetic priority over second operator.
         */
        if (op2 == '(' || op2 == ')') return false;
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }
}
