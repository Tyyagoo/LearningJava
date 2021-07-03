import java.util.*;

public class Main {

  public static void main(String[] args) {
    //put your code here
    Scanner sc = new Scanner(System.in);
    Map<Integer, String> map = new HashMap<>();

    int minRange = sc.nextInt();
    int maxRange = sc.nextInt();
    int inputLength = sc.nextInt();

    for(int c = 0 ; c < inputLength ; c++){
      int key = sc.nextInt();
      String value = sc.nextLine();
      map.put(key, value);
    }
    sc.close();

    for(Map.Entry<Integer, String> entry: map.entrySet()){
      if(entry.getKey() >= minRange && entry.getKey() <= maxRange){
        System.out.println(entry.getKey() + entry.getValue());
      }
    }
  }
}