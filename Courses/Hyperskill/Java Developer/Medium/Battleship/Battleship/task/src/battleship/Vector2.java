package battleship;

public class Vector2 {

    private int x;
    private int y;

    public static Vector2 getVectorByCoordinates(String coordinates) {
        int number = Integer.parseInt(coordinates.substring(1));
        int letter = (int) coordinates.charAt(0) - 64;

        if (number > 10 || number < 1) throw new IllegalArgumentException();
        if (letter > 10 || letter < 1) throw new IllegalArgumentException();

        return new Vector2(letter, number);
    }

    Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getMagnitude(Vector2 to) {
        int magnitude = 0;

        if (x == to.getX()) {
            magnitude = 1 + Math.abs(y - to.getY());
        }
        else if (y == to.getY()) {
            magnitude = 1 + Math.abs(x - to.getX());
        }

        else {
            throw new IllegalArgumentException();
        }

        return magnitude;
    }

    public void setX(int x) {
        if (x > 10) return;
        this.x = x;
    }

    public void setY(int y) {
        if (y > 10) return;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
