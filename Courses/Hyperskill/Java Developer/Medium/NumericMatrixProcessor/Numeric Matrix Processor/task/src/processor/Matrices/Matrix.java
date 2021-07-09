package processor.Matrices;

import processor.Exceptions.*;

public class Matrix {

    private final double[][] object;
    private final int n;
    private final int m;

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
                    result.fillPosition((getPosition(i, j) + other.getPosition(i, j)), i, j);
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
        if (this.n == other.m) {
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

    public void print() {
        System.out.println("The result is:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.printf("%d ", (int) object[j][i]);
                //System.out.printf("%5.2f ", object[j][i]);
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
