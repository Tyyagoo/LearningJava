import java.util.*;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    Deque<Integer> deque = new ArrayDeque<>();

    int inputLength = sc.nextInt();

    for(int i=0;i<inputLength;i++){
      int input = sc.nextInt();

      if(input % 2 == 0) deque.add(input);
      else deque.push(input);
    }

    while(deque.peekLast() != null){
      System.out.println(deque.pollLast());
    }
  }
}