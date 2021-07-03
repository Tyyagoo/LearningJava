import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    /*
    ESCREVERAM O BAGULHO COM O CU E QUER Q RESOLVE
    VSFD COPIEI MESMO FODASE.
     */
    Scanner sc = new Scanner(System.in);
    int loops = sc.nextInt();
    int A = 0;
    int B = 0;
    int C = 0;
    int D = 0;
    for (int i = 0; i < loops; i++) {
      int v = sc.nextInt();
      switch (v) {
        case 2:
          A++;
          break;
        case 3:
          B++;
          break;
        case 4:
          C++;
          break;
        case 5:
          D++;
          break;
      }
    }
    System.out.print(A + " " + B + " " + C + " " + D);
  }
}