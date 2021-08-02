package blockchain;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Chain implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int difficultFactor;
    private final List<Block> chain;
    private final String requiredStart;

    Chain (int difficultFactor) {
        this.difficultFactor = difficultFactor;
        this.chain = new ArrayList<>();
        this.requiredStart = "0".repeat(difficultFactor);
    }

    public boolean mine() {
        String previousHash = (chain.size() > 0) ? chain.get(chain.size() - 1).getHash() : "0";
        Block block = new Block(chain.size() + 1,
                new Random().nextInt(Integer.MAX_VALUE),
                previousHash);
        // verify block hash
        if (block.getHash().substring(0, difficultFactor).equals(requiredStart)) {
            chain.add(block);
            return true;
        } else {
            return false;
        }
    }

    public Block getLastBlock() {
        return chain.get(chain.size() - 1);
    }

    public int getSize() {
        return chain.size();
    }
}
