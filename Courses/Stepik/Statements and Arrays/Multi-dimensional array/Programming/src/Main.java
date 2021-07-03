import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    int[][] array2D = new int[n][n];

    for(int a=0;a<n;a++){
      for(int i=0;i<n;i++){
        array2D[a][i] = sc.nextInt();
      }
    }

    boolean isSymmetric = true;
    for(int a=0;a<n;a++){
      for(int b=0;b<n;b++){
        if(array2D[a][b] != array2D[b][a]){
          isSymmetric = false;
          break;
        }
      }
    }

    System.out.println((isSymmetric)? "YES" : "NO");

  }
}