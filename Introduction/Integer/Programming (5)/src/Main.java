import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    int rest = n % 100;

    int c = n / 100;
    int d = rest / 10;
    int u = rest % 10;
    System.out.println(c+d+u);
  }
}