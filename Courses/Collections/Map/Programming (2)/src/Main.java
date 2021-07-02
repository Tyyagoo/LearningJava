import java.util.*;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    Map<String, Integer> map = new LinkedHashMap<>();
    String inputLine = sc.nextLine();

    for(String str: inputLine.split(" ")){
      if(map.containsKey(str.toLowerCase())){
        map.put(str.toLowerCase(),map.get(str.toLowerCase())+1);
      } else{
        map.put(str.toLowerCase(), 1);
      }
    }

    for(Map.Entry<String, Integer> entry: map.entrySet()){
      System.out.println(entry.getKey() + " " + entry.getValue());
    }

  }
}