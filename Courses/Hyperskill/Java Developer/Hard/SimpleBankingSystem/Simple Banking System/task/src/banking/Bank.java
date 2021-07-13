package banking;

import java.util.Random;

public class Bank {

    public static final char[] bankIdentificationNumber = {'4', '0', '0', '0', '0', '0'};
    public static final int binLength = 6;
    public static final int numberLength = 16;
    public static final int pinLength = 4;

    public static String generateAccountNumber() {
        StringBuilder numberBuilder = new StringBuilder();
        numberBuilder.append(String.valueOf(bankIdentificationNumber));

        Random randomizer = new Random();
        for (int i = binLength; i < numberLength - 1; i++) {
            numberBuilder.append(randomizer.nextInt(10));
        }

        int sum = 0;
        String currentString = numberBuilder.toString();
        for (int i = 0; i < currentString.length(); i++) {
            int digit = Integer.parseInt(String.valueOf(currentString.charAt(i)));
            if ((i + 1) % 2 != 0) digit *= 2;
            if (digit > 9) digit -= 9;
            sum += digit;
        }

        int checksum;
        if (sum % 10 == 0) checksum = 0;
        else checksum = 10 - (sum % 10);
        numberBuilder.append(checksum);
        return numberBuilder.toString();
    }

    public static boolean checkNumber(String number) {
        int i, sum = 0;
        for (i = 1; i < numberLength; i++) {
            int n = Integer.parseInt(String.valueOf(number.charAt(i - 1)));
            if (i % 2 != 0) n *= 2;
            if (n > 9) n -= 9;
            sum += n;
        }
        sum += Integer.parseInt(String.valueOf(number.charAt(i - 1)));
        return sum % 10 == 0;
    }
}
