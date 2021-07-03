import java.util.Arrays;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    int[][] matrix = fillMatrixInSpiral(n, n*n);
    for(int i=0;i<n;i++){
      for(int j=0;j<n;j++){
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println();
    }
  }

  public static int[][] fillMatrixInSpiral(int size, int maximumValue){
    int[][] newMatrix = new int[size][size];

    int i = 0;
    int j = 0;
    int currentValue = 1;

    while(currentValue <= maximumValue){
      // right
      while(true){
        try{
          if(newMatrix[i][j] == 0){
            newMatrix[i][j] = currentValue++;
            j++;
          } else{
            j--;
            i++;
            break;
          }
        } catch(IndexOutOfBoundsException e){
          j--;
          i++;
          break;
        }
      }

      // down
      while(true){
        try{
          if(newMatrix[i][j] == 0){
            newMatrix[i][j] = currentValue++;
            i++;
          } else{
            i--;
            j--;
            break;
          }
        } catch(IndexOutOfBoundsException e){
          i--;
          j--;
          break;
        }
      }

      // left
      while(true){
        try{
          if(newMatrix[i][j] == 0){
            newMatrix[i][j] = currentValue++;
            j--;
          } else{
            j++;
            i--;
            break;
          }
        } catch(IndexOutOfBoundsException e){
          j++;
          i--;
          break;
        }
      }

      // up
      while(true){
        try{
          if(newMatrix[i][j] == 0){
            newMatrix[i][j] = currentValue++;
            i--;
          } else{
            j++;
            i++;
            break;
          }
        } catch(IndexOutOfBoundsException e){
          break;
        }
      }
    }
    return newMatrix;
  }
}