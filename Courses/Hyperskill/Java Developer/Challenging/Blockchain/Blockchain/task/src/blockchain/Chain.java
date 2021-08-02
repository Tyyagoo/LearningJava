package blockchain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Chain implements Serializable {
    private static final long serialVersionUID = 1L;

    private volatile int difficultFactor;
    private final List<Block> chain;

    private long timestampStart;
    private long timestampFinish;

    Chain () {
        this.difficultFactor = 0;
        this.chain = new ArrayList<>();
        timestampStart = new Date().getTime();
    }

    synchronized public boolean validateBlock(Block block) {
        if (block == null) return false;
        if (getSize() == 5) return false;
        Block previousBlock = getLastBlock();
        String previousHash = (previousBlock != null) ? previousBlock.getHash() : "0";
        if (previousHash.equals(block.getPreviousHash()) &&
                block.getHash().substring(0, difficultFactor).equals(getRequiredStart())) {
            chain.add(block);
            timestampFinish = new Date().getTime();
            long delta = timestampFinish - timestampStart;
            System.out.println(block);
            System.out.printf("Block was generating for %d seconds%n", delta / 1000);
            balanceChainGrowth(delta);
            System.out.println();
            timestampStart = new Date().getTime();
            return true;
        }
        return false;
    }

    public synchronized void balanceChainGrowth(long delta) {
        if (delta / 1000 < 10) {
            difficultFactor++;
            System.out.printf("N was increased to %d%n", difficultFactor);
            return;
        }

        if (delta / 1000 > 60) {
            difficultFactor--;
            System.out.printf("N was decreased by %d%n", difficultFactor);
            return;
        }

        System.out.println("N stays the same");
    }

    public Block getLastBlock() {
        return (getSize() > 0) ? chain.get(getSize() - 1) : null;
    }

    public int getSize() {
        return chain.size();
    }

    public int getDifficultFactor() {
        return difficultFactor;
    }

    public String getRequiredStart() {
        return "0".repeat(difficultFactor);
    }
}
