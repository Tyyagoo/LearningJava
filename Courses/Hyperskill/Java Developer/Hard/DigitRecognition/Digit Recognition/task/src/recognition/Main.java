package recognition;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input grid:");
        List<Integer> input = scanner.tokens()
                .flatMap(s -> Arrays.stream(s.split("")))
                .limit(15)
                .map(s -> "X".equals(s) ? 1 : 0)
                .collect(Collectors.toList());

        int[][] weights = {
                {1, 1, 1, 1, -1, 1, 1, -1, 1, 1, -1, 1, 1, 1, 1},
                {-1, 1, -1, -1, 1, -1, -1, 1, -1, -1, 1, -1, -1, 1, -1},
                {1, 1, 1, -1, -1, 1, 1, 1, 1, 1, -1, -1, 1, 1, 1},
                {1, 1, 1, -1, -1, 1, 1, 1, 1, -1, -1, 1, 1, 1, 1},
                {1, -1, 1, 1, -1, 1, 1, 1, 1, -1, -1, 1, -1, -1, 1},
                {1, 1, 1, 1, -1, -1, 1, 1, 1, -1, -1, 1, 1, 1, 1},
                {1, 1, 1, 1, -1, -1, 1, 1, 1, 1, -1, 1, 1, 1, 1},
                {1, 1, 1, -1, -1, 1, -1, -1, 1, -1, -1, 1, -1, -1, 1},
                {1, 1, 1, 1, -1, 1, 1, 1, 1, 1, -1, 1, 1, 1, 1},
                {1, 1, 1, 1, -1, 1, 1, 1, 1, -1, -1, 1, 1, 1, 1}};

        int[] biases = {-1, 6, 1, 0, 2, 0, -1, 3, -2, -1};

        int[] output = new int[10];

        for (int i = 0; i < output.length; i++) {
            output[i] = 0;
            for (int j = 0; j < weights[0].length; j++) {
                output[i] += (input.get(j) * weights[i][j]);
            }
            output[i] += biases[i];
        }

        int biggestProbability = Integer.MIN_VALUE;
        int number = Integer.MIN_VALUE;

        for (int i = 0; i < output.length; i++) {
            if (output[i] >= biggestProbability) {
                biggestProbability = output[i];
                number = i;
            }
        }

        System.out.printf("This number is %d%n", number);
    }
}
