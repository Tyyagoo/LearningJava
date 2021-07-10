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

    public static Account logIntoAccount(char[] number, char[] pin) throws InvalidCredentialsException {
        Account acc = getAccountByNumber(number); // can throw InvalidCredentialsException
        if (acc.getCard().isValidPin(pin)) return acc;
        throw new InvalidCredentialsException(); // if pin isn't the same, throw InvalidCredentials
    }

    private static Account getAccountByNumber(char[] number) throws InvalidCredentialsException {
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

    private static int getBucketNumber(char[] number) {
        int sum = 0;
        for (char n: number) {
            sum += n;
        }
        return sum;
    }

    private static boolean numbersAreEqual(char[] n1, char[] n2) {
        for (int i = 0; i < Bank.numberLength - Bank.binLength; i++) {
            if (n1[i] != n2[i]) return false;
        }
        return true;
    }


}
