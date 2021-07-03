import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    int m = sc.nextInt();
    if(n>100 || m>100) return;

    int[][] array2D = new int[n][m];

    for(int a=0;a<n;a++){
      for(int b=0;b<m;b++){
        array2D[a][b] = sc.nextInt();
      }
    }

    int biggestNumber = Integer.MIN_VALUE;
    int[] numberPosition = new int[2];
    for(int a=0;a<n;a++){
      for(int b=0;b<m;b++){
        if(array2D[a][b] > biggestNumber){
          biggestNumber = array2D[a][b];
          numberPosition[0] = a;
          numberPosition[1] = b;
        }
      }
    }

    System.out.println(numberPosition[0] + " " + numberPosition[1]);
  }
}