package rudiments;

import java.util.Random;

import Jama.Matrix;

/**
 * Includes constructor that prints poweeeehhhhhhhhh
 * Math 2605
 * @author Jayden Gardiner
 * @version 1.0
 */
public class power_method {

    //multiply by vector
    //transpose

    public static double iterate(double[][] A, double[] v, double ε, int N) {
        
        //(A)(old eigenvector)(1 / eigenValue) = new eigenvector
        //eigenVector[0] = new eigenValue
        
        double[] lastEigenVector = v;
        double lastEigenValue = v[0];
        
        double[] newEigenVector = v;
        double newEigenValue = v[0];
        
        do {
            lastEigenVector = newEigenVector;
            lastEigenValue = newEigenValue;
            
            newEigenVector = matrix_multiplication.multiplyByVector(A, lastEigenVector);
            newEigenVector = matrix_multiplication.multiplyVectorbyScalar(newEigenVector, (1 / lastEigenValue));
            newEigenValue = newEigenVector[0];
            
            N--;
        } while (N >= 0 && Math.abs(newEigenValue - lastEigenValue) > ε);
        
        if (Math.abs(newEigenValue - lastEigenValue) > ε) {
            System.out.println("Power iteration quit in failure.");
            return 0.0;
        } else {
            System.out.println("Eigenvalue is " + newEigenValue);
            return newEigenValue;
        }
    }
    
    public static void matrixGenerator() {
        Random rand = new Random();
        //double[] initialGuess = {1,1};
        for (int i = 1; i < 1000 + rand.nextInt(1000); i++ ) {
            double[][] A = { {4 * (rand.nextDouble()) - 2, 4 * (rand.nextDouble()) - 2},
                    {4 * (rand.nextDouble()) - 2, 4 * (rand.nextDouble()) - 2}};
            double[][] Ainverse = inverse(A);
            String matrixName = "Matrix" + i;
            if (Ainverse != null) {
                //iterate(A, initialGuess, 0.00005, 100);
                System.out.println("Trace of " + matrixName + " is " + trace(A));
                System.out.println("Determinant of " + matrixName + " is " + determinant(A));
                //iterate(Ainverse, initialGuess, 0.00005, 100);
                System.out.println("Trace of " + matrixName + " inverse is " + trace(Ainverse));
                System.out.println("Determinant of " + matrixName + " inverse is " + determinant(Ainverse));
            } else {
                boolean hasInverse = false;
                while (hasInverse == false) {
                    double[][] newA = { {4 * (rand.nextDouble()) - 2, 4 * (rand.nextDouble()) - 2},
                            {4 * (rand.nextDouble()) - 2, 4 * (rand.nextDouble()) - 2}};
                    double[][] newInverse = inverse(newA);
                    if (newInverse != null) {
                        //iterate(newA, initialGuess, 0.00005, 100);
                        System.out.println("Trace of " + matrixName + " is " + trace(newA));
                        System.out.println("Determinant of " + matrixName + " is " + determinant(newA));
                        //iterate(newInverse, initialGuess, 0.00005, 100);
                        System.out.println("Trace of " + matrixName + " inverse is " + trace(newInverse));
                        System.out.println("Determinant of " + matrixName + " inverse is " + determinant(newInverse));
                        hasInverse = true;
                    }
                }
                
            }
            //record number of iterations needed for running power method on A and A inverse
        }
    }
    
    public static double determinant(double[][] A) {
        return (A[0][0] * A[1][1]) - (A[0][1] * A[1][0]);
      }
    
    public static double trace(double[][] A) {
        return A[0][0] + A[1][1];
    }
    
    public static double[][] inverse(double[][] A) {
        double determinant = determinant(A);
        if (determinant != 0) {
            double[][] Ainverse = {{A[1][1] / determinant, -A[0][1] / determinant},
                    {-A[1][0] / determinant, A[0][0] / determinant}};
            return Ainverse;
        } else {
            return null;
        }
    }
    
  //plot two colour-coded scatterplots
  //x-axis determinant
  //y-axis trace
  //second scatterplot for inverse
    
    public static void main(String[] args) {
        //matrixGenerator();
        double[][] hello = {{2, 1},{2, 1}};
        //double[][] helloi = inverse(hello);
        //System.out.println(Arrays.deepToString(helloi));
        double[] hay = {1,1};
        iterate(hello, hay, 0.00005, 100);
    }
}