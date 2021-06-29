import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int len = sc.nextInt();
    int[] arr = new int[len];
    for (int i = 0; i < len; i++) {
      arr[i] = sc.nextInt();
    }
    int maxNum = 0;
    int indexMaxNum = 0;

    if (arr.length != 1) {
      for (int i = 0; i < len; i++) {
        if (arr[i] > maxNum) {
          maxNum = arr[i];
          indexMaxNum = i;
        }
      }
    }
    System.out.println(indexMaxNum);
  }
}