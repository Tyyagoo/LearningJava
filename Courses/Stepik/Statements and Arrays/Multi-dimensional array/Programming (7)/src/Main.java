import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int m = sc.nextInt();

    if(n>20 || m>20) return;

    int[][] matrix = new int[n][m];

    for(int i=0;i<n;i++){
      for(int j=0;j<m;j++){
        matrix[i][j] = sc.nextInt();
      }
    }

    int k = sc.nextInt();
    int numberOfSpots = 0;
    int rowNumber = 0;
    boolean forceBreak = false;

    for(int i=0;i<n;i++){
      if(forceBreak) break;

      numberOfSpots = 0;
      for(int j=0;j<m;j++){
        if(matrix[i][j] == 0) {
          numberOfSpots++;
          if (numberOfSpots >= k){
            rowNumber = i+1;
            forceBreak = true;
            break;
          }
        }
        else numberOfSpots = 0;
      }
    }

    System.out.println(rowNumber);
  }
}