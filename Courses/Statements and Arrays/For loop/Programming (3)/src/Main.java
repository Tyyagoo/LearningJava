import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);

    int a = sc.nextInt();
    int b = sc.nextInt();
    int c = sc.nextInt();
    int d = sc.nextInt();

    for(int x=0;x<=1000;x++){
      if((a*x*x*x) + (b*x*x) + (c*x) + d==0) System.out.println(x);
    }
  }
}