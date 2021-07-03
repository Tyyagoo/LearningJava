import java.util.*;

public class Main {

  public static void main(String[] args) {
    //put your code here

    Scanner sc = new Scanner(System.in);

    Map<String, String> decodeMap = new HashMap<>();
    Map<String, String> encodeMap = new HashMap<>();

    String[] originalAlphabet = readAndSplitString(sc);
    String[] encodedAlphabet = readAndSplitString(sc);

    String[] lineToEncode = readAndSplitString(sc);
    String[] lineToDecode = readAndSplitString(sc);


    if(originalAlphabet.length != encodedAlphabet.length) return;
    // create the cipher
    for(int i = 0 ; i < originalAlphabet.length ; i++){
      decodeMap.put(encodedAlphabet[i], originalAlphabet[i]);
      encodeMap.put(originalAlphabet[i], encodedAlphabet[i]);
    }

    String encodedLine = swapString(lineToEncode, encodeMap);
    String decodedline = swapString(lineToDecode, decodeMap);

    System.out.println(encodedLine);
    System.out.println(decodedline);

  }

  public static String[] readAndSplitString(Scanner scanner){
    String[] inputArray = scanner.nextLine().split("");
    return inputArray;
  }

  public static String swapString(String[] original, Map<String, String> mapAlphabet){
    StringBuilder stringBuilder = new StringBuilder();

    for(String letter: original){
      stringBuilder.append(mapAlphabet.get(letter));
    }

    return stringBuilder.toString();
  }
}