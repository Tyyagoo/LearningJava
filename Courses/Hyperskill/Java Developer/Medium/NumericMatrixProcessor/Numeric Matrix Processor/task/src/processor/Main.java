package processor;

import java.util.*;
import java.io.*;

import processor.Exceptions.InvalidMatrixSizeException;
import processor.Matrices.*;

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
                resultMatrix.print();
            } catch (InvalidMatrixSizeException e) {
                System.out.println(e.getMessage());
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
            matrix.scale(s);
            matrix.print();
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
                resultMatrix.print();
            } catch (InvalidMatrixSizeException e) {
                System.out.println(e.getMessage());
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
