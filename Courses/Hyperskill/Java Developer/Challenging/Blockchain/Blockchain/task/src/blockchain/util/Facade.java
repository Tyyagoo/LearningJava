package blockchain.util;

import blockchain.client.Miner;
import blockchain.client.User;
import blockchain.core.Blockchain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Facade {

    private final Blockchain blockchain;
    private final ExecutorService usersPool;
    private final ExecutorService minersPool;

    public Facade(Blockchain blockchain) {
        this.blockchain = blockchain;
        this.usersPool = Executors.newSingleThreadExecutor();
        int poolSize = Runtime.getRuntime().availableProcessors();
        this.minersPool = Executors.newFixedThreadPool(poolSize);

        initializeMiners(poolSize);
        initializeUsers();
    }

    private void initializeUsers() {
        for (int i = 0; i < 5; i++) {
            usersPool.submit(new User(String.format("User #%d", i + 1),
                    "Private content",
                    blockchain));
        }
    }

    private void initializeMiners(int numbersOfMiners) {
        for (int i = 0; i < numbersOfMiners; i++) {
            minersPool.submit(new Miner(i + 1, blockchain));
        }
    }

    public void exit() {
        usersPool.shutdown();
        minersPool.shutdown();

        try {
            usersPool.awaitTermination(3, TimeUnit.SECONDS);
            minersPool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
