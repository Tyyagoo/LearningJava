import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    int m = sc.nextInt();
    if(n>100 || m>100) return;

    int[][] matrix = new int[n][m];

    for(int i=0;i<n;i++){
      for(int j=0;j<m;j++){
        matrix[i][j] = sc.nextInt();
      }
    }

    int c1 = sc.nextInt();
    int c2 = sc.nextInt();

    for(int i=0;i<n;i++){
      int tmp = matrix[i][c1];
      matrix[i][c1] = matrix[i][c2];
      matrix[i][c2] = tmp;
    }

    for(int i=0;i<n;i++){
      for(int j=0;j<m;j++){
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println();
    }
  }
}