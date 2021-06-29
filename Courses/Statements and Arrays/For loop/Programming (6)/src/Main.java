import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    int rangeMin = sc.nextInt();
    int rangeMax = sc.nextInt();

    for(int currentNumber=rangeMin;currentNumber<=rangeMax;currentNumber++){
      if(currentNumber%3==0) System.out.print("Fizz");
      if(currentNumber%5==0) System.out.print("Buzz");
      if(currentNumber%3!=0 && currentNumber%5!=0) System.out.print(currentNumber);
      System.out.println();
    }
  }
}