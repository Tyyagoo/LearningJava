package battleship;

public class Main {

    public static void main(String[] args) {
        // Write your code here
        Battlefield battlefield = UserInterface.setupBattlefield();
        UserInterface.startGame(battlefield);
    }
}
