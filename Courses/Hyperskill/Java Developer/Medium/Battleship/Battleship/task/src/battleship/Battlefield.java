package battleship;

import battleship.Exceptions.ShipSankedException;
import battleship.Exceptions.ShotOnDestroyedShipException;
import battleship.Ships.AbstractShip;

import java.util.*;

public class Battlefield {

    public static final int xSize = 10;
    public static final int ySize = 10;

    enum Tiles {
        NONE('~', '~'),
        SHIP('~', 'O'),
        DESTROYED_SHIP('X', 'X'),
        MISSED_SHOOT('M', 'M');

        private final char withFogCharacter;
        private final char withoutFogCharacter;

        Tiles(char fog, char withoutFog) {
            this.withFogCharacter = fog;
            this.withoutFogCharacter = withoutFog;
        }

        public char getWithFogCharacter() {
            return withFogCharacter;
        }

        public char getWithoutFogCharacter() {
            return withoutFogCharacter;
        }
    }

    private Tiles[][] field = new Tiles[xSize][ySize];
    private Map<AbstractShip, List<Vector2>> aliveShips = new HashMap<>();

    Battlefield() {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                field[i][j] = Tiles.NONE;
            }
        }
    }

    public boolean checkSurroundings(int i, int j) {
        boolean isEmpty = true;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int c = 0; c < 4; c++) {
            try {
                if (field[i - directions[c][0]][j - directions[c][1]] == Tiles.SHIP) {
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
                if (field[begin.getX() - 1][i] == Tiles.SHIP) {
                    isEmpty = false;
                    break;
                }
                isEmpty = checkSurroundings(begin.getX() - 1, i);
                if (!isEmpty) break;
            }
        } else {
            for (int i = Math.min(begin.getX(), end.getX()) - 1; i < Math.max(begin.getX(), end.getX()); i++) {
                if (field[i][begin.getY() - 1] == Tiles.SHIP) {
                    isEmpty = false;
                    break;
                }
                isEmpty = checkSurroundings(i, begin.getY() - 1);
                if (!isEmpty) break;
            }
        }

        return isEmpty;
    }

    public boolean shootOnCoordinate(Vector2 coordinates) throws ShipSankedException, ShotOnDestroyedShipException {
        Tiles tileAtPosition = field[coordinates.getX() - 1][coordinates.getY() - 1];
        boolean rightShoot = false;
        if (tileAtPosition == Tiles.SHIP) {
            field[coordinates.getX() - 1][coordinates.getY() - 1] = Tiles.DESTROYED_SHIP;
            rightShoot = true;
            AbstractShip hittedShip = getShipAtCoordinate(coordinates);
            if (hittedShip != null) {
                boolean shipIsDestroyed = true;
                for (Vector2 v: aliveShips.getOrDefault(hittedShip, new ArrayList<Vector2>())) {
                    if (field[v.getX() - 1][v.getY() - 1] == Tiles.SHIP) {
                        shipIsDestroyed = false;
                    }
                }
                if (shipIsDestroyed) {
                    aliveShips.remove(hittedShip);
                    throw new ShipSankedException();
                }
            }
        } else if (tileAtPosition == Tiles.DESTROYED_SHIP) {
            throw new ShotOnDestroyedShipException();
        } else {
            field[coordinates.getX() - 1][coordinates.getY() - 1] = Tiles.MISSED_SHOOT;
        }

        return rightShoot;
    }

    public void placeShip(AbstractShip ship) {
        List<Vector2> listOfPositions = new ArrayList<>();
        Vector2 begin = ship.getPosition().getFrom();
        Vector2 end = ship.getPosition().getTo();
        if (begin.getX() == end.getX()) {
            for (int i = Math.min(begin.getY(), end.getY()) - 1; i < Math.max(begin.getY(), end.getY()); i++) {
                field[begin.getX() - 1][i] = Tiles.SHIP;

                listOfPositions.add(new Vector2(begin.getX(), i + 1));
            }
        } else {
            for (int i = Math.min(begin.getX(), end.getX()) - 1; i < Math.max(begin.getX(), end.getX()); i++) {
                field[i][begin.getY() - 1] = Tiles.SHIP;
                listOfPositions.add(new Vector2(i + 1, begin.getY()));
            }
        }

        aliveShips.put(ship, listOfPositions);
    }

    public void showField(boolean ...cancelFog) {

        System.out.print("  ");
        for (int c = 1; c <= 10; c++){
            System.out.print(c + " ");
        }
        System.out.println();

        char[] lettersArray = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        for (int i = 0; i < xSize; i++) {
            System.out.print(String.format("%s ", lettersArray[i]));
            for (int j = 0; j < ySize; j++) {
                if (cancelFog.length != 0) {
                    if (cancelFog[0]) {
                        System.out.print(field[i][j].getWithoutFogCharacter() + " ");
                        continue;
                    }
                }
                System.out.print(field[i][j].getWithFogCharacter() + " ");
            }
            System.out.println();
        }
    }

    public boolean isAllShipsDestroyed() {
        if (aliveShips.isEmpty()) return true;
        return false;
    }

    private AbstractShip getShipAtCoordinate(Vector2 coordinate) {
        for (Map.Entry<AbstractShip, List<Vector2>> entry: aliveShips.entrySet()) {
            for (Vector2 v: entry.getValue()) {
                if (Vector2.equals(coordinate, v)) return entry.getKey();
            }
        }
        return null;
    }
}
