package Project_2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Orthogonalization {
    public static void main(String[] args) {
        try {
            double[][] coefficients = readMatrixFromFile("matrix.txt");
            double[] constants = readVectorFromFile("vector.txt");

            double[] solution = solveUsingOrthogonalization(coefficients, constants);

            System.out.println("Solution:");
            for (int i = 0; i < solution.length; i++) {
                System.out.println("x[" + i + "] = " + solution[i]);
            }
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
    }

    public static double[][] readMatrixFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        int rows = 0;
        int cols = 0;

        while ((line = reader.readLine()) != null) {
            rows++;
            String[] values = line.trim().split("\\s+");
            cols = values.length;
        }

        reader.close();
        reader = new BufferedReader(new FileReader(filename));

        double[][] matrix = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            line = reader.readLine();
            String[] values = line.trim().split("\\s+");
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = Double.parseDouble(values[j]);
            }
        }

        reader.close();
        return matrix;
    }

    public static double[] readVectorFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine().trim();
        String[] values = line.split("\\s+");
        double[] vector = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            vector[i] = Double.parseDouble(values[i]);
        }
        reader.close();
        return vector;
    }

    public static double[] solveUsingOrthogonalization(double[][] coefficients, double[] constants) {
        int n = constants.length;
        double[] solution = new double[n];
        double[][] q = new double[n][n];
        double[][] r = new double[n][n];
        // Основний цикл, який виконує ортогоналізацію Грама-Шмідта
        for (int j = 0; j < n; j++) {
            // Обчислення елементів матриці R та оновлення коефіцієнтів
            for (int i = 0; i < j; i++) {
                double dotProduct = 0;
                for (int k = 0; k < n; k++) {
                    dotProduct += q[k][i] * coefficients[k][j];
                }
                r[i][j] = dotProduct;
                for (int k = 0; k < n; k++) {
                    coefficients[k][j] -= r[i][j] * q[k][i];
                }
            }
            // Обчислення довжини вектора та нормалізація, щоб отримати новий вектор q
            double length = 0;
            for (int i = 0; i < n; i++) {
                length += coefficients[i][j] * coefficients[i][j];
            }
            length = Math.sqrt(length);

            for (int i = 0; i < n; i++) {
                q[i][j] = coefficients[i][j] / length;
            }
            r[j][j] = length;
        }
        // Обчислення проекції вектора b на кожен з векторів ортогонального базису
        double[] y = new double[n];
        for (int i = 0; i < n; i++) {
            double dotProduct = 0;
            for (int k = 0; k < n; k++) {
                dotProduct += q[k][i] * constants[k];
            }
            y[i] = dotProduct;
        }
        // Обернений хід методу Гауса для знаходження розв'язку
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += r[i][j] * solution[j];
            }
            solution[i] = (y[i] - sum) / r[i][i];
        }
        return solution;
    }
}