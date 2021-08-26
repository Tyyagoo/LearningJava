package recognition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LearningData {
    final List<Double> input;
    final List<Double> output;

    public LearningData(String input, int index) {
        try (Scanner scanner = new Scanner(input)) {
            this.input = scanner.tokens()
                    .flatMap(s -> Arrays.stream(s.split("")))
                    .limit(15)
                    .map(s -> "X".equals(s) ? 1.0 : 0.0)
                    .collect(Collectors.toList());
        }

        this.output = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            this.output.add(index == i ? 1.0 : 0.0);
        }
    }

    public LearningData(List<Double> input, List<Double> output) {
        this.input = input;
        this.output = output;
    }
}
