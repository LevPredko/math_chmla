package Project_4;
import java.util.Arrays;

public class PowerMethod {

    public static void main(String[] args) {
        double[][] matrix = {
                {4, 1},
                {2, 3}
        };

        double[] eigenvector = {1, 1}; // Початковий вектор
        double tolerance = 1e-6;       // Точність
        int maxIterations = 1000;      // Максимальна кількість ітерацій

        double[] result = powerMethod(matrix, eigenvector, tolerance, maxIterations);
        System.out.println("Найбільше за модулем власне число: " + result[0]);
        System.out.println("Відповідний власний вектор: " + Arrays.toString(Arrays.copyOfRange(result, 1, result.length)));
    }

    public static double[] powerMethod(double[][] matrix, double[] vector, double tolerance, int maxIterations) {
        int n = matrix.length;
        double[] b = Arrays.copyOf(vector, vector.length);
        double eigenvalue = 0;

        for (int k = 0; k < maxIterations; k++) {
            double[] bNext = multiply(matrix, b);
            double norm = norm(bNext);
            double[] bNormalized = normalize(bNext, norm);

            double newEigenvalue = dotProduct(bNormalized, multiply(matrix, bNormalized));
            if (Math.abs(newEigenvalue - eigenvalue) < tolerance) {
                eigenvalue = newEigenvalue;
                break;
            }
            eigenvalue = newEigenvalue;
            b = bNormalized;
        }

        double[] result = new double[n + 1];
        result[0] = eigenvalue;
        System.arraycopy(b, 0, result, 1, n);
        return result;
    }

    public static double[] multiply(double[][] matrix, double[] vector) {
        int n = matrix.length;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = 0;
            for (int j = 0; j < n; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }

    public static double norm(double[] vector) {
        double sum = 0;
        for (double v : vector) {
            sum += v * v;
        }
        return Math.sqrt(sum);
    }

    public static double[] normalize(double[] vector, double norm) {
        int n = vector.length;
        double[] normalized = new double[n];
        for (int i = 0; i < n; i++) {
            normalized[i] = vector[i] / norm;
        }
        return normalized;
    }

    public static double dotProduct(double[] vector1, double[] vector2) {
        double sum = 0;
        for (int i = 0; i < vector1.length; i++) {
            sum += vector1[i] * vector2[i];
        }
        return sum;
    }
}
