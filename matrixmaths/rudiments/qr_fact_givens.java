package rudiments;

import Jama.Matrix;

/**
 * A class that holds methods that QR-factorize matrices by method of
 * Givens rotations.
 *
 * @author Jayden Gardiner
 * @version 1.0
 */
public class qr_fact_givens {
   
    /**
     * Takes in a matrix, and decomposes it into a Q matrix and an R matrix by
     * means of Givens rotations.
     * 
     * @param A the given matrix
     * @return an array of the two matrices, Q and R
     */
   public static Matrix[] factorize(double[][] A) {
        if (A == null) {
            throw new IllegalArgumentException();
        }
        
        double[][] An = A;
        double[][] Gn = new double[A.length - 1][A.length - 1];
        double[][] Q = new double[A.length - 1][A.length - 1];
        
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                if (i == j) {
                    Gn[i][j] = 1;
                    Q[i][j] = 1;
                } else {
                    Gn[i][j] = 0;
                    Q[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < A.length; i++) {
            for (int j  = A.length - 1; j > 1; j--) {
                double x = An[j - 1][i];
                double y = An[j][i];
                double cosTheta = x / (Math.sqrt(x * x + y * y));
                double sinTheta = y / (Math.sqrt(x * x + y * y));
                Gn[j][j] = cosTheta;
                Gn[j][j - 1] = sinTheta;
                Gn[j - 1][j] = -sinTheta;
                Gn[j - 1][j - 1] = cosTheta;
                
                An = matrix_multiplication.multiply2DArrays(Gn, An);
                Q = matrix_multiplication.multiply2DArrays(Gn, Q);
                
                for (int a = 0; a < A.length; a++) {
                    for (int b = 0; b < A.length; b++) {
                        if (a == b) {
                            Gn[a][b] = 1;
                        } else {
                            Gn[a][b] = 0;
                        }
                    }
                }
            }
        }
        
        Matrix Qmatrix = new Matrix(Q);
        Qmatrix = Qmatrix.transpose();
        double[][] R = An;
        Matrix Rmatrix = new Matrix(R);
        Matrix[] QR = {Qmatrix, Rmatrix};
        
        return QR;
    }
}