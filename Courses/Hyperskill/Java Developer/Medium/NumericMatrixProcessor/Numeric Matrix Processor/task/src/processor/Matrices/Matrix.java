package processor.Matrices;

import processor.Exceptions.*;
import java.util.*;

public class Matrix {

    private final double[][] object;
    private final int n;
    private final int m;

    public enum TransposeType {
        MAIN_DIAGONAL(1),
        SIDE_DIAGONAL(2),
        VERTICAL(3),
        HORIZONTAL(4);

        private final int value;

        TransposeType(int v) {
            this.value = v;
        }

        public static TransposeType getTypeByValue(int value) {
            switch (value) {
                case 2:
                    return SIDE_DIAGONAL;
                case 3:
                    return VERTICAL;
                case 4:
                    return HORIZONTAL;
                default:
                    return MAIN_DIAGONAL;
            }
        }
    }

    public Matrix(int n, int m) {
        this.object = new double[n][m];
        this.n = n;
        this.m = m;
    }

    public void fillMatrix(double[][] obj) throws InvalidMatrixSizeException {
        if (n != obj.length || m != obj[0].length) {
            throw new InvalidMatrixSizeException();
        }

        for (int i = 0; i < n; i++) {
            System.arraycopy(obj[i], 0, this.object[i], 0, m);
        }
    }

    public void fillPosition(double value, int i, int j) throws MatrixIndexOutOfBoundsException {
        if (i < n && j < m) {
            this.object[i][j] = value;
            return;
        }
        throw new MatrixIndexOutOfBoundsException();
    }

    public double getPosition(int i, int j) {
        if (i < n && j < m) {
            return object[i][j];
        }
        throw new MatrixIndexOutOfBoundsException();
    }

