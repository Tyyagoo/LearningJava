package battleship;

import battleship.Exceptions.*;
import battleship.Ships.AbstractShip;

import java.io.IOException;
import java.util.*;

public class UserInterface {

    private static Scanner scanner = new Scanner(System.in);


    public static void startGame(Player player1, Player player2) {
        setupPlayerBattlefield(player1);
        setupPlayerBattlefield(player2);
        //System.out.println("The game starts!");

        while (true) {
            // player 1 turn
            makeTurn(player1, player2);
            if (player2.getBattlefield().isAllShipsDestroyed()) break;
            makeTurn(player2, player1);
            if (player1.getBattlefield().isAllShipsDestroyed()) break;
        }
    }

    public static void setupPlayerBattlefield(Player player) {
        System.out.printf("%s, place your ships on the game field%n", player.name);
        player.buildBattlefield();
        promptEnterKey();
    }

    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n".repeat(99));
    }

    public static void makeTurn(Player player, Player enemy) {
        enemy.getBattlefield().showField();
        System.out.println("---------------------");
        player.getBattlefield().showField(true);
        System.out.printf("%s, it's your turn:%n", player.name);
        makeTurn(enemy.getBattlefield());
    }

    public static void makeTurn(Battlefield battlefield) {
        String inputLine = getInputCoordinates();
        Vector2 shootCoordinate;
        try {
            shootCoordinate = Vector2.getVectorByCoordinates(inputLine);
        } catch (IllegalArgumentException e) {
            promptEnterKey();
            return;
        }

        boolean shootResult;
        try {
            shootResult = battlefield.shootOnCoordinate(shootCoordinate);
            //battlefield.showField();
            System.out.println((shootResult) ? "You hit a ship!" : "You missed!");
        } catch (ShipSankedException e) {
            // battlefield.showField();
            if (battlefield.isAllShipsDestroyed()) {
                System.out.println("You sank the last ship. You won. Congratulations!");
            } else {
                System.out.println(e.getMessage());
            }
        } catch (ShotOnDestroyedShipException ignored) {
            //battlefield.showField();
        }

        promptEnterKey();
    }

    private static String getInputCoordinates() {
        return scanner.nextLine();
    }

    private static String[] getInputPosition() {
        return scanner.nextLine().split(" ");
    }

    public static void placeShipOnBattlefield(String shipName, Battlefield battlefield) {
        askToPlaceShip(shipName, battlefield);
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
