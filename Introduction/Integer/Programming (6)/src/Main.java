import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    
    Scanner scanner = new Scanner(System.in);
    int number = scanner.nextInt();
    String ns = String.valueOf(number);
    System.out.println(new StringBuilder(ns).reverse().toString());
  }
}