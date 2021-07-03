import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    String string = sc.nextLine();
    char[] chars = string.toCharArray();
    String reversedString = String.valueOf(reverseCharArray(chars));

    System.out.println((string.equalsIgnoreCase(reversedString))? "yes" : "no");
  }

  public static char[] reverseCharArray(char[] initialArray){
    char[] newArray = new char[initialArray.length];
    int index = initialArray.length - 1;
    for (char ch: initialArray){
      newArray[index] = ch;
      index--;
    }
    return newArray;
  }
}