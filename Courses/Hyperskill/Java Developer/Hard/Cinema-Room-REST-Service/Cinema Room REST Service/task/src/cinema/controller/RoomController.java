package cinema.controller;

import cinema.model.Room;
import cinema.model.Seat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {

    @GetMapping("/seats")
    public ResponseEntity<Room> getRoomInfo() {
        return new ResponseEntity<>(Room.getInstance(), HttpStatus.OK);
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody Seat seat) {
        int x = Room.getInstance().getRows();
        int y = Room.getInstance().getColumns();
        if (seat.getRowNumber() < 1 || seat.getRowNumber() > x ||
                seat.getColumnNumber() < 1 || seat.getColumnNumber() > y) {
            return new ResponseEntity<>("The number of a row or a column is out of bounds!", HttpStatus.BAD_REQUEST);
        }

        if (!Room.getInstance().getAvailableSeats().contains(seat)) {
            return new ResponseEntity<>("The ticket has been already purchased!", HttpStatus.BAD_REQUEST);
        }
        Room.getInstance().getAvailableSeats().remove(seat);
        seat.updatePrice();
        return new ResponseEntity<>(seat, HttpStatus.OK);
    }
}
