import java.util.*;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner sc = new Scanner(System.in);
    ArrayList<Integer> integerList = new ArrayList<>();
    String[] inputLine;
    // Fill ArrayList
    try{
      inputLine = sc.nextLine().split(" ");
    } catch(NoSuchElementException e) {
      return;
    }

    int index = 0;
    for(String str: inputLine){
      if(index++ % 2 != 0) integerList.add(Integer.parseInt(str));
    }

    // Print in reverse order
    Collections.reverse(integerList);
    for(Integer intObj: integerList){
      System.out.print(intObj.intValue() + " ");
    }
  }
}