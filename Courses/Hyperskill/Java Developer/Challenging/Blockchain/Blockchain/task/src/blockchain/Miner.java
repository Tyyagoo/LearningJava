package blockchain;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Miner implements Runnable {

    private final int id;
    private final Chain blockchain;

    public Miner(int id, Chain blockchain) {
        this.blockchain = blockchain;
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            if (blockchain.getSize() == 5) break;

            Block block = mine();
            if (validateHash(block)) {
                blockchain.validateBlock(block);
            }
        }
    }

    private Block mine() {
        Block previousBlock = blockchain.getLastBlock();
        String previousHash = (previousBlock != null) ? previousBlock.getHash() : "0";
        // int id, int nonce, int minerId, String previousHash
        return new Block(blockchain.getSize() + 1,
                ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE),
                id,
                previousHash);
    }

    private boolean validateHash(Block block) {
        if (block == null) return false;
        return block.getHash().substring(0, blockchain.getDifficultFactor()).equals(blockchain.getRequiredStart());
    }
}
