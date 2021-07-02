import java.util.*;

public class Main {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int size = sc.nextInt();
    Set<String> nameSet = new TreeSet<>();
    for (int i = 0; i <= size; i++) {
      nameSet.add(sc.nextLine());
    }
    nameSet.forEach(System.out::println);
  }
}