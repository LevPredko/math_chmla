package Project_3_5;

import java.util.Arrays;

public class LeastSquaresMethod {

    public static void main(String[] args) {
        // Визначення системи рівнянь
        double[][] A1 = {
                {15, -4, -3, 8},
                {-4, 10, -4, 2},
                {-3, -4, 10, 2},
                {8, 2, 2, 12}
        };
        double[] b1 = {2, -12, -4, 6};

        double[][] A2 = {
                {3, -8, 4},
                {-3, -1, 0},
                {4, 0, -4}
        };
        double[] b2 = {1, 2, 3};

        double tolerance = 1e-6; // Точність
        int maxIterations = 1000; // Максимальна кількість ітерацій

        System.out.println("Розв'язок для системи:");
        double[] solution1 = leastSquaresMethod(A1, b1, tolerance, maxIterations);
        System.out.println(Arrays.toString(solution1));

//        System.out.println("Розв'язок для другої системи:");
//        double[] solution2 = leastSquaresMethod(A2, b2, tolerance, maxIterations);
//        System.out.println(Arrays.toString(solution2));
    }

    public static double[] leastSquaresMethod(double[][] A, double[] b, double tolerance, int maxIterations) {
        int n = b.length;
        double[] x = new double[n]; // Початковий наближений розв'язок (нульовий вектор)
        double[] r = new double[n]; // Вектор залишків
        double[] Ar = new double[n]; // A * r

        for (int k = 0; k < maxIterations; k++) {
            // Обчислення залишків r = b - A * x
            r = subtract(b, multiply(A, x));

            // Перевірка на досягнення точності
            if (norm(r) < tolerance) {
                break;
            }

            // Обчислення A * r
            Ar = multiply(A, r);

            // Обчислення параметра t = (r * r) / (r * Ar)
            double t = dotProduct(r, r) / dotProduct(r, Ar);

            // Оновлення розв'язку x = x + t * r
            x = add(x, scale(r, t));
        }

        return x;
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

    public static double[] subtract(double[] vector1, double[] vector2) {
        int n = vector1.length;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = vector1[i] - vector2[i];
        }
        return result;
    }

    public static double[] add(double[] vector1, double[] vector2) {
        int n = vector1.length;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = vector1[i] + vector2[i];
        }
        return result;
    }

    public static double[] scale(double[] vector, double scalar) { // множення вектора на скаляр
        int n = vector.length;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = vector[i] * scalar;
        }
        return result;
    }

    public static double norm(double[] vector) { //обчислює евклідову норму(довжина чи модуль)
        double sum = 0;
        for (double v : vector) {
            sum += v * v;
        }
        return Math.sqrt(sum);
    }

    public static double dotProduct(double[] vector1, double[] vector2) { //обчислює скалярний добуток (або внутрішній добуток) двох векторів
        double sum = 0;
        for (int i = 0; i < vector1.length; i++) {
            sum += vector1[i] * vector2[i];
        }
        return sum;
    }
}
