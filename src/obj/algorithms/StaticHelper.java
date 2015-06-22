package obj.algorithms;

/**
 * A helper class that does mathematical function
 *
 * @author Kareem Horstink
 * @version 1.0
 */
public class StaticHelper {

    /**
     * Subtracts 2 vector
     *
     * @param a Limited to 3 array
     * @param b Limited to 3 array
     * @return The results
     */
    public static double[] substract(double[] a, double[] b) {
        double[] c = new double[3];
        c[0] = a[0] - b[0];
        c[1] = a[1] - b[1];
        c[2] = a[2] - b[2];

        return c;
    }

    /**
     * Cross product 2 vector
     *
     * @param a Limited to 3 array
     * @param b Limited to 3 array
     * @return The results
     */
    public static double[] crossProduct(double[] a, double[] b) {
        double[] c = new double[3];
        c[0] = a[1] * b[2] - a[2] * b[1];
        c[1] = a[2] * b[0] - a[0] * b[2];
        c[2] = a[0] * b[1] - a[1] * b[0];
        return c;
    }

    /**
     * Gives the magnitude of a vector 3D
     *
     * @param a The vector in 3D (R3)
     * @return The magnitude of the vector
     */
    public static double mag(double[] a) {
        return Math.sqrt(Math.pow(a[0], 2) + Math.pow(a[1], 2) + Math.pow(a[2], 2));
    }
}
