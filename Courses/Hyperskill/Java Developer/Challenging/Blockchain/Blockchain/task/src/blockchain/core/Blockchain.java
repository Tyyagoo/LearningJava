package blockchain.core;

import blockchain.core.Block;
import blockchain.client.action.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class Blockchain {
    private static final long serialVersionUID = 1L;
    private static final int blockThreshold = 15;
    public static final int miningReward = 100;

    private final List<Block> blocks;
    private volatile int difficultFactor;

    private final List<Message> messages;
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    private long timestampStart;
    private long timestampFinish;

    private boolean online;

    public Blockchain() {
        this.blocks = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.difficultFactor = 0;
        timestampStart = new Date().getTime();
        this.online = true;
    }

    public synchronized boolean validateBlock(Block block) {
        if (getSize() >= blockThreshold) {
            online = false;
        }
        if (block == null || !isOnline()) return false;
        writeLock.lock();
        String lastBlockHash = getLastBlockHash();
        if (lastBlockHash.equals(block.getPreviousHash()) &&
                block.getHash().substring(0, difficultFactor).equals(getRequiredStart()) &&
                block.getFormattedData().equals(getFormattedData())) {
            blocks.add(block);
            messages.clear();
            timestampFinish = new Date().getTime();
            long delta = timestampFinish - timestampStart;
            System.out.println(block);
            System.out.printf("Block was generating for %d seconds%n", delta / 1000);
            balanceChainGrowth(delta);
            System.out.println();
            timestampStart = new Date().getTime();
            writeLock.unlock();
            return true;
        }
        writeLock.unlock();
        return false;
    }

    public synchronized void addMessageToBlockchain(Message message) {
        writeLock.lock();
        messages.add(message);
        writeLock.unlock();
    }

    public synchronized void balanceChainGrowth(long delta) {
        if (difficultFactor > 3) {
            System.out.println("N stays the same");
            difficultFactor = 3;
            return;
        }

        if (delta / 1000 < 1) {
            difficultFactor++;
            System.out.printf("N was increased to %d%n", difficultFactor);
            return;
        }

        if (delta / 1000 > 2) {
            difficultFactor--;
            System.out.printf("N was decreased by %d%n", difficultFactor);
            return;
        }

        System.out.println("N stays the same");
    }

    /*
    GETTERS
     */

    public String getLastBlockHash() {
        return (getSize() > 0) ? blocks.get(getSize() - 1).getHash() : "0";
    }

    public Block getLastBlock() {
        return (getSize() > 0) ? blocks.get(getSize() - 1) : null;
    }

    public int getSize() {
        return blocks.size();
    }

    public int getDifficultFactor() {
        return difficultFactor;
    }

    public String getRequiredStart() {
        return "0".repeat(difficultFactor);
    }

    public boolean isOnline() {
        return online;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public String getFormattedData() {
        return (getMessages().isEmpty()) ? "no messages" : "\n" + getMessages().stream()
                .map(Message::toString)
                .collect(Collectors.joining("\n"));
    }
}
