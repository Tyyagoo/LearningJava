
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner scanner = new Scanner(System.in);

    int n = scanner.nextInt();
    int k = scanner.nextInt();
    n = clamp_value(n);
    k = clamp_value(k);
    int quantity = k / n;
    int trash = k % n;
    System.out.println(trash);
  }

  public static int clamp_value(int value){
    if(value <= 0){
      return 0;
    }
    else if(value >= 10000){
      return 10000;
    }
    else{
      return value;
    }
  }
}