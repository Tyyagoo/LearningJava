package blockchain;

import blockchain.util.*;

import java.io.Serializable;
import java.util.Date;

public class Block implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int nonce;
    private final int id;
    private final long timestamp;
    private final String previousHash;
    private final String hash;

    public Block (int id, int nonce, String previousHash) {
        this.id = id;
        this.nonce = nonce;
        this.timestamp = new Date().getTime();
        this.previousHash = previousHash;
        this.hash = generateHash();
    }

    private String generateHash() {
        String blockData = serialVersionUID +
                previousHash +
                timestamp +
                id +
                nonce;

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

    @Override
    public String toString() {
        return "Block:\n" +
                "Id: " + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Magic number: " + nonce + "\n" +
                "Hash of the previous block: \n" +
                previousHash + "\n" +
                "Hash of the block: \n" +
                hash;
    }

}
