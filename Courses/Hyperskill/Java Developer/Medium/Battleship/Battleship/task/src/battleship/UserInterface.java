package battleship;

import battleship.Exceptions.*;
import battleship.Ships.AbstractShip;

import java.util.*;

public class UserInterface {

    private static Scanner scanner = new Scanner(System.in);



    public static Battlefield setupBattlefield() {
        Battlefield battlefield = new Battlefield();
        String[] namesOfShips = {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};

        for(String shipName: namesOfShips) {
            battlefield.showField(true);
            askToPlaceShip(shipName, battlefield);
        }
        battlefield.showField(true);
        return battlefield;
    }

    public static void startGame(Battlefield battlefield) {
        System.out.println("The game starts!");
        battlefield.showField();
        System.out.println("Take a shot!");

        while (!battlefield.isAllShipsDestroyed()) {
            makeTurn(battlefield);
        }
    }

    public static void makeTurn(Battlefield battlefield) {
        String inputLine = getInputCoordinates();
        Vector2 shootCoordinate;
        while(true){
            try {
                shootCoordinate = Vector2.getVectorByCoordinates(inputLine);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                inputLine = scanner.nextLine();
            }
        }
        boolean shootResult;
        try {
            shootResult = battlefield.shootOnCoordinate(shootCoordinate);
            battlefield.showField();
            System.out.println((shootResult) ? "You hit a ship! Try again:" : "You missed. Try again:");
        } catch (ShipSankedException e) {
            battlefield.showField();
            if (battlefield.isAllShipsDestroyed()) {
                System.out.println("You sank the last ship. You won. Congratulations!");
            } else {
                System.out.println(e.getMessage());
            }
        } catch (ShotOnDestroyedShipException e) {
            battlefield.showField();
        }
    }

    private static String getInputCoordinates() {
        return scanner.nextLine();
    }

    private static String[] getInputPosition() {
        return scanner.nextLine().split(" ");
    }

    private static void askToPlaceShip(String shipName, Battlefield battlefield) {

        System.out.printf("Enter the coordinates of the %s (%d cells):%n", shipName, ShipFactory.getShipSizeByName(shipName));
        System.out.println();

        while(true) {
            try {
                String[] inputLine = getInputPosition();
                askToPlaceShip(shipName, inputLine, battlefield);
                break;
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void askToPlaceShip(String shipName, String[] coordinates, Battlefield battlefield)
            throws InvalidShipLocationException, InvalidShipSizeException, TooCloseShipsException, InvalidShipException{
        Vector2 startPosition = Vector2.getVectorByCoordinates(coordinates[0]);
        Vector2 endPosition = Vector2.getVectorByCoordinates(coordinates[1]);

        try {
            int magnitude = startPosition.getMagnitude(endPosition);
            if (magnitude == ShipFactory.getShipSizeByName(shipName)) {
                if (battlefield.scanForEmptyPosition(startPosition, endPosition)) {
                    AbstractShip ship = ShipFactory.makeShip(shipName, startPosition, endPosition);
                    battlefield.placeShip(ship);
                    return;
                } else {
                    throw new TooCloseShipsException();
                }
            } else {
                throw new InvalidShipSizeException(shipName);
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidShipLocationException();
        }
    }

}
