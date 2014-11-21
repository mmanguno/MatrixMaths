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
import javafx.stage.StageStyle;

/**
 * A class that holds a power iteration method algorithm.
 * Math 2605
 * @author Jayden Gardiner
 * @version 1.0
 */
public class power_method extends Application {
    
    private static List<Double> traceList = new ArrayList<Double>();
    private static List<Double> determinantList = new ArrayList<Double>();
    private static List<Integer> iterationsList = new ArrayList<Integer>();
    private static List<Double> inverseTraceList = new ArrayList<Double>();
    private static List<Double> inverseDeterminantList = new ArrayList<Double>();
    private static List<Integer> inverseIterationsList = new ArrayList<Integer>();

    public static int iterate(double[][] A, double[] v, double ε, int N) {
        
        //(A)(old eigenvector)(1 / eigenValue) = new eigenvector
        //eigenVector[0] = new eigenValue
        
        int originalN = N;
        
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
            
        } while (N > 0 && Math.abs(newEigenValue - lastEigenValue) > ε);
        
        if (Math.abs(newEigenValue - lastEigenValue) > ε) {
            System.out.println("Power iteration quit in failure.");
            return originalN - N;
        } else {
            //for A inverse, 1/ eigenvalue = smallest eigenvalue of A
            System.out.println("Eigenvalue is " + newEigenValue);
            return originalN - N;
        }
    }
    
    public static void matrixGenerator() {
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
                iterationsList.add(iterate(A, initialGuess, 0.00005, 100));
                System.out.println("Trace of " + matrixName + " is " + trace(A));
                traceList.add(trace(A));
                System.out.println("Determinant of " + matrixName + " is " + determinant(A));
                determinantList.add(determinant(A));
                inverseIterationsList.add(iterate(Ainverse, initialGuess, 0.00005, 100));
                System.out.println("Trace of " + matrixName + " inverse is " + trace(Ainverse));
                inverseTraceList.add(trace(Ainverse));
                System.out.println("Determinant of " + matrixName + " inverse is " + determinant(Ainverse) + "\n");
                inverseDeterminantList.add(determinant(Ainverse));
            } else {
                boolean hasInverse = false;
                while (hasInverse == false) {
                    double[][] newA = { {4 * (rand.nextDouble()) - 2, 4 * (rand.nextDouble()) - 2},
                            {4 * (rand.nextDouble()) - 2, 4 * (rand.nextDouble()) - 2}};
                    double[][] newInverse = inverse(newA);
                    if (newInverse != null) {
                        System.out.println(matrixName + " is " + Arrays.deepToString(newA));
                        System.out.println(matrixName + " inverse is " + Arrays.deepToString(newInverse));
                        iterationsList.add(iterate(newA, initialGuess, 0.00005, 100));
                        System.out.println("Trace of " + matrixName + " is " + trace(newA));
                        traceList.add(trace(newA));
                        System.out.println("Determinant of " + matrixName + " is " + determinant(newA));
                        determinantList.add(determinant(newA));
                        inverseIterationsList.add(iterate(newInverse, initialGuess, 0.00005, 100));
                        System.out.println("Trace of " + matrixName + " inverse is " + trace(newInverse));
                        inverseTraceList.add(trace(newInverse));
                        System.out.println("Determinant of " + matrixName + " inverse is " + determinant(newInverse) + "\n");
                        inverseDeterminantList.add(determinant(newInverse));
                        hasInverse = true;
                    }
                }
                
            }
        }
        launch();
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
        stage.setTitle("Matrix Traces vs Determinants");
        final NumberAxis xAxis = new NumberAxis(-5, 5, 1);
        final NumberAxis yAxis = new NumberAxis(-5, 5, 1);        
        final ScatterChart<Number,Number> sc = new ScatterChart<Number,Number>(xAxis,yAxis);
        xAxis.setLabel("Determinants");                
        yAxis.setLabel("Traces");
        sc.setTitle("Matrix Traces vs Determinants");

        double[] traceArray = new double[traceList.size()];
        for (int i = 0; i < traceList.size(); i++) {
            traceArray[i] = traceList.get(i);
        }
        double[] determinantArray = new double[determinantList.size()];
        for (int i = 0; i < determinantList.size(); i++) {
            determinantArray[i] = determinantList.get(i);
        }
        double[] iterationsArray = new double[iterationsList.size()];
        for (int i = 0; i < iterationsList.size(); i++) {
            iterationsArray[i] = iterationsList.get(i);
        }
        
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Less than 20 iterations -> Found eigenvalue");
        for (int i = 0; i < traceList.size(); i++) {
            if (iterationsArray[i] <= 20) {
                series1.getData().add(new XYChart.Data(determinantArray[i], traceArray[i]));
            }
        }
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("20 to 50 iterations -> Found eigenvalue");
        for (int i = 0; i < traceList.size(); i++) {
            if (iterationsArray[i] >= 20 && iterationsArray[i] <= 50) {
                series2.getData().add(new XYChart.Data(determinantArray[i], traceArray[i]));
            }
        }
        
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("More than 50 iterations -> Found eigenvalue");
        for (int i = 0; i < traceList.size(); i++) {
            if (iterationsArray[i] != 100 && iterationsArray[i] > 50) {
                series3.getData().add(new XYChart.Data(determinantArray[i], traceArray[i]));
            }
        }
        
        XYChart.Series series4 = new XYChart.Series();
        series4.setName("100 iterations -> Ended in failure");
        for (int i = 0; i < traceList.size(); i++) {
            if (iterationsArray[i] == 100) {
                series4.getData().add(new XYChart.Data(determinantArray[i], traceArray[i]));
            }
        }

        sc.getData().addAll(series1, series2, series3, series4);
        Scene scene  = new Scene(sc, 1000, 800);
        stage.setScene(scene);
        stage.show();
        
        
        
        final Stage stage2 = new Stage(StageStyle.UTILITY);
        stage2.setTitle("Matrix Inverse Traces vs Determinants");
        final NumberAxis x2Axis = new NumberAxis(-5, 5, 1);
        final NumberAxis y2Axis = new NumberAxis(-5, 5, 1);        
        final ScatterChart<Number,Number> sc2 = new ScatterChart<Number,Number>(xAxis,yAxis);
        x2Axis.setLabel("Determinants");                
        y2Axis.setLabel("Traces");
        sc2.setTitle("Matrix Inverse Traces vs Determinants");

        double[] inverseTraceArray = new double[inverseTraceList.size()];
        for (int i = 0; i < inverseTraceList.size(); i++) {
            inverseTraceArray[i] = inverseTraceList.get(i);
        }
        double[] inverseDeterminantArray = new double[inverseDeterminantList.size()];
        for (int i = 0; i < inverseDeterminantList.size(); i++) {
            inverseDeterminantArray[i] = inverseDeterminantList.get(i);
        }
        double[] inverseIterationsArray = new double[inverseIterationsList.size()];
        for (int i = 0; i < inverseIterationsList.size(); i++) {
            inverseIterationsArray[i] = inverseIterationsList.get(i);
        }
        
        XYChart.Series series5 = new XYChart.Series();
        series5.setName("Less than 20 iterations -> Found eigenvalue");
        for (int i = 0; i < inverseTraceList.size(); i++) {
            if (inverseIterationsArray[i] <= 20) {
                series5.getData().add(new XYChart.Data(inverseDeterminantArray[i], inverseTraceArray[i]));
            }
        }
        
        XYChart.Series series6 = new XYChart.Series();
        series6.setName("20 to 50 iterations -> Found eigenvalue");
        for (int i = 0; i < inverseTraceList.size(); i++) {
            if (inverseIterationsArray[i] >= 20 && inverseIterationsArray[i] <= 50) {
                series6.getData().add(new XYChart.Data(inverseDeterminantArray[i], inverseTraceArray[i]));
            }
        }
        
        XYChart.Series series7 = new XYChart.Series();
        series7.setName("More than 50 iterations -> Found eigenvalue");
        for (int i = 0; i < inverseTraceList.size(); i++) {
            if (inverseIterationsArray[i] != 100 && inverseIterationsArray[i] > 50) {
                series7.getData().add(new XYChart.Data(inverseDeterminantArray[i], inverseTraceArray[i]));
            }
        }
        
        XYChart.Series series8 = new XYChart.Series();
        series8.setName("100 iterations -> Ended in failure");
        for (int i = 0; i < inverseTraceList.size(); i++) {
            if (inverseIterationsArray[i] == 100) {
                series8.getData().add(new XYChart.Data(inverseDeterminantArray[i], inverseTraceArray[i]));
            }
        }
        
        sc2.getData().addAll(series5, series6, series7, series8);
        Scene scene2  = new Scene(sc2, 1000, 800);
        stage2.setScene(scene2);
        stage2.show();
        
        traceList.clear();
        determinantList.clear();
        iterationsList.clear();
        inverseTraceList.clear();
        inverseDeterminantList.clear();
        inverseIterationsList.clear();
    }
    
    public static void main(String[] args) {
        //matrixGenerator();
        double[][] hello = {{5, 3},{4, 2}};
        //double[][] helloi = inverse(hello);
        //System.out.println(Arrays.deepToString(helloi));
        double[][] helloInverse = inverse(hello);
        double[] hay = {1,1};
        iterate(hello, hay, 0.00005, 100);
        System.out.println(Arrays.deepToString(helloInverse));
        iterate(helloInverse, hay, 0.00005, 100);
        
    }
}