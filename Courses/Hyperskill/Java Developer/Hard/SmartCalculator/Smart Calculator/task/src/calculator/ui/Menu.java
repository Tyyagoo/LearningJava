package calculator.ui;

import calculator.Calculator;
import calculator.exceptions.UnknownCommandException;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);
    private static boolean running = true;

    private static final Pattern commandPattern = Pattern.compile("/[a-zA-Z]*");

    private static final ICommand helpCommand = (ignore) -> {
        System.out.println("This is a help command. I hope I helped you!");
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

        Calculator.process(formattedString);
    }

    public static String formatInput(String input) {
        String output = input;

        /*
        Remove unnecessary whitespaces from string, and returns an new String.
         */
        Pattern unnecessaryWhiteSpacesPattern = Pattern.compile("\\s+");
        output = unnecessaryWhiteSpacesPattern.matcher(output).replaceAll(" ");

        Matcher commandMatcher = commandPattern.matcher(output);
        if (commandMatcher.matches()) {
            return output;
        }

        /*
        Arranging the operators
         */
        Pattern stackablePlus = Pattern.compile("\\+{2,}");
        output = stackablePlus.matcher(output).replaceAll("+");

        Pattern stackableMinus = Pattern.compile("-{3}");
        output = stackableMinus.matcher(output).replaceAll("-");

        Pattern minusToPlus = Pattern.compile("-{2}");
        output = minusToPlus.matcher(output).replaceAll("+");

        Pattern allToMinus = Pattern.compile("(\\+\\s*-|-\\s*\\+)");
        output = allToMinus.matcher(output).replaceAll("-");

        /*
        Handle initial number being negative.
         */
        output = output.trim();
        if (output.charAt(0) == '-') return "0 " + output;
        else if (output.charAt(0) == '+') return output.replaceFirst("\\+", "").trim();

        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = output.toCharArray();
        int i = 0;
        do {
            char c = chars[i];
            switch (c) {
                case ' ':
                    i++;
                    continue;
                case '*': case '+': case '-': case '/': case '^':
                    stringBuilder.append(" ");
                    stringBuilder.append(c);
                    stringBuilder.append(" ");
                    break;
                default:
                    stringBuilder.append(c);
                    break;
            }
            i++;
        } while(i < chars.length);

        return stringBuilder.toString();
    }

    public static boolean isRunning() { return running; }
}
