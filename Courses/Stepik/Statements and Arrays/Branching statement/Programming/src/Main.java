import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    while(true){
      int input = sc.nextInt();
      if(input==0) break;
      if(input%2==0){
        System.out.println("even");
        continue;
      }
      System.out.println("odd");
    }
  }
}