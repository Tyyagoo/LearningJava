package cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Seat {

    @JsonProperty("row")
    private int rowNumber;

    @JsonProperty("column")
    private int columnNumber;

    @JsonProperty("price")
    private int price;

    public Seat () {}

    public Seat(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        updatePrice();
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public void updatePrice() {
        this.price = (rowNumber > 4) ? 8 : 10;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seat seat = (Seat) o;

        if (rowNumber != seat.rowNumber) return false;
        return columnNumber == seat.columnNumber;
    }

    @Override
    public int hashCode() {
        int result = rowNumber;
        result = 31 * result + columnNumber;
        return result;
    }
}
