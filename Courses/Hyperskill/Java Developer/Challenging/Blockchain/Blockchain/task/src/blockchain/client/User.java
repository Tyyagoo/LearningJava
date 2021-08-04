package blockchain.client;

import blockchain.client.action.Message;
import blockchain.core.Blockchain;

public class User implements Runnable {

    private final Message message;
    private final Blockchain target;

    public User(String name, String message, Blockchain target) {
        this.message = new Message(name, message);
        this.target = target;
    }

    @Override
    public void run() {
        target.addMessageToBlockchain(message);
    }
}
