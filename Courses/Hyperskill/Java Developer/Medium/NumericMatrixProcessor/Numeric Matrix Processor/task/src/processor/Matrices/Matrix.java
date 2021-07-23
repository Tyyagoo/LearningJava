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

    /*
    THANKS FOR:
    https://www.youtube.com/watch?v=xfhzwNkMNg4 - Inverse of a 3x3 Matrix using Adjoint | Don't Memories
    https://www.mathwords.com/c/cofactor_matrix.htm - Matrix of Cofactors

    Cofactor Expansion for calculate determinant:
    https://people.math.carleton.ca/~kcheung/math/notes/MATH1107/wk07/07_cofactor_expansion.html#:~:text=One%20way%20of%20computing%20the,the%20determinant%20along%20row%20i.
     */

    public double getDeterminant() throws InvalidMatrixSizeException {
        if (n != m) throw new InvalidMatrixSizeException();

        // O(1) Time - O(1) Memory
        if (n == 1) return object[0][0];
        // O(1) Time - O(1) Memory
        if (n == 2) return (getPosition(0, 0) * getPosition(1,1)) - (getPosition(0, 1) * getPosition(1, 0));

        double determinant = 0.0;
        for (int j = 0; j < n; j++) {
            determinant += getPosition(0, j) * getCofactorOfElement(0, j);
        }

        return determinant;
    }

    private Matrix getSubMatrix(int ignoreI, int ignoreJ) {
        Matrix subMatrix = new Matrix(n - 1, m - 1);

        int currentI = 0;
        int currentJ = 0;
        for (int i = 0; i < n; i++) {
            if (i == ignoreI) continue;
            for (int j = 0; j < n; j++) {
                if (j == ignoreJ) continue;
                subMatrix.fillPosition(getPosition(i, j), currentI, currentJ++);
                if (currentJ == n - 1) {
                    currentJ = 0;
                    currentI++;
                }
            }
        }
        return subMatrix;
    }


    private double getCofactorOfElement(int i, int j) {
        Matrix subMatrix = getSubMatrix(i, j);
        /*
        THIS FUCKING LINE OF CODE SAVED ME:
        return Math.pow(-1, 2 + j + i) * determinantOfMatrix(minor);
        FROM:
        @mlompar
         */
        return Math.pow(-1, 2 + i + j) * subMatrix.getDeterminant();
    }

    private Matrix getCofactorMatrix() {
        Matrix cofactorMatrix = new Matrix(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                double cofactor = getCofactorOfElement(i, j);
                cofactorMatrix.fillPosition(cofactor, i, j);
            }
        }

        return cofactorMatrix;
    }

    private Matrix getAdjointMatrix() {
        Matrix cofactorMatrix = getCofactorMatrix();
        return cofactorMatrix.transpose(TransposeType.MAIN_DIAGONAL);
    }

    public Matrix getInverseMatrix() throws DeterminantEqualsZeroException {
        double determinant = MatrixFactory.cloneMatrix(this).getDeterminant();
        if (determinant == 0.0) throw new DeterminantEqualsZeroException();
        return getAdjointMatrix().scale(1.0 / determinant);
    }

    public void print() {
        System.out.println("The result is:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.printf("%8.5f ", object[i][j]);
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
