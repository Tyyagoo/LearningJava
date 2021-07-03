import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner sc =  new Scanner(System.in);

    int sequenceLength = sc.nextInt();
    int result = 0;
    int input;
    for(int c=0;c<sequenceLength;c++){
      input = sc.nextInt();
      if(input%6 == 0) result += input;
    }
    System.out.println(result);
  }
}