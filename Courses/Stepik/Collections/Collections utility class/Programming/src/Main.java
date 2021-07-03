import java.util.*;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    List<String> stringList = Arrays.asList(sc.nextLine().split(" "));

    int numberOfSwaps = sc.nextInt();

    for(int c = 0 ; c < numberOfSwaps ; c++){
      int firstIndex = sc.nextInt();
      int secondIndex = sc.nextInt();

      Collections.swap(stringList, firstIndex, secondIndex);
    }

    for(String str: stringList){
      System.out.print(str + " ");
    }
  }
}