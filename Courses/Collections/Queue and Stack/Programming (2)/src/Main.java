import java.util.*;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);

    int inputLength = sc.nextInt();
    Deque<Integer> stack = new ArrayDeque<>();

    for(int i=0;i<inputLength;i++){
      stack.push(sc.nextInt());
    }

    for(int i=0;i<inputLength;i++){
      System.out.println(stack.pop());
    }
  }
}