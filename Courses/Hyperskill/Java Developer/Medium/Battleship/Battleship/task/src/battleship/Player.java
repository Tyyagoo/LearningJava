package battleship;

public class Player {

    public final String name;
    private Battlefield battlefield = new Battlefield();

    Player(String name) {
        this.name = name;
    }

    public void buildBattlefield() {
        String[] namesOfShips = {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};

        for(String shipName: namesOfShips) {
            battlefield.showField(true);
            UserInterface.placeShipOnBattlefield(shipName, battlefield);
        }
        battlefield.showField(true);
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }
}
