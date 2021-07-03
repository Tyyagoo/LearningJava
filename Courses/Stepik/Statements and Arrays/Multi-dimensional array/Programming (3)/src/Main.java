import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();

    if(n>100) return;

    int[][] matrix = new int[n][n];

    for(int i=0;i<n;i++){
      for(int j=0;j<n;j++){
        matrix[i][j] = Math.abs(i-j);
      }
    }

    for(int i=0;i<n;i++){
      for(int j=0;j<n;j++){
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println();
    }
  }
}