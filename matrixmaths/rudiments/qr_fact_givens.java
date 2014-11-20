package rudiments;

import java.util.Arrays;

import Jama.Matrix;

/**
 * A class that holds a method that QR-factorizes matrices by method of
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
        double[][] Gn = new double[A.length][A.length];
        double[][] Q = new double[A.length][A.length];
        
        System.out.println(); 
        System.out.println("Number of rows and columns in our original matrix: " + (A.length) + " x " + (A.length) + ".");
        System.out.println();  
        
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
        
        int iteration = 1;

        for (int i = 0; i < A.length; i++) {
            for (int j  = A.length - 1; j > i + 1; j--) {
                double x = An[j - 1][i];
                double y = An[j][i];
                double cosTheta = x / (Math.sqrt(x * x + y * y));
                double sinTheta = y / (Math.sqrt(x * x + y * y));
                Gn[j][j] = cosTheta;
                Gn[j][j - 1] = sinTheta;
                Gn[j - 1][j] = -sinTheta;
                Gn[j - 1][j - 1] = cosTheta;
                
                System.out.println("G" + iteration + ":");
                new Matrix(Gn).print(2,5);

                An = matrix_multiplication.multiply2DArrays(Gn, An);
                
                System.out.println("A" + iteration + ":");
                new Matrix(An).print(2, 5);
                
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
                iteration += 1;
            }
        }
        
        System.out.println("Q:");
        new Matrix(Q).print(2, 5);
         
        System.out.println("R:");
        new Matrix(An).print(2, 5);
        
        Matrix Qmatrix = new Matrix(Q);
        Qmatrix = Qmatrix.transpose();
        double[][] R = An;
        Matrix Rmatrix = new Matrix(R);
        Matrix[] QR = {Qmatrix, Rmatrix};
        
        System.out.println("QR:");
        Matrix mm = matrix_multiplication.multiply(Q, R);
        mm.print(2, 5);
        System.out.println("A:");
        new Matrix(A).print(2, 5);
        
        return QR;
    }

   public static void printMatrix(double[][] matrix) {
       System.out.println(Arrays.deepToString(matrix));
   }
   
   public static void main(String[] args) {
       double[][] hello = {{6, 5, 1},{5, 1, 1}, {1, 1,1}};
       factorize(hello);
   }
}