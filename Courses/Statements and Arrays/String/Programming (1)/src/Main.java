import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner sc = new Scanner(System.in);
    String string = sc.nextLine();

    String[] splittedString = string.split(" ");

    int biggestStringIndex = 0;

    for(int i=1;i< splittedString.length;i++){
      if(splittedString[i].length() > splittedString[biggestStringIndex].length()) biggestStringIndex = i;
    }

    System.out.println(splittedString[biggestStringIndex]);
  }
}