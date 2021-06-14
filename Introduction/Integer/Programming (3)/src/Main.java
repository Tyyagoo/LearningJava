
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner scanner = new Scanner(System.in);
    int number = scanner.nextInt();
    number = clamp_value(number);
    String ns = String.valueOf(number);

    if (ns.length() < 2) {
      System.out.println(0);
    } else{
      char ten = ns.charAt(ns.length() - 2);
      System.out.println(Integer.parseInt(String.valueOf(ten)));
    }
  }

  public static int clamp_value(int value){
    // 0 ≤ N ≤ 1000000
    if(value<=0) return 0;
    if(value>=1000000) return 1000000;
    return value;
  }
}