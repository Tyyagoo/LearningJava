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


    if(arrayLength > 1){
      int firstNumber = 0;
      int secondNumber = 0;
      for(int i=0;i<arrayLength;i++){
        if(array[i] > firstNumber){
          int tmp = firstNumber;
          firstNumber = array[i];
          if(tmp > secondNumber) secondNumber = tmp;
          continue;
        }

        if(array[i] > secondNumber){
          secondNumber = array[i];
          continue;
        }
      }

      System.out.println(firstNumber * secondNumber);

    } else {
      System.out.println(array[0]);
    }
  }
}