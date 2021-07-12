import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.hyperskill.hstest.dynamic.DynamicTest;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleBankSystemTest extends StageTest<String> {

    private static final Pattern cardNumberPattern = Pattern.compile("^400000\\d{10}$", Pattern.MULTILINE);
    private static final Pattern pinPattern = Pattern.compile("^\\d{4}$", Pattern.MULTILINE);

    @DynamicTest
    CheckResult test1_checkCardCredentials() {

        TestedProgram program = new TestedProgram();
        program.start();

        String output = program.execute("1");

        Matcher cardNumberMatcher = cardNumberPattern.matcher(output);

        if (!cardNumberMatcher.find()) {
            return CheckResult.wrong("You are printing the card number " +
                "incorrectly. The card number should look like in the example: " +
                "400000DDDDDDDDDD, where D is a digit.");
        }

        Matcher pinMatcher = pinPattern.matcher(output);

        if (!pinMatcher.find()) {
            return CheckResult.wrong("You are printing the card PIN " +
                "incorrectly. The PIN should look like in the example: DDDD, where D is " +
                "a digit.");
        }

        String correctCardNumber = cardNumberMatcher.group();

        output = program.execute("1");
        cardNumberMatcher = cardNumberPattern.matcher(output);

        if (!cardNumberMatcher.find()) {
            return CheckResult.wrong("You are printing the card number " +
                "incorrectly. The card number should look like in the example: " +
                "400000DDDDDDDDDD, where D is a digit.");
        }

        pinMatcher = pinPattern.matcher(output);

        if (!pinMatcher.find()) {
            return CheckResult.wrong("You are printing the card PIN " +
                "incorrectly. The PIN should look like in the example: DDDD, where D is " +
                "a digit.");
        }

        String anotherCardNumber = cardNumberMatcher.group();

        if (anotherCardNumber.equals(correctCardNumber)) {
            return CheckResult.wrong("Your program generates two identical card numbers!");
        }

        program.execute("0");

        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult test2_checkLogInAndLogOut() {

        TestedProgram program = new TestedProgram();
        program.start();

        String output = program.execute("1");

        Matcher cardNumberMatcher = cardNumberPattern.matcher(output);

        if (!cardNumberMatcher.find()) {
            return new CheckResult(false, "You are printing the card number " +
                "incorrectly. The card number should look like in the example: " +
                "400000DDDDDDDDDD, where D is a digit.");
        }

        Matcher pinMatcher = pinPattern.matcher(output);

        if (!pinMatcher.find()) {
            return new CheckResult(false, "You are printing the card PIN " +
                "incorrectly. The PIN should look like in the example: DDDD, where D is " +
                "a digit.");
        }
        String correctPin = pinMatcher.group().trim();
        String correctCardNumber = cardNumberMatcher.group();

        program.execute("2");
        output = program.execute(correctCardNumber + "\n" + correctPin);

        if (!output.toLowerCase().contains("successfully")) {
            return new CheckResult(false, "The user should be signed in after " +
                "entering the correct card information.");
        }

        output = program.execute("2");

        if (!output.toLowerCase().contains("create")) {
            return new CheckResult(false, "The user should be logged out after choosing 'Log out' option.\n" +
                "And you should print the menu with 'Create an account' option.");
        }

        program.execute("0");

        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult test3_checkLogInWithWrongPin() {

        TestedProgram program = new TestedProgram();
        program.start();

        String output = program.execute("1");

        Matcher cardNumberMatcher = cardNumberPattern.matcher(output);
        Matcher pinMatcher = pinPattern.matcher(output);

        if (!cardNumberMatcher.find() || !pinMatcher.find()) {
            return new CheckResult(false, "You should output card number and PIN like in example!");
        }

        String correctCardNumber = cardNumberMatcher.group();
        String correctPin = pinMatcher.group();

        Random random = new Random();

        String incorrectPin = correctPin;

        while (correctPin.equals(incorrectPin)) {
            incorrectPin = String.valueOf(1000 + random.nextInt(8999));
        }

        program.execute("2");
        output = program.execute(correctCardNumber + "\n" + incorrectPin);

        if (output.toLowerCase().contains("successfully")) {
            return new CheckResult(false, "The user should not be signed in after" +
                " entering incorrect card information.");
        }

        program.execute("0");

        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult test4_checkLogInToNotExistingAccount() {

        TestedProgram program = new TestedProgram();
        program.start();

        String output = program.execute("1");

        Matcher cardNumberMatcher = cardNumberPattern.matcher(output);
        Matcher pinMatcher = pinPattern.matcher(output);

        if (!cardNumberMatcher.find() || !pinMatcher.find()) {
            return new CheckResult(false, "You should output card number " +
                "and PIN like in example");
        }

        String correctCardNumber = cardNumberMatcher.group();

        Random random = new Random();

        String correctPin = pinMatcher.group().trim();
        String incorrectCardNumber = correctCardNumber;

        while (correctCardNumber.equals(incorrectCardNumber)) {
            incorrectCardNumber = "400000" + (1_000_000_00 + random.nextInt(8_000_000_00));
        }

        program.execute("2");
        output = program.execute(incorrectCardNumber + "\n" + correctPin);

        if (output.toLowerCase().contains("successfully")) {
            return new CheckResult(false, "The user should not be signed" +
                " in after entering the information of a non-existing card.");
        }

        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult test5_checkBalance() {

        TestedProgram program = new TestedProgram();
        program.start();

        String output = program.execute("1");

        Matcher cardNumberMatcher = cardNumberPattern.matcher(output);
        Matcher pinMatcher = pinPattern.matcher(output);

        if (!cardNumberMatcher.find() || !pinMatcher.find()) {
            return new CheckResult(false, "You should output card number and PIN like in example");
        }

        String correctPin = pinMatcher.group().trim();
        String correctCardNumber = cardNumberMatcher.group();

        program.execute("2");
        program.execute(correctCardNumber + "\n" + correctPin);
        output = program.execute("1");

        if (!output.contains("0")) {
            return new CheckResult(false, "Expected balance: 0");
        }

        program.execute("0");

        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult test6_checkLuhnAlgorithm() {

        TestedProgram program = new TestedProgram();
        program.start();

        String output = program.execute("1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1");

        Matcher cardNumberMatcher = cardNumberPattern.matcher(output);

        boolean isSomeCardFound = false;
        int foundCards = 0;

        while (cardNumberMatcher.find()) {

            foundCards++;

            if (!isSomeCardFound) {
                isSomeCardFound = true;
            }

            String cardNumber = cardNumberMatcher.group();

            if (!checkLuhnAlgorithm(cardNumber)) {
                return new CheckResult(false, String.format("The card number %s doesn’t pass the Luhn algorithm.", cardNumber));
            }
        }

        if (!isSomeCardFound) {
            return new CheckResult(false, "You should output card number and PIN like in example");
        }

        if (foundCards != 20) {
            return new CheckResult(false, "Tried to generate 20 cards, but found " + foundCards);
        }

        return CheckResult.correct();
    }

    private boolean checkLuhnAlgorithm(String cardNumber) {
        int result = 0;
        for (int i = 0; i < cardNumber.length(); i++) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));
            if (i % 2 == 0) {
                int doubleDigit = digit * 2 > 9 ? digit * 2 - 9 : digit * 2;
                result += doubleDigit;
                continue;
            }
            result += digit;
        }
        return result % 10 == 0;
    }
}