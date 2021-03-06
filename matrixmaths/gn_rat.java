import java.util.List;

import Jama.Matrix;
import rudiments.Scrape;
import rudiments.matrix_multiplication;
import rudiments.qr_fact_givens;
import rudiments.qr_fact_househ;

public class gn_rat {

    /**
     * The main method.  Performs the Gauss-Newton iteration method in order to
     * find the exponential curve of best fit to the given points.
     *
     * @param args The name of the file containing the data points.
     */
    public static void main(String[] args) {
        //Scrape returns exactly these types; casting is safe
        Object[] info = Scrape.scrapeInformation();

        @SuppressWarnings("unchecked")
        List<Double[]> points = (List<Double[]>) info[0];
        Double[] guesses = (Double[]) info[1];
        Integer iterations = (Integer) info[2];
        Boolean householder = (Boolean) info[3];

        Double[] curve = gaussNewton(points, guesses, iterations.intValue(),
                                                    householder.booleanValue());

        System.out.println("\nParameters:");
        System.out.println("a: " + curve[0]);
        System.out.println("b: " + curve[1]);
        System.out.println("c: " + curve[2]);

    }

    private static Double[] gaussNewton(List<Double[]> points, Double[] guesses,
                                          int iterations, boolean householder) {

        int iter = 0;
        
        
        while (iter < iterations) {

            //The Jacobian needs to be calculated each time

            Matrix jacobian = new Matrix(points.size(), 3);

            double[][] residuals = new double[points.size()][1];

            double a = guesses[0];
            double b = guesses[1];
            double c = guesses[2];
            
            for (int i = 0; i < points.size(); i++) {
                double x = points.get(i)[0];
                //Change of equation here
                double[][] raw = {{-(x / (x + b)),
                                   ((a*x) / Math.pow((b + x), 2)),
                                   -1.0}};
                Matrix rowN = new Matrix(raw);
                jacobian.setMatrix(i, i, 0, 2, rowN);
            }

            //QR decompose the Jacobian
            Matrix[] QR = householder ? qr_fact_househ.factorize(jacobian):
                                        qr_fact_givens.factorize(jacobian);
            
            Matrix Q = QR[0];
            Matrix R = QR[1];

            //Q may be simplified into an n X 3 matrix
            Q = Q.getMatrix(0, Q.getRowDimension() - 1, 0, 2);
            //R may be simplified into a 3 x 3 matrix
            R = R.getMatrix(0, 2, 0, 2);
            
            //Get the residual vector

            for (int i = 0; i < points.size(); i++) {
                double x = points.get(i)[0];
                double y = points.get(i)[1];

                //Change of equation here
                residuals[i][0] = y - (((a*x) / (x + b)) + c);
            }

            Matrix resid = new Matrix(residuals);

            //Form the right hand side of the equation

            Matrix rHS = matrix_multiplication.multiply(Q.transpose(), resid);

            //back substitute
            //it's only a 3 x 3, so you can manually do it

            Double[] solute = new Double[3];

            solute[2] = ((rHS.get(2, 0))
                         / R.get(2, 2));

            solute[1] = ((rHS.get(1, 0) - (R.get(1, 2) * solute[2]))
                         / R.get(1,1));

            solute[0] = ((rHS.get(0, 0) - (R.get(0, 2) * solute[2])
                                        - (R.get(0, 1) * solute[1]))
                         / R.get(0, 0));

            guesses[0] = guesses[0] - solute[0];
            guesses[1] = guesses[1] - solute[1];
            guesses[2] = guesses[2] - solute[2];

            iter++;
        }
        return guesses;
    }

}
