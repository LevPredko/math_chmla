package Project_1;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {

        double[][] A = GaussianElimination.readMatrix("matrix.txt");
        double[] b = GaussianElimination.readVector("vector.txt");

        GaussianResult result = GaussianElimination.gauss(A, b);

        double[] x = result.x;
        double det_A = result.detA;

        double[] xRounded = Arrays.stream(x)
                .map(d -> Math.round(d * 10) / 10.0)
                .toArray();

        System.out.println("Розв'язок системи рівнянь: " + Arrays.toString(xRounded));
        System.out.println("Визначник матриці A: " + String.format("%.2f", det_A));
    }
}
