package rudiments;

import Jama.Matrix;

/**
 * Includes method to multiply two matrices
 * Math 2605
 * @author Jayden Gardiner
 * @version 1.0
 */
public class matrix_multiplication {

    /**
     * This method multiplies two matrices and
     * returns a new matrix after iterating
     * through the rows and columns of the
     * matrices and calculating the new values.
     * @param x Matrix to be multiplied by matrix y
     * @param y Matrix to be multiplied by matrix x
     * @return z New multiplied matrix
     */
    public static Matrix multiply(Matrix x, Matrix y) {
        double[][] z = new double[x.length - 1][y[0].length - 1];
        Matrix z = new Matrix(z);
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < y[0].length; j++) {
                for (int k = 0; k < x[0].length; k++) {
                    z[i][j] += x[i][k] * y[k][j]
                }
            }
        }
        return z;
    }
    
    /**
     * This method takes in two 2D arrays, turns them
     * into Jawa matrices, then multiplying two
     * matrices will return a new matrix after
     * iterating through the rows and columns of the
     * matrices and calculating the new values.
     * @param x Matrix to be multiplied by matrix y
     * @param y Matrix to be multiplied by matrix x
     * @return New multiplied matrix
     */
    public static Matrix multiply(double[][] x, double[][] y) {
        Matrix x = new Matrix(x);
        Matrix y = new Matrix(y);
        return multiply(x, y);
    }
    
    public static Matrix multiply(Matrix[] matrices) {
        double[][] z = new double[matrices.length - 1][matrices.length - 1];
        Matriz z = new Matrix(z);
        for (int b = 1; b < matrices[0].length; b++) {
            for (int i = 0; i < matrices[0] - b; i++) {
                int j = i + b;
                z[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    z[i][j] = Math.min(z[i, j], z[i][k] + z[k + 1][j]
                            + matrices[i]*matrices[k + 1]*matrices[j + 1]]);
                }
            }
        }
        return z;
    }
}