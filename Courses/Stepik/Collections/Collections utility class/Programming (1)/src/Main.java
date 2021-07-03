import java.util.*;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);
    List<List<Integer>> list = new ArrayList<>();

    int numberOfRows = sc.nextInt();
    int numberOfColumns = sc.nextInt();

    for(int i = 0 ; i < numberOfRows ; i++){

      List<Integer> subList = new ArrayList<>();

      for(int j = 0 ; j < numberOfColumns ; j++){
        subList.add(sc.nextInt());
      }

      list.add(subList);
    }

    int rotation = sc.nextInt();
    Collections.rotate(list, rotation);

    for(List<Integer> subList: list){
      for(Integer i: subList){
        System.out.print(i + " ");
      }
      System.out.println();
    }


  }
}