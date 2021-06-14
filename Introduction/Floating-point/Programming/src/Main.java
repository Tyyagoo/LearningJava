import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner scanner = new Scanner(System.in);
    float distance = scanner.nextFloat();
    float hours = scanner.nextFloat();
    System.out.println(distance/hours);
  }
}