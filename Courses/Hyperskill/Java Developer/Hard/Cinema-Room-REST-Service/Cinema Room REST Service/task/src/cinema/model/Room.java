package cinema.model;


import com.fasterxml.jackson.annotation.*;
import java.util.*;

public class Room {
    private final int rows = 9;
    private final int columns = 9;
    private Seat[][] seats;

    public Room() {
        seats = new Seat[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                seats[i][j] = Seat.EMPTY;
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
    public List<Map<String, Integer>> getAvailableSeats() {
        List<Map<String, Integer>> availableSeats = new LinkedList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (seats[i][j] == Seat.EMPTY) {
                    availableSeats.add(Map.of("row", i + 1, "column", j + 1));
                }
            }
        }
        return availableSeats;
    }
}
