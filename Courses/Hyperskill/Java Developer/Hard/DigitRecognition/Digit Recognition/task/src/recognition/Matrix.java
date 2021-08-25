package recognition;

import java.util.ArrayList;
import java.util.List;

class Matrix {
    /*
    Understanding and Implementing Neural Networks in Java from Scratch 💻
    https://towardsdatascience.com/understanding-and-implementing-neural-networks-in-java-from-scratch-61421bb6352c
     */

    private final double[][] data;
    private final int rows,cols;

    public Matrix(int rows, int cols) {
        data = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;

        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                data[i][j]=Math.random()*2-1;
    }

    public void add(double offset) {
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                this.data[i][j] += offset;
    }

    public void add(Matrix m) {
        if(cols != m.cols || rows != m.rows) return;
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                this.data[i][j] += m.data[i][j];
    }

    public void multiply(double scalar) {
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                this.data[i][j] *= scalar;
    }

    public void multiply(Matrix a) {
        for(int i = 0; i < a.rows; i++)
            for(int j = 0; j < a.cols; j++)
                this.data[i][j] *= a.data[i][j];
    }

    public static Matrix fromArrayList(List<Double> list) {
        Matrix temp = new Matrix(list.size(),1);
        for(int i = 0; i < list.size(); i++)
            temp.data[i][0] = list.get(i);
        return temp;

    }

    public List<Double> toArrayList() {
        List<Double> list = new ArrayList<>();

        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                list.add(data[i][j]);

        return list;
    }

    public static Matrix add(Matrix a, Matrix b) {
        Matrix temp = new Matrix(a.rows, a.cols);

        for(int i = 0; i < a.rows; i++)
            for(int j = 0; j < a.cols; j++)
                temp.data[i][j] = a.data[i][j] + b.data[i][j];

        return temp;
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        Matrix temp = new Matrix(a.rows, b.cols);
        for(int i = 0; i< temp.rows; i++) {
            for (int j = 0; j < temp.cols; j++) {
                double sum = 0;
                for (int k = 0; k < a.cols; k++) {
                    sum += a.data[i][k] * b.data[k][j];
                }
                temp.data[i][j] = sum;
            }
        }
        return temp;
    }

    public static Matrix transpose(Matrix a) {
        Matrix temp = new Matrix(a.cols, a.rows);

        for(int i = 0; i < a.rows; i++)
            for(int j = 0; j < a.cols; j++)
                temp.data[j][i] = a.data[i][j];

        return temp;
    }

    /*
        NEURAL NETWORK HELPERS
     */

    public void sigmoid() {
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                this.data[i][j] = 1 / (1 + Math.exp(-this.data[i][j]));
    }

    public Matrix dSigmoid() {
        Matrix temp = new Matrix(rows, cols);

        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                temp.data[i][j] = this.data[i][j] * (1 - this.data[i][j]);

        return temp;
    }
}
