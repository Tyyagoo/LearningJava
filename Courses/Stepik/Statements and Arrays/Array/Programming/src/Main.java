import java.util.Arrays;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner sc = new Scanner(System.in);

    int arrayLength = sc.nextInt();
    int[] arrayOfIntegers = new int[arrayLength];

    for(int i=0;i<arrayLength;i++){
      arrayOfIntegers[i] = sc.nextInt();
    }

    int n = sc.nextInt();
    int m = sc.nextInt();
    boolean stopped = false;
    for(int i=0;i<arrayLength;i++){
      int previousIndex = i - 1;
      int nextIndex = i + 1;

      if(arrayOfIntegers[i] == n){
        // search for m
        if(previousIndex >= 0){
          if (arrayOfIntegers[previousIndex] == m){
            stopped = true;
            break;
          }
        }

        if(nextIndex < arrayLength){
          if (arrayOfIntegers[nextIndex] == m){
            stopped = true;
            break;
          }
        }
      }
      if(arrayOfIntegers[i] == m){
        // search for n
        if(previousIndex >= 0){
          if (arrayOfIntegers[previousIndex] == n){
            stopped = true;
            break;
          }
        }

        if(nextIndex < arrayLength){
          if (arrayOfIntegers[nextIndex] == n){
            stopped = true;
            break;
          }
        }
      }
      continue;
    }

    System.out.println(stopped);
  }
}