import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);

    int a = sc.nextInt();
    int b = sc.nextInt();

    int quantityOfNumbers = 0;
    double sum = 0;
    double average = 0;
    for(int currentNumber=a;currentNumber<=b; currentNumber++){
      if(currentNumber%3 == 0){
        sum += currentNumber;
        quantityOfNumbers++;
      }
    }
    average = sum / quantityOfNumbers;
    System.out.println(average);
  }
}