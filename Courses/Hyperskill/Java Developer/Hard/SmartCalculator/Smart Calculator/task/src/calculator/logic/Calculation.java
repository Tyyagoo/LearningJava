package calculator.logic;

import calculator.exceptions.UnbalancedExpressionException;
import calculator.exceptions.UnsupportedOperationException;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@FunctionalInterface
interface Operation<T> {
    T apply(T n1, T n2);
}

public class Calculation {

    private static final Operation<BigInteger> sum = (n1, n2) -> n1.add(n2);
    private static final Operation<BigInteger> sub = (n1, n2) -> n2.subtract(n1);
    private static final Operation<BigInteger> mult = (n1, n2) -> n1.multiply(n2);
    private static final Operation<BigInteger> div = (n1, n2) -> n2.divide(n1);
    private static final Operation<BigInteger> pow = (n1, n2) -> n2.pow(n1.intValue());

    private final Map<Character, Operation<BigInteger>> supportedOperations = new HashMap<>();

    Stack<BigInteger> numbers = new Stack<>();
    Stack<Character> operators = new Stack<>();

    public Calculation() {
        supportedOperations.put('+', sum);
        supportedOperations.put('-', sub);
        supportedOperations.put('*', mult);
        supportedOperations.put('/', div);
        supportedOperations.put('^', pow);
    }

    public BigInteger evaluate(String exp) {
        if (!isBalanced(exp)) throw new UnbalancedExpressionException();
        Pattern isNumber = Pattern.compile("\\d");
        Pattern isOperator = Pattern.compile("[\\+\\-\\*/\\(\\)\\^]");
        String[] tokens = exp.split("");

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals(" ")) continue;

            if (isOperator.matcher(tokens[i]).matches()) {
                char s = tokens[i].toCharArray()[0];
                switch (s) {
                    case '(':
                        operators.push(s);
                        break;
                    case '+': case '-': case '*': case '/': case '^':
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
                StringBuilder stringBuilder = new StringBuilder();
                while (isNumber.matcher(tokens[i]).matches()) {
                    stringBuilder.append(tokens[i++]);
                    if (i == tokens.length) break;
                }
                --i;
                numbers.push(new BigInteger(stringBuilder.toString()));
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

    private BigInteger applyOperator(char op, BigInteger n1, BigInteger n2) {
        /*
        Calculate the desired operation.
        If this is not supported, throws an UnsupportedOperationException.
         */
        if (supportedOperations.containsKey(op)) {
            Operation<BigInteger> operation = supportedOperations.get(op);
            return operation.apply(n1, n2);
        }
        throw new UnsupportedOperationException();
    }

    private boolean checkPriority(char op1, char op2) {
        /*
        Checks if the first operator have arithmetic priority over second operator.
         */
        if (op2 == '(' || op2 == ')') return false;
        if ((op1 == '^') && ((op2 == '*' || op2 == '/') || (op2 == '+' || op2 == '-'))) return false;
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }
}
