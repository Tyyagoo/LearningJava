import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner scanner = new Scanner(System.in);
    int class1 = scanner.nextInt();
    int class2 = scanner.nextInt();
    int class3 = scanner.nextInt();
  
    System.out.println(get_desks_number(class1)+get_desks_number(class2)+get_desks_number(class3));
  }
  
  public static int get_desks_number(int students) {
    if (students % 2 != 0) ++students;
    return students / 2;
  }
}