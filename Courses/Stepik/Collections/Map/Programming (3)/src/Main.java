import java.util.*;
class Main {

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    SortedMap<String, String> sm = new TreeMap<>();
    SortedMap<String, String> result = new TreeMap<>();


    int dict_words = sc.nextInt();
    sc.nextLine();
    for (int i = 0; i < dict_words; i++) {
      String dict_words_s = sc.nextLine().toLowerCase();
      sm.put(dict_words_s, dict_words_s);
    }

    int check_words = sc.nextInt();
    sc.nextLine();
    for (int j = 0; j < check_words; j++) {
      String[] arr_of_cw = sc.nextLine().toLowerCase().split(" ");
      for (int n = 0; n < arr_of_cw.length; n++) {
        if (!sm.containsValue(arr_of_cw[n])) {
          result.put(arr_of_cw[n], arr_of_cw[n]);
        }
      }
    }
    for (String result_words : result.values()) {
      System.out.println(result_words);
    }

  }
}