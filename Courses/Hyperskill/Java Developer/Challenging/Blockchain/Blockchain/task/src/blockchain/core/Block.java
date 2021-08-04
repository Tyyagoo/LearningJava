package blockchain.core;

import blockchain.client.action.Message;
import blockchain.util.StringUtil;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class Block implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
    BLOCK HEADER
     */
    private final long ID;
    private final long NONCE;
    private final long MINER_ID;
    private final String HASH;
    private final long TIMESTAMP;
    private final String PREVIOUS_HASH;


    /*
    BLOCK CONTENT
     */
    private final List<Message> DATA;


    public Block(long id, long nonce, long minerId, long timestamp, String previousHash, List<Message> data) {
        this.ID = id;
        this.NONCE = nonce;
        this.MINER_ID = minerId;
        this.TIMESTAMP = timestamp;
        this.PREVIOUS_HASH = previousHash;
        this.DATA = data;
        this.HASH = calculateHash();
    }

    private String calculateHash() {
        return StringUtil.applySha256(
                Long.toString(ID) +
                        NONCE +
                        MINER_ID +
                        TIMESTAMP +
                        PREVIOUS_HASH +
                        getFormattedData()
        );
    }

    @Override
    public String toString() {
        return String.format("Block:\n" +
                        "Created by miner # %s\n" +
                        "Id: %s\n" +
                        "Timestamp: %s\n" +
                        "Magic number: %s\n" +
                        "Hash of the previous block: \n" +
                        "%s\n" +
                        "Hash of the block: \n" +
                        "%s\n" +
                        "Block data: %s",
                getMinerId(), getId(), getTimestamp(), getNonce(), getPreviousHash(), getHash(), getFormattedData());
    }

    /*
    GETTERS
     */

    public long getId() {
        return ID;
    }

    public long getNonce() {
        return NONCE;
    }

    public long getMinerId() {
        return MINER_ID;
    }

    public long getTimestamp() {
        return TIMESTAMP;
    }

    public String getHash() {
        return HASH;
    }

    public String getPreviousHash() {
        return PREVIOUS_HASH;
    }

    public List<Message> getData() {
        return DATA;
    }

    public String getFormattedData() {
        return (getData().isEmpty()) ? "no messages" : "\n" + getData().stream()
                                                            .map(Message::toString)
                                                            .collect(Collectors.joining("\n"));
    }
}