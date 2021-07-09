package battleship;

public class Main {

    public static void main(String[] args) {
        // Write your code here
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        UserInterface.startGame(player1, player2);
    }
}
