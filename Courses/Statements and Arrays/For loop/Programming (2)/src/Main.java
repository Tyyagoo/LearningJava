import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    int input;
    int sequenceLength = sc.nextInt();
    int max = 0;
    if (sequenceLength < 30000) {
      for (int i = 0; i < sequenceLength; i++) {
        input = sc.nextInt();
        if (input % 4 == 0 && input < 1000) {
          if (max < input) {
            max = input;
          }
        }
      }
    }
    System.out.println(max);
  }
}