
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    //put your code here

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String[] input = reader.readLine().split("\\s+");

    int n = Integer.parseInt(input[0]);
    int result = ((n+1)*n + 2)*n + 3;
    System.out.println(result);
  }
}