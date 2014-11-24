package rudiments;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * A class that holds a power iteration method algorithm.
 * This class also has a method which generates over a
 * thousand matrices and produces a scatter plot based
 * on their data.
 * Math 2605
 * @author Jayden Gardiner
 * @version 1.0
 */
public class power_method extends Application {
    
    private static List<Double> traceList = new ArrayList<Double>();
    private static List<Double> determinantList = new ArrayList<Double>();
    private static List<Double> iterationsList = new ArrayList<Double>();
    private static List<Double> inverseTraceList = new ArrayList<Double>();
    private static List<Double> inverseDeterminantList = new ArrayList<Double>();
    private static List<Double> inverseIterationsList = new ArrayList<Double>();
    private static Scanner keyboard = new Scanner(System.in);

    public static double[][] iterate(double[][] A, double[] v, double ε, int N) {
        
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
            
        } while (newEigenValue != 0 && N > 0 && Math.abs(newEigenValue - lastEigenValue) > ε);
        
        double numberOfIterations = originalN - N;
        double[][] returnValues = {{newEigenValue, numberOfIterations}, newEigenVector};
        
        if (newEigenValue == v[0]) {
            //0.0 == failure
            
            returnValues[0][0] = 0.0;
            returnValues[1][1] = originalN;
            return returnValues;
        }
        
