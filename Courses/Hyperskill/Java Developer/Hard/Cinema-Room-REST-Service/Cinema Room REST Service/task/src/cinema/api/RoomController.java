package cinema.api;

import cinema.model.Room;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoomController {
    Room room = new Room();

    @GetMapping("/seats")
    public Room getRoom() {
        return room;
    }
}
