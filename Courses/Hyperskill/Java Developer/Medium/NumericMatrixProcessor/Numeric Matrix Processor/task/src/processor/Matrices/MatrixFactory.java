package processor.Matrices;

import java.util.Scanner;

public class MatrixFactory {

    public static Scanner scanner;

    public static Matrix getMatrixFromSize(int n, int m) {
        Matrix matrix = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix.fillPosition(scanner.nextDouble(), i, j);
            }
        }
        return matrix;
    }
}
