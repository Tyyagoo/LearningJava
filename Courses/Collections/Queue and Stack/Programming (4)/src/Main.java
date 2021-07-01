import java.io.IOException;
import java.util.*;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    String[] inputLine = sc.nextLine().split("");

    // rule 1
    Deque<String> stack = new ArrayDeque<>();

    // helpers
    List<String> startingBrackets = Arrays.asList("(", "[", "{");
    List<String> endingBrackets = Arrays.asList(")", "]", "}");

    boolean isBalanced = true;
    String lastPop;

    // rule 2
    for(String str: inputLine){
      if(startingBrackets.contains(str)){
        stack.push(str);
      } else if(endingBrackets.contains(str)){

        if(stack.peek() != null) {
          lastPop = stack.pop();
          if(startingBrackets.indexOf(lastPop) != endingBrackets.indexOf(str)){
            isBalanced = false;
            break;
          }
        }
        else {
          isBalanced = false;
          break;
        }
      } else {
        // invalid character in input.
        isBalanced = false;
        break;
      }
    }

    // rule 3
    if(stack.size() > 0) isBalanced = false;
    System.out.println(isBalanced);
  }
}