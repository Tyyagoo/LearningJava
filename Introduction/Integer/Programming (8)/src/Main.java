import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner scanner = new Scanner(System.in);
    int number = scanner.nextInt();
    
    if(number%2 == 0){
      System.out.println(number+2);
    } else{
      System.out.println(++number);
    }
  }
}