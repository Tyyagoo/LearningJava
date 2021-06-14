import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    int lastTwoDigits = n % 100;
    System.out.println((lastTwoDigits%10)^(lastTwoDigits/10));
  }
}