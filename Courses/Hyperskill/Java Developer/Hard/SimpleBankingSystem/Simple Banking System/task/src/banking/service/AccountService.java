package banking.service;

import banking.Bank;
import banking.exceptions.InvalidCredentialsException;

import java.util.*;

public class AccountService {

    private static final Map<Integer, ArrayList<Account>> accounts = new HashMap<>();

    public static Account createNewAccount() {
        System.out.println("Your card has been created");
        Account account = new Account();
        registryAccount(account);
        return account;
    }

    private static void registryAccount(Account account) {
        int bucketNumber = getAccountBucket(account);

        if (accounts.containsKey(bucketNumber)) {
            accounts.get(bucketNumber).add(account);
        } else {
            ArrayList<Account> accountList = new ArrayList<>();
            accountList.add(account);
            accounts.put(bucketNumber, accountList);
        }
    }

    public static Account logIntoAccount(String number, String pin) throws InvalidCredentialsException {
        String numberWithoutBin = number.substring(6);
        Account acc = getAccountByNumber(numberWithoutBin); // can throw InvalidCredentialsException
        if (acc.getCard().isValidPin(pin)) return acc;
        throw new InvalidCredentialsException(); // if pin isn't the same, throw InvalidCredentials
    }

    private static Account getAccountByNumber(String number) throws InvalidCredentialsException {
        int bucketNumber = getBucketNumber(number);

        if (accounts.containsKey(bucketNumber)) {
            for (Account acc: accounts.get(bucketNumber)) {
                if (numbersAreEqual(number, acc.getNumber())) return acc;
            }
            throw new InvalidCredentialsException();
        }
        throw new InvalidCredentialsException();
    }

    private static int getAccountBucket(Account acc) {
        return getBucketNumber(acc.getNumber());
    }

    private static int getBucketNumber(String number) {
        int sum = 0;
        char[] numberArray = number.toCharArray();
        for (char n: numberArray) {
            sum += n;
        }
        return sum;
    }

    private static boolean numbersAreEqual(String n1, String n2) {
        return n1.equals(n2);
    }


}
