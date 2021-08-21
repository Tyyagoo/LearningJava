import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static int searchIndexOfLastOccurrence(int[] numbers, int value) {
        // write your code here
        int index = -1;
        int len = numbers.length;
        for (int i = len - 1; i >= 0; i--) {
            if (numbers[i] == value) {
                index = i;
                break;
            }
        }
        return index;
    }

    /* Do not change code below */
    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int[] numbers;
        final int k;
        if (scanner.hasNextInt()) {
            numbers = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            k = Integer.parseInt(scanner.nextLine());
        } else {
            numbers = new int[0];
            k = 10;
        }
        System.out.println(searchIndexOfLastOccurrence(numbers, k));
    }
}