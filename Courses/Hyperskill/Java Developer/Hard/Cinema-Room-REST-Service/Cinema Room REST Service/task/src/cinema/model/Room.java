package cinema.model;


import com.fasterxml.jackson.annotation.*;
import java.util.*;

public class Room {
    private static Room instance;

    private final int rows = 9;
    private final int columns = 9;
    private List<Seat> availableSeats;

    public Room() {
        availableSeats = new ArrayList<>(rows * columns);

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                availableSeats.add(new Seat(i, j));
            }
        }
    }

    @JsonGetter(value = "total_rows")
    public int getRows() {
        return rows;
    }

    @JsonGetter(value = "total_columns")
    public int getColumns() {
        return rows;
    }

    @JsonGetter(value = "available_seats")
    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public static Room getInstance() {
        if (instance == null) instance = new Room();
        return instance;
    }
}
