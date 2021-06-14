
import java.util.Scanner;

public class Main{

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		int h1 = scanner.nextInt();
		int m1 = scanner.nextInt();
		int s1 = scanner.nextInt();
		
		int h2 = scanner.nextInt();
		int m2 = scanner.nextInt();
		int s2 = scanner.nextInt();
		
		int hourDiff = h2 - h1;
		int minDiff = m2 - m1;
		int secDiff = s2 - s1;
		
		System.out.println((hourDiff*3600) + (minDiff*60) + (secDiff));
	}
}