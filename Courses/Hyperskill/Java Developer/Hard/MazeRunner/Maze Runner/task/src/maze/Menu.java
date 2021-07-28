package maze;

import java.io.*;
import java.util.Scanner;

public class Menu {
    public static final String filepath = ".\\";
    private static final Scanner scanner = new Scanner(System.in);
    private static Maze currentMaze;
    private static boolean running = true;


    private static final ICommand generateMaze = () -> {
        System.out.println("Enter the size of a new maze:");
        int size = scanner.nextInt();
        scanner.nextLine();
        currentMaze = new Maze(size);
        currentMaze.generate();
        currentMaze.draw();
    };

    private static final ICommand loadMaze = () -> {
        System.out.println("Please enter the file name:");
        String filename = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath + filename))) {
            String mazeSize = reader.readLine();
            int size = Integer.parseInt(mazeSize);
            currentMaze = new Maze(size);
            int adjMatrixSize = (size % 2 != 0) ? (size - 2) / 2 + 1 : (size - 2) / 2;
            int[][] adjMatrix = new int[adjMatrixSize * adjMatrixSize][adjMatrixSize * adjMatrixSize];
            for (int i = 0; i < adjMatrix.length; i++) {
                String[] row = reader.readLine().split(" ");
                for (int j = 0; j < adjMatrix.length; j++) {
                    adjMatrix[i][j] = Integer.parseInt(row[j]);
                }
            }
            currentMaze.loadMaze(adjMatrix);
        } catch (FileNotFoundException e) {
            System.out.printf("The file %s does not exist.%n", filename);
        } catch (IOException | NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("Cannot load the maze. It has an invalid format.");
        }
    };

    private static final ICommand saveMaze = () -> {
        String filename = scanner.nextLine();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath + filename, false))) {
            String content = currentMaze.saveMaze();
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    private static final ICommand showMaze = () -> {
        currentMaze.draw();
    };

    private static final ICommand exit = () -> {
        running = false;
        System.out.println("Bye!");
    };

    public static void invoke() {
        System.out.println("=== Menu ===");
        System.out.println("1. Generate a new maze");
        System.out.println("2. Load a maze");

        if (currentMaze == null) {
            System.out.println("0. Exit");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    generateMaze.execute();
                    break;
                case 2:
                    loadMaze.execute();
                    break;
                case 0:
                    exit.execute();
                    break;
                default:
                    System.out.println("Incorrect option. Please try again");
                    break;
            }
        } else {
            System.out.println("3. Save the maze");
            System.out.println("4. Display the maze");
            System.out.println("0. Exit");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    generateMaze.execute();
                    break;
                case 2:
                    loadMaze.execute();
                    break;
                case 3:
                    saveMaze.execute();
                    break;
                case 4:
                    showMaze.execute();
                    break;
                case 0:
                    exit.execute();
                    break;
                default:
                    System.out.println("Incorrect option. Please try again");
                    break;
            }
        }
    }

    public static boolean isRunning() {
        return running;
    }
}
