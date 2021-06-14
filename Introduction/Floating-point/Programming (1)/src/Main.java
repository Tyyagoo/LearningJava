import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner scanner = new Scanner(System.in);
    float x = scanner.nextFloat();
    int integer_part = (int) x;
    float decimal = x - integer_part;
    System.out.println(decimal);
  }
}