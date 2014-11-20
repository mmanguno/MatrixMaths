package rudiments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Jama.Matrix;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * A class that holds a power iteration method algorithm.
 * Math 2605
 * @author Jayden Gardiner
 * @version 1.0
 */
public class power_method extends Application {

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
        List traceList = new ArrayList<>();
        List determinantList = new ArrayList<>();
        Random rand = new Random();
        double[] initialGuess = {1,1};
        for (int i = 1; i < 1000 + rand.nextInt(1000); i++ ) {
            double[][] A = { {4 * (rand.nextDouble()) - 2, 4 * (rand.nextDouble()) - 2},
                    {4 * (rand.nextDouble()) - 2, 4 * (rand.nextDouble()) - 2}};
            double[][] Ainverse = inverse(A);
            String matrixName = "Matrix" + i;
            if (Ainverse != null) {
                System.out.println(matrixName + " is " + Arrays.deepToString(A));
                System.out.println(matrixName + " inverse is " + Arrays.deepToString(Ainverse));
                iterate(A, initialGuess, 0.00005, 100);
                System.out.println("Trace of " + matrixName + " is " + trace(A));
                traceList.add(trace(A));
                System.out.println("Determinant of " + matrixName + " is " + determinant(A));
                determinantList.add(determinant(A));
                iterate(Ainverse, initialGuess, 0.00005, 100);
                System.out.println("Trace of " + matrixName + " inverse is " + trace(Ainverse));
                System.out.println("Determinant of " + matrixName + " inverse is " + determinant(Ainverse) + "\n");
            } else {
                boolean hasInverse = false;
                while (hasInverse == false) {
                    double[][] newA = { {4 * (rand.nextDouble()) - 2, 4 * (rand.nextDouble()) - 2},
                            {4 * (rand.nextDouble()) - 2, 4 * (rand.nextDouble()) - 2}};
                    double[][] newInverse = inverse(newA);
                    if (newInverse != null) {
                        System.out.println(matrixName + " is " + Arrays.deepToString(newA));
                        System.out.println(matrixName + " inverse is " + Arrays.deepToString(newInverse));
                        iterate(newA, initialGuess, 0.00005, 100);
                        System.out.println("Trace of " + matrixName + " is " + trace(newA));
                        traceList.add(trace(newA));
                        System.out.println("Determinant of " + matrixName + " is " + determinant(newA));
                        determinantList.add(determinant(newA));
                        iterate(newInverse, initialGuess, 0.00005, 100);
                        System.out.println("Trace of " + matrixName + " inverse is " + trace(newInverse));
                        System.out.println("Determinant of " + matrixName + " inverse is " + determinant(newInverse) + "\n");
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
    
    @Override public void start(Stage stage) {
        stage.setTitle("Scatter Chart Sample");
        final NumberAxis xAxis = new NumberAxis(0, 10, 1);
        final NumberAxis yAxis = new NumberAxis(-100, 500, 100);        
        final ScatterChart<Number,Number> sc = new
            ScatterChart<Number,Number>(xAxis,yAxis);
        xAxis.setLabel("Determinants");                
        yAxis.setLabel("Traces");
        sc.setTitle("Matrix Traces vs Determinants");
       
        XYChart.Series series1 = new XYChart.Series();
        
        series1.setName("Equities");
        series1.getData().add(new XYChart.Data(4.2, 193.2));
        series1.getData().add(new XYChart.Data(2.8, 33.6));
        series1.getData().add(new XYChart.Data(6.2, 24.8));
        series1.getData().add(new XYChart.Data(1, 14));
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Mutual funds");
        series2.getData().add(new XYChart.Data(5.2, 229.2));
        series2.getData().add(new XYChart.Data(2.4, 37.6));
        series2.getData().add(new XYChart.Data(3.2, 49.8));

 
        sc.getData().addAll(series1, series2);
        Scene scene  = new Scene(sc, 500, 400);
        stage.setScene(scene);
        stage.show();
    }
    
  //plot two colour-coded scatterplots
  //x-axis determinant
  //y-axis trace
  //second scatterplot for inverse
    
    public static void main(String[] args) {
        //matrixGenerator();
        //double[][] hello = {{0, 1},{2, 1}};
        //double[][] helloi = inverse(hello);
        //System.out.println(Arrays.deepToString(helloi));
        //double[][] helloInverse = inverse(hello);
        //double[] hay = {1,1};
        //iterate(hello, hay, 0.00005, 100);
        //iterate(helloInverse, hay, 0.00005, 100);
        launch(args);
    }
}