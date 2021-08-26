package recognition;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {
    private static final LearningData[] bigData = {
            new LearningData("XXX\nX_X\nX_X\nX_X\nXXX", 9),
            new LearningData("_X_\n_X_\n_X_\n_X_\n_X_", 0),
            new LearningData("XXX\n__X\nXXX\nX__\nXXX", 1),
            new LearningData("XXX\n__X\nXXX\n__X\nXXX", 2),
            new LearningData("X_X\nX_X\nXXX\n__X\n__X", 3),
            new LearningData("XXX\nX__\nXXX\n__X\nXXX", 4),
            new LearningData("XXX\nX__\nXXX\nX_X\nXXX", 5),
            new LearningData("XXX\n__X\n__X\n__X\n__X", 6),
            new LearningData("XXX\nX_X\nXXX\nX_X\nXXX", 7),
            new LearningData("XXX\nX_X\nXXX\n__X\nXXX", 8),
    };

    private static NeuralNetwork network;
    private static final Scanner scanner = new Scanner(System.in);

    public static final Command trainNetwork = () -> {
        System.out.println("Learning...");
        for (int i = 0; i < 500_000; i++) {
            int sampleIndex = (int) (Math.random() * bigData.length);
            LearningData sample = bigData[sampleIndex];
            network.train(sample.input, sample.output);
        }

        try (FileOutputStream fos = new FileOutputStream("network.txt")) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(network);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done! Saved to the file.");
    };

    public static final Command guessNumber = () -> {
        System.out.println("Input grid:");
        List<Integer> result = network.predict(
                scanner.tokens()
                        .flatMap(s -> Arrays.stream(s.split("")))
                        .limit(15)
                        .map(s -> "X".equals(s) ? 1.0 : 0.0)
                        .collect(Collectors.toList()))
                .stream()
                .map(Double::intValue)
                .collect(Collectors.toList());

        System.out.println(result);

        System.out.printf("This number is %s%n", result.stream().reduce(Integer::max));
    };

    public static void initialize() {
        File file = new File("network.txt");
        if (file.isFile()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                ObjectInputStream ois = new ObjectInputStream(fis);
                network = (NeuralNetwork) ois.readObject();
                System.out.println("Loaded the network from file.");
                return;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Created a new network.");
        network = new NeuralNetwork(15, 50, 10);

        for (int i = 0; i < bigData.length; i++) {
            System.out.println(bigData[i].input);
            System.out.println(bigData[i].output);
        }
    }

    public static void invoke() {
        System.out.println("1. Learn the network");
        System.out.println("2. Guess a number");
        System.out.print("Your choice: ");
        int option = Integer.parseInt(scanner.nextLine());

        if (option == 1) {
            trainNetwork.execute();
        } else {
            guessNumber.execute();
        }
    }
}
