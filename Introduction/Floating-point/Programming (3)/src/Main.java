import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner scanner = new Scanner(System.in);
    double number = scanner.nextDouble();
    double result = Math.pow(number, 3) + Math.pow(number, 2) + number + 1;
    System.out.println(result);
  }
}