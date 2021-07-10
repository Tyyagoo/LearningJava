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

    public static Matrix cloneMatrix(Matrix matrix) {
        int n = matrix.getRowsSize();
        int m = matrix.getColumnsSize();
        Matrix newMatrix = new Matrix(n, m);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                newMatrix.fillPosition(matrix.getPosition(i, j), i, j);
            }
        }

        return newMatrix;
    }
}
