package rudiments;

import Jama.Matrix;
import java.util.ArrayList;


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
        //if (A.getColumnDimension() != A.getRowDimension() || null == A) {
        //    throw new IllegalArgumentException();
        //}
        
        //The column; increases by one every iteration
        int[] chosenColumn = {0};
        //The starting position of the column; increases by one every iteration
        int startPos = 0;
        //The ending position of the column; decreases by one every iteration
        int endPos = A.getRowDimension() - 1;
        
        ArrayList<Matrix> houseHolders = new ArrayList<>();
        
        Matrix B = A.copy();
        
        for (int i = 0; i < A.getColumnDimension() - 1; i++) {
            //The identity container for the householder matrix
            Matrix cont = Matrix.identity(A.getRowDimension(), 
                                                        A.getRowDimension());
            Matrix curMatrix = B.getMatrix(startPos, endPos, chosenColumn);
            System.out.println("CurMatrix:");
            curMatrix.print(2, 5);
            Matrix u = calculateU(curMatrix);
            
            //From here, we need matrix multiply to find Hn = I - 2uuT
            Matrix houseNRaw = matrix_multiplication.multiply(u, u.transpose());
            Matrix ident = Matrix.identity(houseNRaw.getRowDimension(),
                    houseNRaw.getColumnDimension());
            houseNRaw.print(2, 5);
            ident.print(2, 0);
            Matrix houseN = ident.minus(houseNRaw.times(2));
            houseN.print(2,5);
            //Put the values into the container matrix
            cont.setMatrix(startPos, A.getRowDimension() - 1, startPos, 
                                           A.getRowDimension() - 1, houseN);
            cont.print(2, 5);
            houseHolders.add(cont);
            B = matrix_multiplication.multiply(cont, B);
            chosenColumn[0] = chosenColumn[0]++;
            startPos++;
            //endPos--;
        }
        
        Matrix Q = Matrix.identity(A.getRowDimension(), A.getColumnDimension());
        
        for (Matrix i : houseHolders) {
            Q = matrix_multiplication.multiply(Q, i);
        }

        System.out.println("Q:");
        Q.print(2, 5);
        System.out.println("R:");
        B.print(2, 5);
        
        
        Jama.QRDecomposition qr = new Jama.QRDecomposition(A);
        System.out.println("Now...");
        qr.getQ().print(2, 5);
        qr.getR().print(2, 5);
        matrix_multiplication.multiply(qr.getQ(), qr.getR()).print(2, 0);
        
        //Matrix[] QR = {Q, B};
        //TODO: Change this back
        Matrix[] QR = {qr.getQ(), qr.getR()};
        matrix_multiplication.multiply(Q, B).print(2, 0);
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
