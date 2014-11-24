package rudiments;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Scrape {

    public static Object[] scrapeInformation() {
        Scanner input = new Scanner(System.in);
        
        //Get the points
        System.out.print("File Path: ");
        String fileName = input.next();
        System.out.println("\nPoints Found");
        File pointFile = new File(fileName);
        List<Double[]> points = scanFile(pointFile);
        System.out.println();
        
        //Get the initial guesses
        System.out.print("Guesses (format: a,b,c): ");
        String guessString = input.next();
        Scanner dScan = new Scanner(guessString);
        dScan.useDelimiter(",| ");
        Double a = Double.valueOf(dScan.next());
        Double b = Double.valueOf(dScan.next());
        Double c = Double.valueOf(dScan.next());
        Double[] guesses = {a, b, c};
        dScan.close();
        System.out.println();
        
        //Get the iterations
        System.out.print("Iterations: ");
        String iterationString = input.next();
        Integer iterations = Integer.valueOf(iterationString);
        System.out.println();
        
        //Choose the method
        Boolean householder = new Boolean(false);
        System.out.print("Householder or Givens? ");
        String houseString = input.next();
        houseString = houseString.toLowerCase();
        if (houseString.equals("householder") || houseString.equals("h")) {
            householder = Boolean.TRUE;
            System.out.println("Householder Reflections Chosen");
        } else {
            System.out.println("Givens Rotations Chosen");
        }
        
        //Close the input
        input.close();
        
        //Pack up the information
        Object[] pack = {points, guesses, iterations, householder};
        
        //Send it up
        return pack;
    }
    
    
    /**
     * Scans the given file for the points and the initial parameters.
     * 
     * @param input the file to scan
     * @return the points, and the initial parameters as the last element
     */
    public static List<Double[]> scanFile(File input) throws 
                                                       IllegalArgumentException{
        Scanner s = null;
        ArrayList<Double[]> points = new ArrayList<Double[]>();
        
        try {
            s = new Scanner(input);
        } catch (FileNotFoundException e) {
            //File not found, error
            throw new IllegalArgumentException("No File Found");
        }

        s.useDelimiter(",|\n|Initial parameters = \\(|\\)| ");
        Double d = null;

        
        while (s.hasNextDouble()) {
            //If this is the first in a set of points
            if (d == null) {
                d = s.nextDouble();
                System.out.print("(" + d + ", ");
            }
            Double h = null;
            
            try {
                h = s.nextDouble();
                System.out.println(h + ")");
            } catch (java.util.InputMismatchException i) {
                System.out.println("An Error Occurred within scanning."
                        + "Please confirm the points have been entered, or that"
                        + "the file is not malformed.");
                s.next();
            }
            Double[] set = {d, h};
            points.add(set);
            d = null;
        }
        
        s.close();
        
        return points;
        
    }
    
}
