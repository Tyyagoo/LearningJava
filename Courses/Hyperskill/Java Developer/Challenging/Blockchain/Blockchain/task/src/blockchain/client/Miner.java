package blockchain.client;

import blockchain.client.action.Message;
import blockchain.core.Block;
import blockchain.core.Blockchain;
import blockchain.core.Wallet;
import blockchain.util.Facade;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class Miner implements Runnable {

    private long id;
    private Wallet wallet;
    private Blockchain ledger;

    public Miner(long id, Blockchain ledger) {
        this.id = id;
        this.wallet = new Wallet(String.format("miner%d", id));
        this.ledger = ledger;
    }

    @Override
    public void run() {
        while (ledger.isOnline()) {
            Block block = mine();
            if (validateBlockPreviously(block)) {
                if (ledger.validateBlock(block)) {
                    wallet.receive(block.getReward()); // this is really dangerous lol
                    Optional<Message> transaction = wallet.makeTransaction(Facade.governmentWallet,
                            wallet.getTotalCurrency()); // oops, it looks like taxes on this coin are 100%
                    transaction.ifPresent(t -> ledger.addMessageToBlockchain(t));
                }
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
