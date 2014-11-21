package rudiments;

import Jama.Matrix;
import Jama.QRDecomposition;

import java.util.ArrayList;


/**
 * A class that holds methods that QR-factorize matrices by method of
 * Householder Reflections.
 * 
 * @author Mitchell Manguno
 * @version 2.0
 * @since 2014-November-20
 */
public class qr_fact_househ {

    
    /**
     * Takes in a matrix, and decomposes it into a Q matrix and an R matrix by
     * means of Householder Reflections.
     * 
     * @param A the given matrix
     * @return an array of the two matrices, Q and R
     */
    public static Matrix[] factorize(Matrix A) {
        //Check to make sure this is a square matrix
        //if (columns != rows || null == A) {
        //    throw new IllegalArgumentException();
        //}
        
        int columns = A.getColumnDimension();
        int rows = A.getRowDimension();
        
        //The column; increases by one every iteration
        int[] chosenColumn = {0};
        //The starting position of the column; increases by one every iteration
        int startPos = 0;
        //The ending position of the column; decreases by one every iteration
        int endPos = rows - 1;
        
        ArrayList<Matrix> houseHolders = new ArrayList<>();
        
        Matrix B = A.copy();
        
        for (int i = 0; i < columns; i++) {

            //The identity container for the householder matrix
            Matrix cont = Matrix.identity(rows, rows);
            Matrix curMatrix = B.getMatrix(startPos, endPos, chosenColumn);
            Matrix u = calculateU(curMatrix);
            
            //From here, we need matrix multiply to find Hn = I - 2uuT
            Matrix houseNRaw = matrix_multiplication.multiply(u, u.transpose());
            Matrix ident = Matrix.identity(houseNRaw.getRowDimension(),
                    houseNRaw.getColumnDimension());
            Matrix houseN = ident.minus(houseNRaw.times(2));
            
            //Put the values into the container matrix
            cont.setMatrix(startPos, rows - 1, startPos, rows - 1, houseN);
            houseHolders.add(cont);
            B = matrix_multiplication.multiply(cont, B);
            chosenColumn[0] = i + 1;
            startPos++;
        }
        
        Matrix Q = Matrix.identity(rows, rows);
        
        for (Matrix i : houseHolders) {
            Q = matrix_multiplication.multiply(Q, i);
        }
        
        /*Jama.QRDecomposition real = new Jama.QRDecomposition(A);
        
        System.out.println("Q produced:");
        Q.print(2, 5);
        
        System.out.println("Q real:");
        real.getQ().print(2, 5);
        
        System.out.println("R produced:");
        B.print(2, 5);
        
        System.out.println("R real:");
        real.getR().print(2, 5);
        */
        Matrix[] QR = {Q, B};
        return QR;
    }
    /**
     * Takes in a 2-dimensional array of doubles, mutates this into a
     * Jama.Matrix, and then decomposes it into a Q matrix and an R matrix by
     * means of Householder Reflections.
     * 
     * @param A the given 2-dimensional array of doubles
     * @return an array of the two matrices, Q and R
     */
    public static Matrix[] factorize(double[][] A) {
        return factorize(new Matrix(A));
    }
 
    /**
     * Calculate the normal u vector that is required to reflect the matrix.
     * 
     * @param v the n X 1 matrix whose u vector is to be found
     * @return the u vector of the given matrix
     */
    public static Matrix calculateU(Matrix v) {
        //Create e1, the first vector of the standard basis
        Matrix e = Matrix.identity(v.getRowDimension(), 1);
        //Get the magnitude of the v matrix
        double rawMagnitude = 0;
        for (int i = 0; i < v.getRowDimension(); i++) {
            double value = v.get(i,0);
            rawMagnitude += (value * value);
        }
        double magnitude = Math.sqrt(rawMagnitude);
        
        Matrix eMutated = e.times(magnitude);
        Matrix vMutated = v.plus(eMutated);
        //Get the magnitude of the vMutated matrix
        rawMagnitude = 0;
        for (int j = 0; j < vMutated.getRowDimension(); j++) {
            double value = vMutated.get(j,0);
            rawMagnitude += (value * value);
        }
        magnitude = Math.sqrt(rawMagnitude);
        double magInverse = Math.pow(magnitude, -1.0);
        Matrix U = vMutated.times(magInverse);
        return U;
    }
    
}