        if (Math.abs(newEigenValue - lastEigenValue) > ε) {
            //0.0 == failure
            
            returnValues[0][0] = 0.0;
            return returnValues;
            
        } else {
            //for A inverse, 1/ eigenvalue = smallest eigenvalue of A
            return returnValues;
        }
    }
    
    public static void matrixGenerator() {
        Random rand = new Random();
        double[] initialGuess = {1,1};
        String data = "Running power method on over 1000 randomly generated matrices.\n\n";
        
        for (int i = 1; i < 1000 + rand.nextInt(1000); i++ ) {
            
            double[][] A = { {4 * (rand.nextDouble()) - 2, 4 * (rand.nextDouble()) - 2},
                    {4 * (rand.nextDouble()) - 2, 4 * (rand.nextDouble()) - 2}};
            double[][] Ainverse = inverse(A);
            String matrixName = "Matrix" + i;
            
            if (Ainverse != null) {
                
                data += matrixName + " is " + Arrays.deepToString(A) + "\n";
                data += matrixName + " inverse is " + Arrays.deepToString(Ainverse) + "\n";
                double[][] iteration = iterate(A, initialGuess, 0.00005, 100);
                iterationsList.add(iteration[0][1]);
                if (iteration[0][0] != 0.0) {
                    data += "The largest eigenvalue of A is " + iteration[0][0] + "\n";
                } else {
                    data += "Power method quit in failure.\n";
                }
                data += "Trace of " + matrixName + " is " + trace(A) + "\n";
                traceList.add(trace(A));
                data += "Determinant of " + matrixName + " is " + determinant(A) + "\n";
                determinantList.add(determinant(A));
                
                double[][] inverseIteration = iterate(Ainverse, initialGuess, 0.00005, 100);
                inverseIterationsList.add(inverseIteration[0][1]);
                if (inverseIteration[0][0] != 0.0) {
                    data += "The smallest eigenvalue of A is " + (1.0 / inverseIteration[0][0]) + "\n";
                } else {
                    data += "Power method quit in failure.\n";
                }
                data += "Trace of " + matrixName + " inverse is " + trace(Ainverse) + "\n";
                inverseTraceList.add(trace(Ainverse));
                data += "Determinant of " + matrixName + " inverse is " + determinant(Ainverse) + "\n\n";
                inverseDeterminantList.add(determinant(Ainverse));
                
            } else {
                
                boolean hasInverse = false;
                while (hasInverse == false) {
                    double[][] newA = { {4 * (rand.nextDouble()) - 2, 4 * (rand.nextDouble()) - 2},
                            {4 * (rand.nextDouble()) - 2, 4 * (rand.nextDouble()) - 2}};
                    double[][] newInverse = inverse(newA);
                    
                    if (newInverse != null) {
                        data += matrixName + " is " + Arrays.deepToString(newA) + "\n";
                        data += matrixName + " inverse is " + Arrays.deepToString(newInverse) + "\n";
                        double[][] iteration = iterate(newA, initialGuess, 0.00005, 100);
                        iterationsList.add(iteration[0][1]);
                        if (iteration[0][0] != 0.0) {
                            data += "The largest eigenvalue of A is " + iteration[0] + "\n";
                        } else {
                            data += "Power method quit in failure.\n";
                        }
                        data += "Trace of " + matrixName + " is " + trace(newA) + "\n";
                        traceList.add(trace(newA));
                        data += "Determinant of " + matrixName + " is " + determinant(newA) + "\n";
                        determinantList.add(determinant(newA));
                        
                        double[][] inverseIteration = iterate(newInverse, initialGuess, 0.00005, 100);
                        inverseIterationsList.add(inverseIteration[0][1]);
                        if (inverseIteration[0][0] != 0.0) {
                            data += "The smallest eigenvalue of A is " + (1.0 / inverseIteration[0][0]) + "\n";
                        } else {
                            data += "Power method quit in failure.\n";
                        }
                        data += "Trace of " + matrixName + " inverse is " + trace(newInverse) + "\n";
                        inverseTraceList.add(trace(newInverse));
                        data += "Determinant of " + matrixName + " inverse is " + determinant(newInverse) + "\n\n";
                        inverseDeterminantList.add(determinant(newInverse));
                        hasInverse = true;
                    }
                }
                
            }
        }
        launch();
        
        try {

            File file = new File("Matrix Generator Data.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
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
        
        //matrices graph
        
        stage.setTitle("Matrix Traces vs Determinants");
        final NumberAxis xAxis = new NumberAxis(-7, 7, 1);
        final NumberAxis yAxis = new NumberAxis(-7, 7, 1);        
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
        
        //inverse matrices graph
        
        final Stage stage2 = new Stage(StageStyle.UTILITY);
        stage2.setTitle("Matrix Inverse Traces vs Determinants");
        final NumberAxis x2Axis = new NumberAxis(-20, 20, 1);
        final NumberAxis y2Axis = new NumberAxis(-20, 20, 1);        
        final ScatterChart<Number,Number> sc2 = new ScatterChart<Number,Number>(x2Axis,y2Axis);
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
        System.out.println("Press 1 to run matrixGenerator and generate the data for over 1000 square matrices.\n");
        System.out.println("Press 2 to iterate through the power method using your own parameters.");
        int input = keyboard.nextInt();
        if (input == 1) {
            System.out.println("Check Matrix Generator Data.txt for a list of all the data recorded.");
            System.out.println("Graphs should appear showing data for both the matrices and inverse matrices.");
            matrixGenerator();
        } else {
            System.out.println("Declare a 2 x 2 matrix by typing in four doubles. Matrix will be generated as {{input1, input2}, {input3, input4}}'");
            double a = keyboard.nextDouble();
            double b = keyboard.nextDouble();
            double c = keyboard.nextDouble();
            double d = keyboard.nextDouble();
            double[][] matrix = {{a, b}, {c, d}};
            System.out.println("Declare your initial guess vector by typing in two doubles. Matrix will be generated as '{input1, input2}'");
            double e = keyboard.nextDouble();
            double f = keyboard.nextDouble();
            double[] initialGuess = {e, f};
            System.out.println("Declare your value ε as a double, such as 1.0");
            double ε = keyboard.nextDouble();
            System.out.println("Declare your number of iterations as an integer, such as 100");
            int N = keyboard.nextInt();
            double[][] iteration = iterate(matrix, initialGuess, ε, N);
            if (iteration[0][0] != 0.0) {
                System.out.println("Calculated largest eigenvalue is " + iteration[0][0]);
            } else {
                System.out.println("Power method quit in failure.");
            }
            if ((iterate(inverse(matrix), initialGuess, ε, N)[0][0]) != 0.0) {
                System.out.println("Calculated smallest eigenvalue is " + (1.0 / (iterate(inverse(matrix), initialGuess, ε, N)[0][0])));
            } else {
                System.out.println("Power method quit in failure.");
            }
            System.out.println("Calculated eigenvector is " + Arrays.toString(iteration[1]));
            System.out.println("Number of iterations: " + (int) iteration[0][1]);
        }
    }
}