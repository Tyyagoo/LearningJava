package blockchain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        int numberOfMiners = 10;
        Chain blockchain = new Chain();
        ExecutorService botnet = Executors.newFixedThreadPool(numberOfMiners);

        for (int c = 0; c < numberOfMiners; c++) {
            Miner miner = new Miner(c + 1, blockchain);
            botnet.submit(miner);
        }
        botnet.shutdown();
        try {
            botnet.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
