import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner sc = new Scanner(System.in);
    String ticket = sc.nextLine();

    int firstSum = Integer.parseInt(String.valueOf(ticket.charAt(0))) + Integer.parseInt(String.valueOf(ticket.charAt(1))) + Integer.parseInt(String.valueOf(ticket.charAt(2)));
    int secondSum = Integer.parseInt(String.valueOf(ticket.charAt(3))) + Integer.parseInt(String.valueOf(ticket.charAt(4))) + Integer.parseInt(String.valueOf(ticket.charAt(5)));
    System.out.println((firstSum==secondSum)? "Lucky" : "Regular");
  }
}