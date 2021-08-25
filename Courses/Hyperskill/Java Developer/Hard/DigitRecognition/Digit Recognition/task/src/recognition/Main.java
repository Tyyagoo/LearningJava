package recognition;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input grid:");
        List<String> input = scanner.tokens()
                .flatMap(s -> Arrays.stream(s.split("")))
                .limit(9)
                .collect(Collectors.toList());

        int[] weights = {2, 1, 2, 4, -4, 4, 2, -1, 2, -5};
        int result = 0;

        for (int i = 0; i < input.size(); i++) {
            if ("X".equals(input.get(i))) {
                result += weights[i];
            }
        }

        result += weights[weights.length - 1];
        System.out.printf("This number is %d%n", result >=0 ? 0 : 1);
    }
}
