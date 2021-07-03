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

    int[][] rotatedMatrix = new int[m][n];
    for(int i=0;i<m;i++){
      for(int j=0;j<n;j++){
        rotatedMatrix[i][j] = matrix[(n-1)-j][i];
      }
    }

    for(int i=0;i<m;i++){
      for(int j=0;j<n;j++){
        System.out.print(rotatedMatrix[i][j] + " ");
      }
      System.out.println();
    }
  }
}