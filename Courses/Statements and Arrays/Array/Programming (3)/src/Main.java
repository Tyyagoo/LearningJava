import java.util.Arrays;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner sc = new Scanner(System.in);

    int arrayLength = sc.nextInt();
    int[] array = new int[arrayLength];

    for(int i=0;i<arrayLength;i++){
      array[i] = sc.nextInt();
    }

    array = shiftArray(array, arrayLength);
    for(int i=0;i<arrayLength;i++){
      System.out.print(array[i] + " ");
    }
  }

  public static int[] shiftArray(int[] initialArray, int arrayLength){
    int[] newArray = new int[arrayLength];
    for(int i=0;i<arrayLength;i++){
      if(i == arrayLength-1) break;
      else newArray[i+1] = initialArray[i];
    }
    newArray[0] = initialArray[arrayLength - 1];
    return newArray;
  }
}