
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    //put your code here

    BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
    String[] values = buffer.readLine().split("\\s+");

    int a = Integer.parseInt(values[0]);
    int b = Integer.parseInt(values[1]);

    System.out.print(b);
    System.out.print(" ");
    System.out.print(a);
  }
}