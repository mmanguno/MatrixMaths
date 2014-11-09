package rudiments;

import Jama.Matrix;

public class Test {

    public static void main(String[] args) {
        double[][] vals = {{1.,2.,3},{4.,5.,6.},{7.,8.,10.}};
        Matrix alpha = new Matrix(vals);
        System.out.println(alpha);
    }
}
