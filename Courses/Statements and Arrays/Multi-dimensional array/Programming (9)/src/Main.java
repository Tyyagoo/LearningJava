import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String input = "";
    while (!sc.hasNext("end")) {
      input += sc.nextLine() + ",";
    }
    String[] rows = input.split(",");
    int sizeRow = rows.length;
    int sizeColumn = rows[0].split(" ").length;

    int[][] matrix = new int[sizeRow][sizeColumn], outMatrix = new int[sizeRow][sizeColumn];

    for (int i = 0; i < sizeRow; i++) {
      String[] s = rows[i].split(" ");
      for (int j = 0; j < sizeColumn; j++) {
        matrix[i][j] = Integer.parseInt(s[j]);
      }
    }

    for (int i = 0; i < sizeRow; i++) {
      for (int j = 0; j < sizeColumn; j++) {

        int iPlus, iMinus, jPlus, jMinus;

        if (i == 0) {
          iPlus = sizeRow - 1;
        } else {
          iPlus = i - 1;
        }

        if (i == sizeRow - 1) {
          iMinus = 0;
        } else {
          iMinus = i + 1;
        }

        if (j == 0) {
          jPlus = sizeColumn - 1;
        } else {
          jPlus = j - 1;
        }

        if (j == sizeColumn - 1) {
          jMinus = 0;
        } else {
          jMinus = j + 1;
        }

        outMatrix[i][j] = (matrix[iPlus][j] + matrix[iMinus][j] + matrix[i][jPlus] + matrix[i][jMinus]);

      }
    }

    for (int[] anInt : outMatrix) {
      StringBuilder builder = new StringBuilder();
      for (int j = 0; j < sizeColumn; j++) {
        builder.append(anInt[j]).append(" ");
      }
      System.out.println(builder.toString().trim());
    }
  }
}