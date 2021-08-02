package blockchain;

public class Main {
    public static void main(String[] args) {
        Block[] blockchain = new Block[5];

        String previousHash = "0";
        for (int i = 0; i < blockchain.length; i++) {
            blockchain[i] = new Block(i + 1, previousHash);
            previousHash = blockchain[i].getHash();
        }

        for (Block block: blockchain) {
            System.out.println("Block:");
            System.out.printf("Id: %d%n", block.getId());
            System.out.printf("Timestamp: %s%n", block.getTimestamp());
            System.out.println("Hash of the previous block: ");
            System.out.println(block.getPreviousHash());
            System.out.println("Hash of the block: ");
            System.out.println(block.getHash());
            System.out.println();
        }
    }
}
