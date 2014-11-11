package rudiments;

import Jama.Matrix;

/**
 * Includes method to calculate Givens rotation
 * Math 2605
 * @author Jayden Gardiner
 * @version 1.0
 */
public class qr_fact_givens {
    
    Matrix Q;
    Matrix R;
    
    public static void Givens(double[][] A) {
        Matrix matrix = new Matrix(A);
        Givens(matrix);
    }
    
    public static void Givens(Matrix A) {
        double[][] newMatrix = new double[A.length - 1][A[0].length - 1];
        Matrix Gn = new Matrix(newMatrix);
        Matrix An = new Matrix(newMatrix);
        //form identity matrix
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                if (i == j) {
                    Gn[i][j] = 1;
                } else {
                    Gn[i][j] = 0;
                }
            }
        }
        //calc cos + sin shit
        for (int i = 0; i < A.length; i++) {
            for (int j  = A.length - 1; j > 1; j--) {
                double x = A[j - 1][i];
                double y = A[j][i];
                double cosTheta = x / (Math.sqrt(x * x + y * y));
                double sinTheta = y / (Math.sqrt(x * x + y * y));
                Gn[j][j] = cosTheta;
                Gn[j][j - 1] = sinTheta;
                Gn[j - 1][j] = -sinTheta;
                Gn[j - 1][j - 1] = cosTheta;
                Matrix GnA = multiply(Gn, A);
                Q = multiply(Gn.transpose(), Q);
                R = multiply(Gn, R);
                //....
            }
        }
    }
    
    public static Matrix getQ() {
        return Q;
    }
    
    public static Matrix getR() {
        return R;
    }
}