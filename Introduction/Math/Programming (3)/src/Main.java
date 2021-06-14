import java.util.Scanner;

public class Main {

  public static void main(String[] args){
    //put your code here
    Scanner scanner = new Scanner(System.in);
    
    double x1 = scanner.nextDouble();
    double y1 = scanner.nextDouble();
    double x2 = scanner.nextDouble();
    double y2 = scanner.nextDouble();
    
    System.out.println(Math.toDegrees(calculate_angle(x1, y1, x2, y2)));
  }
  
  public static double calculate_dot_product(double x, double y, double a, double b){
    double dot = 0;
    dot = (x*a) + (y*b);
    return dot;
  }
  
  public static double calculate_magnitude(double x, double y){
    double magnitude = 0;
    magnitude = Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2)));
    return magnitude;
  }
  
  public static double calculate_angle(double x, double y, double a, double b){
    double cos = 0.0;
    cos = calculate_dot_product(x, y, a, b) / (calculate_magnitude(x, y) * calculate_magnitude(a, b));
    return Math.acos(cos);
  }
}