package blockchain.core;

import blockchain.client.action.Message;

import java.util.Optional;

public class Wallet {
    private long currency;
    private String ownerName; // yes, this is lazy

    public Wallet(String ownerName) {
        this.currency = 100;
        this.ownerName = ownerName;
    }

    public void receive(long amount) {
        if (amount > 0) {
            currency += amount;
        }
    }

    public Optional<Message> makeTransaction(Wallet to, long quantity) {
        if (quantity < 1 || quantity > currency) return Optional.empty();
        currency -= quantity;
        to.receive(quantity);
        return Optional.of(new Message(ownerName,
                String.format("transferred %d VC to %s.", quantity, to.ownerName)));
    }

    public long getTotalCurrency() {
        return currency;
    }
}
