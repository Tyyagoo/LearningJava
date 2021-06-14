import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner scanner = new Scanner(System.in);
    int h = scanner.nextInt();
    int a = scanner.nextInt();
    int b = scanner.nextInt();
    int currentHeight = 0;
    int days = 0;
    
    while(true){
      currentHeight += a;
      days++;
      if(currentHeight >= h) break;
      currentHeight -= b;
    }
    System.out.println(days);
  }
}