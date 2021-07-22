package calculator.ui;

import calculator.Calculator;
import calculator.exceptions.UnknownCommandException;

import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);
    private static boolean running = true;

    private static final Pattern commandPattern = Pattern.compile("/[a-zA-Z]*");

    private static final ICommand helpCommand = (ignore) -> {
        System.out.println("The program calculates the sum of numbers");
    };
    private static final ICommand exitCommand = (ignore) -> {
        System.out.println("Bye!");
        running = false;
    };

    public static void invoke() {
        String inputLine = scanner.nextLine();
        if (inputLine.isBlank()) return;
        String formattedString = formatInput(inputLine);

        Matcher commandMatcher = commandPattern.matcher(formattedString);
        if (commandMatcher.matches()) {
            String desiredCommand = formattedString.replace("/", "").toLowerCase();
            if (desiredCommand.equals("help")) {
                helpCommand.execute();
                return;
            }
            if (desiredCommand.equals("exit")) {
                exitCommand.execute();
                return;
            }
            throw new UnknownCommandException();
        }

        Calculator.calculate(formattedString);
    }

    private static String formatInput(String input) {
        String output = input;

        /*
        Remove unnecessary whitespaces from string, and returns an new String.
         */
        Pattern unnecessaryWhiteSpacesPattern = Pattern.compile("\\s+");
        output = unnecessaryWhiteSpacesPattern.matcher(output).replaceAll(" ");

        /*
        Arranging the operators
         */
        Pattern stackablePlus = Pattern.compile("\\+{2,}");
        output = stackablePlus.matcher(output).replaceAll("+");

        Pattern stackableMinus = Pattern.compile("-{3}");
        output = stackableMinus.matcher(output).replaceAll("-");

        Pattern minusToPlus = Pattern.compile("-{2}");
        output = minusToPlus.matcher(output).replaceAll("+");

        Pattern allToMinus = Pattern.compile("(\\+-|-\\+)");
        output = allToMinus.matcher(output).replaceAll("-");

        /*
        Handle initial number being negative.
         */
        output = output.trim();
        if (output.charAt(0) == '-') return "0 " + output;
        return output;
    }

    public static boolean isRunning() { return running; }
}
