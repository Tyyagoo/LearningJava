package blockchain;

import blockchain.util.*;

import java.util.Date;

public class Block {
    private final int id;
    private final long timestamp;
    private final String previousHash;
    private final String hash;

    public Block (int id, String previousHash) {
        this.id = id;
        this.timestamp = new Date().getTime();
        this.previousHash = previousHash;
        this.hash = generateHash();
    }

    private String generateHash() {
        String blockData = previousHash +
                timestamp +
                id;

        return StringUtil.applySha256(blockData);
    }

    public int getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }
}
