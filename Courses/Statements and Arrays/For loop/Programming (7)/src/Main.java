import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    int numberOfReady = 0;
    int numberOfFixes = 0;
    int numberOfRejects = 0;

    int numberOfParts = sc.nextInt();

    for(int cont=0;cont<numberOfParts;cont++){
      switch(sc.nextInt()){
        case -1:
          numberOfRejects++;
          break;
        case 0:
          numberOfReady++;
          break;
        case 1:
          numberOfFixes++;
          break;
        default:
          break;
      }
    }

    System.out.println(numberOfReady+" "+numberOfFixes+" "+numberOfRejects);
  }
}