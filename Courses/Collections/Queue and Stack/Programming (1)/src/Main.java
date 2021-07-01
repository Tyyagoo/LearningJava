import java.util.*;

public class Main {

  public static void main(String[] args) {

    Deque<Integer> queue = new ArrayDeque<>(Arrays.asList(1,2,3,4));

    //write your code here
    queue.add(5);
    queue.remove();
    queue.remove();


    System.out.println(queue);

  }
}