package maze;

public class Main {

    enum Cell{
        EMPTY(' '),
        WALL('\u2588');

        private final char graphic;

        Cell(char graphic) {
            this.graphic = graphic;
        }

        public void draw() {
            System.out.printf("%s%s", graphic, graphic);
        }
    }

    public static void main(String[] args) {

        Cell[][] maze = {
                {Cell.WALL,Cell.WALL,Cell.WALL,Cell.WALL,Cell.WALL,Cell.WALL,Cell.WALL,Cell.WALL,Cell.WALL,Cell.WALL},
                {Cell.EMPTY,Cell.EMPTY,Cell.WALL,Cell.EMPTY,Cell.WALL,Cell.EMPTY,Cell.WALL,Cell.EMPTY,Cell.EMPTY,Cell.WALL},
                {Cell.WALL,Cell.EMPTY,Cell.WALL,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.WALL,Cell.EMPTY,Cell.WALL,Cell.WALL},
                {Cell.WALL,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.WALL,Cell.WALL,Cell.WALL,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY},
                {Cell.WALL,Cell.EMPTY,Cell.WALL,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.WALL,Cell.WALL},
                {Cell.WALL,Cell.EMPTY,Cell.WALL,Cell.EMPTY,Cell.WALL,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.WALL,Cell.WALL},
                {Cell.WALL,Cell.EMPTY,Cell.WALL,Cell.EMPTY,Cell.WALL,Cell.WALL,Cell.WALL,Cell.EMPTY,Cell.WALL,Cell.WALL},
                {Cell.WALL,Cell.EMPTY,Cell.WALL,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.WALL,Cell.EMPTY,Cell.EMPTY,Cell.WALL},
                {Cell.WALL,Cell.EMPTY,Cell.WALL,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.WALL,Cell.EMPTY,Cell.EMPTY,Cell.WALL},
                {Cell.WALL,Cell.WALL,Cell.WALL,Cell.WALL,Cell.WALL,Cell.WALL,Cell.WALL,Cell.WALL,Cell.WALL,Cell.WALL}};

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                maze[i][j].draw();
            }
            System.out.println();
        }
    }
}