    public Matrix sum(Matrix other) throws InvalidMatrixSizeException {
        if (this.n == other.n && this.m == other.m) {
            Matrix result = new Matrix(this.n, this.m);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    double newValue = getPosition(i, j) + other.getPosition(i, j);
                    result.fillPosition(newValue, i, j);
                }
            }
            return result;
        }
        throw new InvalidMatrixSizeException();
    }

    public Matrix sub(Matrix other) throws InvalidMatrixSizeException {
        if (this.n == other.n && this.m == other.m) {
            Matrix result = new Matrix(this.n, this.m);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    result.fillPosition((getPosition(i, j) - other.getPosition(i, j)), i, j);
                }
            }
            return result;
        }
        throw new InvalidMatrixSizeException();
    }

    public Matrix multiply(Matrix other) throws InvalidMatrixSizeException {
        if (this.m == other.n) {
            Matrix result = new Matrix(this.n, other.m);

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < other.m; j++) {
                    double x = 0;
                    for (int c = 0; c < m; c++) {
                        x += (getPosition(i, c) * other.getPosition(c, j));
                    }
                    result.fillPosition(x, i, j);
                }
            }

            return result;
        }
        throw new InvalidMatrixSizeException();
    }

    public Matrix scale(double scalar) {
        Matrix scaledMatrix = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                scaledMatrix.fillPosition((scalar * getPosition(i, j)), i, j);
            }
        }
        return scaledMatrix;
    }

    public Matrix transpose(TransposeType type) {
        switch (type) {
            case MAIN_DIAGONAL:
                return transposeAlongMainDiagonal();
            case SIDE_DIAGONAL:
                return transposeAlongSideDiagonal();
            case VERTICAL:
                return transposeAlongVerticalLine();
            case HORIZONTAL:
                return transposeAlongHorizontalLine();
        }
        return new Matrix(1, 1);
    }

    private Matrix transposeAlongMainDiagonal() throws InvalidMatrixSizeException {
        if (n != m) throw new InvalidMatrixSizeException();
        Matrix transposedMatrix = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                transposedMatrix.fillPosition(object[j][i], i, j);
            }
        }
        return transposedMatrix;
    }

    private Matrix transposeAlongSideDiagonal() throws InvalidMatrixSizeException {
        if (n != m) throw new InvalidMatrixSizeException();
        Matrix transposedMatrix = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                double value = getPosition(((n - 1) - j), ((m - 1) - i));
                transposedMatrix.fillPosition(value, i, j);
            }
        }
        return transposedMatrix;
    }

    private Matrix transposeAlongVerticalLine() throws InvalidMatrixSizeException {
        if (n != m) throw new InvalidMatrixSizeException();
        Matrix transposedMatrix = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                double value = getPosition(i, (m - 1) - j);
                transposedMatrix.fillPosition(value, i, j);
            }
        }
        return transposedMatrix;
    }

    private Matrix transposeAlongHorizontalLine() throws InvalidMatrixSizeException {
        if (n != m) throw new InvalidMatrixSizeException();
        Matrix transposedMatrix = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                double value = getPosition((n-1) - i, j);
                transposedMatrix.fillPosition(value, i, j);
            }
        }
        return transposedMatrix;
    }

    public double getDeterminant() throws InvalidMatrixSizeException {
        if (n != m) throw new InvalidMatrixSizeException();

        // O(1) Time - O(1) Memory
        if (n == 1) return object[0][0];
        // O(1) Time - O(1) Memory
        if (n == 2) return (getPosition(0, 0) * getPosition(1,1)) - (getPosition(0, 1) * getPosition(1, 0));
        // O(1) Time - O(1) Memory
        if (n == 3) {
            double a, b, c;
            a = getPosition(0, 0) * ((getPosition(1, 1)*getPosition(2, 2)) - (getPosition(1, 2)*getPosition(2, 1)));
            b = getPosition(0, 1) * ((getPosition(1, 0)*getPosition(2, 2)) - (getPosition(2, 0)*getPosition(1, 2)));
            c = getPosition(0, 2) * ((getPosition(1, 0)*getPosition(2, 1)) - (getPosition(1, 1)*getPosition(2, 0)));

            return a - b + c;
        }

        // now is the time the child cries and the mother doesn't see T.T
        // O(n³) Time - O(n) Memory
        int index;
        double num1, num2;
        double det = 1, total = 1;
        double[] temp = new double[n + 1];

        // loop for traversing the diagonal elements
        for (int i = 0; i < n; i++) {
            index = i; // initialize the index

            // finding the index which has non zero value
            while (getPosition(index, i) == 0 && index < n) {
                index++;
            }

            // if there is non zero element
            if (index == n) {
                // the determinant of matrix as zero
                continue;
            }
            if (index != i) {
                // loop for swapping the diagonal element row
                // and index row
                for (int j = 0; j < n; j++) {
                    double tmp = getPosition(index, j);
                    fillPosition(getPosition(i, j), index, j);
                    fillPosition(tmp, i, j);
                }
                // determinant sign changes when we shift
                // rows go through determinant properties
                det = (int)(det * Math.pow(-1, index - i));
            }

            // storing the values of diagonal row elements
            for (int j = 0; j < n; j++) {
                temp[j] = getPosition(i, j);
            }

            // traversing every row below the diagonal
            // element
            for (int j = i + 1; j < n; j++) {
                num1 = temp[i]; // value of diagonal element
                num2 = getPosition(j, i);

                // traversing every column of row
                // and multiplying to every row
                for (int k = 0; k < n; k++) {
                    // multiplying to make the diagonal
                    // element and next row element equal
                    fillPosition((num1 * getPosition(j, k)) - (num2 * temp[k]), j, k);
                }
                total = total * num1; // Det(kA)=kDet(A);
            }
        }

        // multiplying the diagonal elements to get
        // determinant
        for (int i = 0; i < n; i++) {
            det = det * getPosition(i, i);
        }
        return (det / total); // Det(kA)/k=Det(A);

        /*
        IMPORTANT:
            from: https://www.geeksforgeeks.org/determinant-of-a-matrix/
        TODO -> Then I have to try to implement my own.
         */
    }

    public void print() {
        System.out.println("The result is:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.printf("%5.2f ", object[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getRowsSize() {
        return n;
    }

    public int getColumnsSize() {
        return m;
    }
}
