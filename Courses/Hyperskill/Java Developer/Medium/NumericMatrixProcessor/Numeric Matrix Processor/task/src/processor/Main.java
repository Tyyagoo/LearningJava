package processor;

import java.util.*;
import java.io.*;

public class Main {

    public static boolean forceProgramEnd = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserInterface.scanner = scanner;
        MatrixFactory.scanner = scanner;

        while (!forceProgramEnd) {
            UserInterface.invoke();
        }
    }
}

interface Command {
    void execute();
}


class UserInterface {

    public static Scanner scanner;

    private static final Command optionZero = () -> Main.forceProgramEnd = true;

    private static final Command optionOne = new Command() {
        @Override
        public void execute() {
            System.out.print("Enter the size of first matrix: ");
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            System.out.println("Enter first matrix:");
            Matrix firstMatrix = MatrixFactory.getMatrixFromSize(n, m);

            System.out.print("Enter the size of second matrix: ");
            n = scanner.nextInt();
            m = scanner.nextInt();
            System.out.println("Enter second matrix:");
            Matrix secondMatrix = MatrixFactory.getMatrixFromSize(n, m);

            try {
                Matrix resultMatrix = firstMatrix.sum(secondMatrix);
                System.out.println("The result is:");
                System.out.println(resultMatrix);
            } catch (Exception e) {
                System.out.println("The operation cannot be performed.");
            }
        }
    };

    private static final Command optionTwo = new Command() {
        @Override
        public void execute() {
            System.out.print("Enter the size of matrix: ");
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            System.out.println("Enter matrix:");
            Matrix matrix = MatrixFactory.getMatrixFromSize(n, m);
            System.out.print("Enter constant: ");
            double s = scanner.nextDouble();
            System.out.println("The result is:");
            System.out.println(matrix.scale(s));
        }
    };

    private static final Command optionThree = new Command() {
        @Override
        public void execute() {
            System.out.print("Enter the size of first matrix: ");
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            System.out.println("Enter first matrix:");
            Matrix firstMatrix = MatrixFactory.getMatrixFromSize(n, m);

            System.out.print("Enter the size of second matrix: ");
            n = scanner.nextInt();
            m = scanner.nextInt();
            System.out.println("Enter second matrix:");
            Matrix secondMatrix = MatrixFactory.getMatrixFromSize(n, m);
            try {
                Matrix resultMatrix = firstMatrix.multiply(secondMatrix);
                System.out.println("The result is:");
                System.out.println(resultMatrix);
            } catch (Exception e) {
                System.out.println("The operation cannot be performed.");
            }
        }
    };

    private static void showOptions() {
        System.out.println("1. Add matrices");
        System.out.println("2. Multiply matrix by a constant");
        System.out.println("3. Multiply matrices");
        System.out.println("0. Exit");
    }

    private static void getUserOption() {
        System.out.print("Your choice: ");
        int userChoice = scanner.nextInt();
        switch (userChoice) {
            case 0:
                optionZero.execute();
                break;
            case 1:
                optionOne.execute();
                break;
            case 2:
                optionTwo.execute();
                break;
            case 3:
                optionThree.execute();
                break;
            default:
                System.out.println("This option doesn't exists. Try again.");
                break;
        }
    }

    public static void invoke() {
        System.out.println();
        showOptions();
        getUserOption();
    }
}


class MatrixFactory {

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


class Matrix {

    private final double[][] object;
    private final int n;
    private final int m;
    public final IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException();
    public final RuntimeException invalidMatrixSizeException = new RuntimeException() {};

    private boolean isIntegerMatrix = false;

    Matrix(int n, int m) {
        this.object = new double[n][m];
        this.n = n;
        this.m = m;
    }

    public void fillMatrix(double[][] obj) throws RuntimeException {
        if (n != obj.length || m != obj[0].length) {
            throw invalidMatrixSizeException;
        }

        for (int i = 0; i < n; i++) {
            System.arraycopy(obj[i], 0, this.object[i], 0, m);
        }
    }

    public void fillPosition(double value, int i, int j) throws IndexOutOfBoundsException {
        if (i < n && j < m) {
            this.object[i][j] = value;
            return;
        }
        throw indexOutOfBoundsException;
    }

    public double getPosition(int i, int j) {
        if (i < n && j < m) {
            return object[i][j];
        }
        throw indexOutOfBoundsException;
    }

    public Matrix sum(Matrix other) throws RuntimeException{
        if (this.n == other.n && this.m == other.m) {
            Matrix result = new Matrix(this.n, this.m);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    result.fillPosition((getPosition(i, j) + other.getPosition(i, j)), i, j);
                }
            }
            return result;
        }
        throw invalidMatrixSizeException;
    }

    public Matrix sub(Matrix other) throws RuntimeException{
        if (this.n == other.n && this.m == other.m) {
            Matrix result = new Matrix(this.n, this.m);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    result.fillPosition((getPosition(i, j) - other.getPosition(i, j)), i, j);
                }
            }
            return result;
        }
        throw invalidMatrixSizeException;
    }

    public Matrix multiply(Matrix other) throws RuntimeException {
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
        throw invalidMatrixSizeException;
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

    public void checkIfIsIntegerMatrix() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (object[i][j] - (int) object[i][j] > 0.001) {
                    isIntegerMatrix = false;
                    return;
                }
            }
        }
        isIntegerMatrix = true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        checkIfIsIntegerMatrix();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (isIntegerMatrix) {
                    stringBuilder.append((int) object[i][j]);
                } else {
                    stringBuilder.append(String.format("%.2f", object[i][j]));
                }
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public int getRowsSize() {
        return n;
    }

    public int getColumnsSize() {
        return m;
    }
}
