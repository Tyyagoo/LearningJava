import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner scanner = new Scanner(System.in);
    double a = scanner.nextDouble();
    double b = scanner.nextDouble();
    double c = scanner.nextDouble();
    
    double delta = Math.pow(b, 2) - (4 * a * c);
    double x1 = ((-b) + (Math.sqrt(delta))) / (2*a);
    double x2 = ((-b) - (Math.sqrt(delta))) / (2*a);
    
    if(x1>x2){
      System.out.println(x2);
      System.out.println(x1);
    } else{
      System.out.println(x1);
      System.out.println(x2);
    }
  }
}