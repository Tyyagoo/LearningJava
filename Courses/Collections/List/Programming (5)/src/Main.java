import java.io.IOException;
import java.util.*;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    String[]  inputLineArray;
    try{
      inputLineArray = sc.nextLine().split(" ");
    } catch(NoSuchElementException e){
      return;
    }

    List<String> list = Arrays.asList(inputLineArray);
    System.out.println(list);
  }
}