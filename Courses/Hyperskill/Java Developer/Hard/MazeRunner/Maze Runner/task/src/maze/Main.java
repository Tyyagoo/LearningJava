package maze;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter the size of a maze");
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        Maze maze = new Maze(n, m);
        maze.generate();
        maze.draw();
    }
}
