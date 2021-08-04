package blockchain;

import blockchain.core.Blockchain;
import blockchain.util.Facade;

public class Main {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        Facade facade = new Facade(blockchain);
        facade.exit();
    }
}
