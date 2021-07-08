package battleship;

import battleship.Ships.AbstractShip;

public class Battlefield {

    public static final int xSize = 10;
    public static final int ySize = 10;

    private char[][] field = new char[xSize][ySize];

    Battlefield() {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                field[i][j] = '~';
            }
        }
    }

    public boolean checkSurroundings(int i, int j) {
        boolean isEmpty = true;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int c = 0; c < 4; c++) {
            try {
                if (field[i - directions[c][0]][j - directions[c][1]] != '~') {
                    isEmpty = false;
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
        }
        return isEmpty;
    }

    public boolean scanForEmptyPosition(Vector2 begin, Vector2 end) {
        int magnitude = begin.getMagnitude(end);
        boolean isEmpty = true;

        if (begin.getX() == end.getX()) {
            for (int i = Math.min(begin.getY(), end.getY()) - 1; i < Math.max(begin.getY(), end.getY()); i++) {
                if (field[begin.getX() - 1][i] != '~') {
                    isEmpty = false;
                    break;
                }
                isEmpty = checkSurroundings(begin.getX() - 1, i);
                if (!isEmpty) break;
            }
        } else {
            for (int i = Math.min(begin.getX(), end.getX()) - 1; i < Math.max(begin.getX(), end.getX()); i++) {
                if (field[i][begin.getY() - 1] != '~') {
                    isEmpty = false;
                    break;
                }
                isEmpty = checkSurroundings(i, begin.getY() - 1);
                if (!isEmpty) break;
            }
        }

        return isEmpty;
    }

    public void placeShip(AbstractShip ship) {
        Vector2 begin = ship.getPosition().getFrom();
        Vector2 end = ship.getPosition().getTo();
        if (begin.getX() == end.getX()) {
            for (int i = Math.min(begin.getY(), end.getY()) - 1; i < Math.max(begin.getY(), end.getY()); i++) {
                field[begin.getX() - 1][i] = 'O';
            }
        } else {
            for (int i = Math.min(begin.getX(), end.getX()) - 1; i < Math.max(begin.getX(), end.getX()); i++) {
                field[i][begin.getY() - 1] = 'O';
            }
        }
    }

    public void showField() {

        System.out.print("  ");
        for (int c = 1; c <= 10; c++){
            System.out.print(c + " ");
        }
        System.out.println();

        char[] lettersArray = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        for (int i = 0; i < xSize; i++) {
            System.out.print(String.format("%s ", lettersArray[i]));
            for (int j = 0; j < ySize; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }
}
