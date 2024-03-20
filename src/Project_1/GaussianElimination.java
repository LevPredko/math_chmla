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

    public static GaussianResult gauss(double[][] a, double[] b) {
        int n = a.length;
        int[] p = new int[n]; // Масив для зберігання порядку рядків
        double det_A = 1.0;
        int count = 0;

        for (int i = 0; i < n; i++) {
            p[i] = i;
        }

        for (int i = 0; i < n; i++) {
            // Пошук максимального елемента в стовпці
            double max_a = Math.abs(a[p[i]][i]);
            int max_row = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(a[p[k]][i]) > max_a) {
                    max_a = Math.abs(a[p[k]][i]);
                    max_row = k;
                }
            }

            // Перестановка рядків
            int temp = p[i];
            p[i] = p[max_row];
            p[max_row] = temp;

            det_A *= a[p[i]][i];

            // Гаусівська елімінація
            for (int j = i + 1; j < n; j++) {
                double m = a[p[j]][i] / a[p[i]][i]; //коефіцієнт, на який потрібно помножити поточний рядок
                for (int k = i; k < n; k++) {
                    a[p[j]][k] -= m * a[p[i]][k];
                }
                count++;
                b[p[j]] -= m * b[p[i]];
            }
        }

        // Зворотній хід
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += a[p[i]][j] * x[j];
            }
            x[i] = (b[p[i]] - sum) / a[p[i]][i];
        }

        if(count % 2 == 0){
            count = -1;
        }else {
            count = 1;
        }
        det_A *= count;
        return new GaussianResult(x, det_A);
    }
//
//    public static void printMatrix(double[][] a, double[] b, int[] p) {
//        int n = a.length;
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                System.out.print(String.format("%.1f ", a[p[i]][j]));
//            }
//            System.out.print("| " + String.format("%.1f", b[p[i]]));
//            System.out.println();
//        }
//        System.out.println();
//    }
}
