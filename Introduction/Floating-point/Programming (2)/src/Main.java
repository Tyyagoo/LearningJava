import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner scanner = new Scanner(System.in);
    double x  = scanner.nextDouble();
    double decimal = (x - (int) x);
    int digit = (int) (decimal*10);
    System.out.println(digit);
  }
}