package Project_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class GaussianElimination {
    public static double[][] readMatrix(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        return reader.lines()
                .map(line -> Arrays.stream(line.trim().split("\\s+"))
                        .mapToDouble(Double::parseDouble)
                        .toArray())
                .toArray(double[][]::new);
    }

    public static double[] readVector(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        return Arrays.stream(reader.readLine().trim().split("\\s+"))
                .mapToDouble(Double::parseDouble)
                .toArray();
    }

    public static void main(String[] args) throws IOException {

        double[][] A = readMatrix("matrix.txt");
        double[] b = readVector("vector.txt");

        GaussianResult result = gauss(A, b);

        double[] x = result.x;
        double det_A = result.detA;

        double[] xRounded = Arrays.stream(x)
                .map(d -> Math.round(d * 10) / 10.0)
                .toArray();

        System.out.println("Розв'язок системи рівнянь: " + Arrays.toString(xRounded));
        System.out.println("Визначник матриці A: " + String.format("%.2f", det_A));
    }

    public static GaussianResult gauss(double[][] a, double[] b) {
        int n = a.length;
        int[] p = new int[n];
        double det_A = 1.0;

        for (int i = 0; i < n; i++) {
            p[i] = i;
        }

        for (int i = 0; i < n; i++) {
            double max_a = 0.0;
            int max_j = i;
            for (int j = i; j < n; j++) {
                if (Math.abs(a[p[j]][i]) > max_a) {
                    max_a = Math.abs(a[p[j]][i]);
                    max_j = j;
                }
            }
            int temp = p[i];
            p[i] = p[max_j];
            p[max_j] = temp;

            det_A *= a[p[i]][i];

            System.out.println("Після вибору головного елементу на кроці " + (i + 1) + ":");
            printMatrix(a, b, p);

            for (int j = i + 1; j < n; j++) {
                a[p[j]][i] /= a[p[i]][i];
                for (int k = i + 1; k < n; k++) {
                    a[p[j]][k] -= a[p[i]][k] * a[p[j]][i];
                }
            }

            System.out.println("Після використання елементарних перетворень на кроці " + (i + 1) + ":");
            printMatrix(a, b, p);
        }

        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double s = 0;
            for (int j = i; j < n; j++) {
                s += a[p[i]][j] * x[j];
            }
            x[i] = (b[p[i]] - s) / a[p[i]][i];
        }

        return new GaussianResult(x, det_A);
    }

    public static void printMatrix(double[][] a, double[] b, int[] p) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(a[p[i]][j] + " ");
            }
            System.out.print("| " + b[p[i]]);
            System.out.println();
        }
        System.out.println();
    }

    static class GaussianResult {
        double[] x;
        double detA;

        GaussianResult(double[] x, double detA) {
            this.x = x;
            this.detA = detA;
        }
    }
}
