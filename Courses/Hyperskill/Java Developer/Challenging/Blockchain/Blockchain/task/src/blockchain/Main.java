package blockchain;

import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("Enter how many zeros the hash must start with: ");
        int n = new Scanner(System.in).nextInt();
        System.out.println();
        Chain blockchain = new Chain(n);
        while (blockchain.getSize() < 5) {
            long timestampStart = new Date().getTime();
            while (!blockchain.mine());
            long timestampFinish = new Date().getTime();
            long timeDelta = timestampFinish - timestampStart;

            System.out.println(blockchain.getLastBlock());
            System.out.printf("Block was generating for %d seconds%n%n", timeDelta / 1000);
        }
    }
}
