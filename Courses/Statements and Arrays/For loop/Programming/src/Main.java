import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);

    int a = sc.nextInt();
    int b = sc.nextInt();
    int result = 0;

    for(int c=a;c<=b;c++){
      result += c;
    }
    System.out.println(result);
  }
}