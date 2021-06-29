import java.util.Locale;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    String string = sc.nextLine().toLowerCase(Locale.ROOT);
    char[] genome = string.toCharArray();
    int encounters = 0;
    for (char gen: genome) {
      if (gen == 'c' || gen == 'g') encounters++;
    }

    double gcContent = ((double) encounters * 100) / genome.length;
    System.out.println(gcContent);
  }
}