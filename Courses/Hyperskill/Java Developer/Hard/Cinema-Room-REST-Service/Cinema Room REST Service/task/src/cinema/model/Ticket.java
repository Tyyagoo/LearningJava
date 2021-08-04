package cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Ticket {

    @JsonProperty(value = "token", required = true)
    private UUID token;

    @JsonProperty(value = "ticket", required = true)
    private Seat seat;

    public Ticket() {}

    public Ticket(Seat seat) {
        this.token = UUID.randomUUID();
        this.seat = seat;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
