import java.util.*;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    int inputLength = Integer.parseInt(sc.nextLine().split(" ")[0]);

    for(int c=0 ; c<inputLength ; c++){
      String inputLine = sc.nextLine();
      CommandList.parseCommand(inputLine);
    }
  }
}

interface Command{
  void execute(String[] args);
}

class CommandList{

  static private final Command push = new Command(){
    public void execute(String[] args){
      if(args.length > 0){
        try{
          int number = Integer.parseInt(args[0]);
          SpecialStack.push(number);
        } catch(NumberFormatException e){
          throw e;
        }
      }
    }
  };

  static private final Command pop = new Command(){
    public void execute(String[] args){
      if(args.length > 0) return;
      SpecialStack.pop();
    }
  };

  static private final Command max = new Command(){
    public void execute(String[] args){
      if(args.length > 0) return;
      SpecialStack.max();
    }
  };


  public static void parseCommand(String commandLine){
    String[] commandPieces = commandLine.split(" ");
    String commandKeyword = commandPieces[0];
    //System.out.println(commandKeyword);
    switch(commandKeyword){
      case "max":
        max.execute(new String[0]);
        break;
      case "pop":
        pop.execute(new String[0]);
        break;
      case "push":
        push.execute(new String[]{commandPieces[1]});
        break;
      default:
        System.out.println("Command doesn't exists.");
        break;
    }
  }
}

class SpecialStack {
  /*
  Design from:
  https://www.geeksforgeeks.org/design-a-stack-that-supports-getmin-in-o1-time-and-o1-extra-space/
   */

  static private final ArrayDeque<Integer> stack = new ArrayDeque<>();
  static private Integer maxElement;

  public static void push(Integer obj){
    /*
    Push(x) : Inserts x at the top of stack.

    If stack is empty, insert x into the stack and make maxEle equal to x.
    If stack is not empty, compare x with maxEle. Two cases arise:
    If x is less than or equal to maxEle, simply insert x.
    If x is greater than maxEle, insert (2*x – maxEle) into the stack and make maxEle equal to x.
     */
    if(stack.isEmpty()){
      stack.push(obj);
      maxElement = obj;
      return;
    }

    if(obj > maxElement){
      stack.push(2 * obj - maxElement);
      maxElement = obj;
    } else stack.push(obj);
  }

  public static void pop(){
    /*
    Pop() : Removes an element from top of stack.

    Remove element from top. Let the removed element be y. Two cases arise:
    If y is less than or equal to maxEle, the maximum element in the stack is still maxEle.
    If y is greater than maxEle, the maximum element now becomes (2*maxEle – y), so update (maxEle = 2*maxEle – y).
     */
    if(stack.isEmpty()) return;
    Integer poppedElement = stack.pop();

    if(poppedElement > maxElement){
      maxElement = 2 * maxElement - poppedElement;
    }
  }

  public static void max(){
    if(stack.isEmpty()) return;
    System.out.println(maxElement.toString());
  }
}
