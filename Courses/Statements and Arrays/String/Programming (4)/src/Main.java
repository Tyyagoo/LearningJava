import java.util.Locale;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner sc = new Scanner(System.in);
    String string = sc.nextLine().toLowerCase(Locale.ROOT);
    System.out.println(compressString(string));
  }

  public static String compressString(String str){
    char[] initialArray = str.toCharArray();

    char lastCharacter = initialArray[0];
    int encounters = 1;
    StringBuilder encoder = new StringBuilder();
    for(int i=1;i<initialArray.length;i++){
      if(lastCharacter == initialArray[i]){
        encounters++;
        continue;
      }
      encoder.append(lastCharacter);
      encoder.append(encounters);
      lastCharacter = initialArray[i];
      encounters = 1;

    }
    encoder.append(lastCharacter);
    encoder.append(encounters);

    return encoder.toString();
  }
}