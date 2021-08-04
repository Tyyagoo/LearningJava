package cinema.controller;

import cinema.exception.InvalidSeatException;
import cinema.model.Room;
import cinema.model.Seat;
import cinema.model.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class RoomController {

    private Map<UUID, Ticket> ticketMap = new HashMap<>();

    @GetMapping("/seats")
    public ResponseEntity<Room> getRoomInfo() {
        return new ResponseEntity<>(Room.getInstance(), HttpStatus.OK);
    }

    @PostMapping("/purchase")
    public ResponseEntity<Ticket> purchaseTicket(@RequestBody Seat seat) {
        int x = Room.getInstance().getRows();
        int y = Room.getInstance().getColumns();
        if (seat.getRowNumber() < 1 || seat.getRowNumber() > x ||
                seat.getColumnNumber() < 1 || seat.getColumnNumber() > y) {
            // return new ResponseEntity<>("The number of a row or a column is out of bounds!", HttpStatus.BAD_REQUEST);
            throw new InvalidSeatException("The number of a row or a column is out of bounds!");
        }

        if (!Room.getInstance().getAvailableSeats().contains(seat)) {
            // return new ResponseEntity<>("The ticket has been already purchased!", HttpStatus.BAD_REQUEST);
            throw new InvalidSeatException("The ticket has been already purchased!");
        }

        Room.getInstance().getAvailableSeats().remove(seat);
        seat.updatePrice();
        Ticket ticket = new Ticket(seat);
        ticketMap.put(ticket.getToken(), ticket);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @PostMapping("/return")
    public ResponseEntity<Map<String, Seat>> refundTicket(@RequestBody Map<String, String> json) {
        if (!json.containsKey("token")) throw new InvalidSeatException("Wrong token!");
        UUID token = UUID.fromString(json.get("token"));
        Ticket ticket = ticketMap.getOrDefault(token, null);
        if (ticket == null) throw new InvalidSeatException("Wrong token!");
        Room.getInstance().getAvailableSeats().add(ticket.getSeat());
        ticketMap.remove(token);
        return new ResponseEntity<>(Map.of("returned_ticket", ticket.getSeat()), HttpStatus.OK);
    }


    /*
    CUSTOM EXCEPTION HANDLER
     */

    @ExceptionHandler(InvalidSeatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Map<String,Object> handleBadRequestException(InvalidSeatException exception) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("error", exception.getMessage());
        return result;
    }
}
