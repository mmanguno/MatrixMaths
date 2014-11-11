package rudiments;

import Jama.Matrix;

/**
 * A class that holds methods that QR-factorize matrices by method of
 * Householder Reflections.
 * 
 * @author Mitchell Manguno
 * @version 1.0
 * @since 2014-November-10
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
        if (A.getColumnDimension() != A.getRowDimension() || null == A) {
            throw new IllegalArgumentException();
        }
        
        //The column; increases by one every iteration
        int[] chosenColumn = {0};
        //The starting position of the column; increases by one every iteration
        int startPos = 0;
        //The ending position of the column; decreases by one every iteration
        int endPos = A.getRowDimension() - 1;
        
        for (int i = 0; i < A.getColumnDimension(); i++) {
            Matrix u = calculateU(A.getMatrix(startPos, endPos, chosenColumn));
            Matrix ident = Matrix.identity(A.getRowDimension() - i,
                                                    A.getColumnDimension() - i);
            //From here, we need matrix multiply to find Hn = I - 2uuT
        }
        
        double[][] dummyQ = null;
        double[][] dummyR = null;
        Matrix Q = new Matrix(dummyQ);
        Matrix R = new Matrix(dummyR);
        Matrix[] QR = {Q, R};
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
        vMutated.print(2, 1);
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
