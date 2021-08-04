package blockchain.client;

import blockchain.core.Block;
import blockchain.core.Blockchain;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class Miner implements Runnable {

    private long id;
    private Blockchain ledger;

    public Miner(long id, Blockchain ledger) {
        this.id = id;
        this.ledger = ledger;
    }

    @Override
    public void run() {
        while (ledger.isOnline()) {
            Block block = mine();
            if (validateBlockPreviously(block)) {
                ledger.validateBlock(block);
            }
        }
    }

    private Block mine() {
        // long id, long nonce, long minerId, long timestamp, String previousHash, List<Message> data
        return new Block(ledger.getSize() + 1,
                ThreadLocalRandom.current().nextLong(Long.MAX_VALUE),
                id,
                new Date().getTime(),
                ledger.getLastBlockHash(),
                ledger.getMessages());
    }

    private boolean validateBlockPreviously(Block block) {
        if (block == null) return false;
        return ledger.getLastBlockHash().equals(block.getPreviousHash()) &&
                block.getHash().substring(0, ledger.getDifficultFactor()).equals(ledger.getRequiredStart());
    }
}
