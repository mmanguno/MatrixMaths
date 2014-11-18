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
        
        //if (null == x || y == x || x.getColumnDimension() 
        //                                               != y.getRowDimension()) {
        //    throw new IllegalArgumentException();
        //}
        
        double[][] xDub = x.getArrayCopy();
        double[][] yDub = y.getArrayCopy();
        
        return multiply(xDub, yDub);

    }
    
    /**
     * This method takes in two 2D arrays, turns them
     * into Jama matrices, then multiplying two
     * matrices will return a new matrix after
     * iterating through the rows and columns of the
     * matrices and calculating the new values.
     * @param x Matrix to be multiplied by matrix y
     * @param y Matrix to be multiplied by matrix x
     * @return New multiplied matrix
     */
    public static Matrix multiply(double[][] x, double[][] y) {

        //if (null == x || y == x || x.length != y[0].length) {
        //        throw new IllegalArgumentException();
        //}
        
        double[][] z = new double[x.length][y[0].length];
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < y[0].length; j++) {
                for (int k = 0; k < x[0].length; k++) {
                    z[i][j] += x[i][k] * y[k][j];
                }
            }
        }
        Matrix zMatrix = new Matrix(z);
        return zMatrix;
    }
    
    public static double[][] multiply2DArrays(double[][] x, double[][] y) {

        if (null == x || y == x || x.length != y[0].length) {
                throw new IllegalArgumentException();
        }
        
        double[][] z = new double[x.length][y[0].length];
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < y[0].length; j++) {
                for (int k = 0; k < x[0].length; k++) {
                    z[i][j] += x[i][k] * y[k][j];
                }
            }
        }
        return z;
    }
}