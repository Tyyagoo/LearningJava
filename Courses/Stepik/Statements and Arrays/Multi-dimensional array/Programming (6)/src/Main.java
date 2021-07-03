import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();

    if(n%2 == 0 || n>15) return;

    String[][] matrix = new String[n][n];

    for(int i=0;i<n;i++){
      for(int j=0;j<n;j++){
        if(i==j){
          // fill the main diagonal
          matrix[i][j] = "*";
          continue;
        }
        if(i == n/2 || j == n/2){
          matrix[i][j] = "*";
          continue;
        }

        if(i+j+1 == n){
          matrix[i][j] = "*";
          continue;
        }

        matrix[i][j] = ".";
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