package Project_4;

import java.util.*;

public class PowerIteration {
    private static final int SIZE = 10; // Розмір матриці
    private static final double RANGE = 10; // Діапазон значень у матриці
    private static final double EPS = 1e-7; // Точність
    private static int COUNTER; // Лічильник ітерацій

    // Генерація симетричної матриці з випадковими значеннями
    public static Double[][] generateMatrix() {
        Double[][] matrix = new Double[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = i + 1; j < SIZE; j++) {
                matrix[i][j] = Math.floor(Math.random() * 2 * RANGE - RANGE);
                matrix[j][i] = matrix[i][j];
            }
            matrix[i][i] = Math.floor(Math.random() * 2 * RANGE - RANGE);
        }
        return matrix;
    }

    // Ініціалізація вектора з одиницями
    public static Double[] getVector() {
        Double[] vector = new Double[SIZE];
        Arrays.fill(vector, 1.0);
        return vector;
    }

    // Множення матриці на вектор
    public static Double[] multiply(Double[][] matrix, Double[] y) {
        Double[] f = new Double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            f[i] = 0.0;
            for (int j = 0; j < matrix.length; j++) {
                f[i] += matrix[i][j] * y[j];
            }
        }
        return f;
    }

    // Множення двох матриць
    public static Double[][] multiply(Double[][] matrix1, Double[][] matrix2) {
        Double[][] result = new Double[matrix1.length][matrix2[0].length];
        for (Double[] doubles : result) {
            Arrays.fill(doubles, 0.0);
        }
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix2[i].length; j++) {
                for (int k = 0; k < matrix1[i].length; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }

    // Множення вектора на скаляр
    public static Double[] multiply(Double[] array, Double lambda) {
        Double[] result = array.clone();
        for (int i = 0; i < array.length; i++) {
            result[i] *= lambda;
        }
        return result;
    }

    // Скалярний добуток двох векторів
    public static Double multiply(Double[] vector, Double[] y) {
        double f = 0.0;
        for (int i = 0; i < vector.length; i++) {
            f += vector[i] * y[i];
        }
        return f;
    }

    // Множення матриці на косинус і синус
    public static Double[][] multiply(Double[][] matrix, int i, int j, double cos, double sin) {
        Double[][] result = matrix.clone();
        for (int k = 0; k < result.length; k++) {
            result[k] = matrix[k].clone();
        }

        for (int k = 0; k < result.length; k++) {
            result[k][i] = matrix[k][i] * cos + matrix[k][j] * sin;
            result[k][j] = matrix[k][j] * cos - matrix[k][i] * sin;
        }

        Double[][] result2 = result.clone();
        for (int k = 0; k < result2.length; k++) {
            result2[k] = result[k].clone();
        }

        for (int k = 0; k < result.length; k++) {
            result2[i][k] = result2[i][k] * cos + result2[j][k] * sin;
            result2[j][k] = result[j][k] * cos - result[i][k] * sin;
        }

        return result2;
    }

    // Віднімання двох векторів
    public static Double[] subtract(Double[] array1, Double[] array2) {
        Double[] result = array1.clone();
        for (int i = 0; i < array1.length; i++) {
            result[i] -= array2[i];
        }
        return result;
    }

    // Основний метод для знаходження власного значення і власного вектора
    public static Map<String, Object> solve1(Double[][] matrix, Double[] y) {
        Map<String, Object> map = new HashMap<>();
        COUNTER = 0;
        Double[] uk = multiply(y.clone(), 1 / getNorm(y));
        Double[] yk1 = multiply(matrix, uk);
        double lambda = multiply(yk1, uk);

        Double[] uk1 = multiply(yk1, 1 / getNorm(yk1));
        while (getNorm(residual(matrix, uk1, lambda)) > EPS) {
            if (COUNTER > 1000) {
                System.out.println("Ітераційний процес розходиться.");
                break;
            } else {
                COUNTER++;
                uk = multiply(yk1.clone(), 1 / getNorm(yk1));
                yk1 = multiply(matrix, uk);
                lambda = multiply(yk1, uk);
                uk1 = multiply(yk1, 1 / getNorm(yk1));
            }
        }
        map.put("lambda", lambda);
        map.put("vector", uk1);
        return map;
    }

    // Обчислення залишку
    public static Double[] residual(Double[][] matrix, Double[] u, Double lambda) {
        Double[] A1 = multiply(matrix, u);
        Double[] A2 = multiply(u, lambda);
        return subtract(A1, A2);
    }

    // Обчислення норми вектора
    public static Double getNorm(Double[] a) {
        return Math.sqrt(multiply(a, a));
    }

    // Метод для знаходження всіх власних значень і власних векторів
    public static Map<String, Object> solve2(Double[][] matrix) {
        Double[][] T = new Double[matrix.length][matrix.length];
        for (Double[] doubles : T) {
            Arrays.fill(doubles, 0.0);
        }
        Double[][] matrixCopy = matrix.clone();
        for (int i = 0; i < matrixCopy.length; i++) {
            matrixCopy[i] = matrix[i].clone();
            T[i][i] = 1.0;
        }
        COUNTER = 0;
        while (getSquareSum(matrixCopy) > EPS) {
            COUNTER++;
            int[] max = getIndicesOfMax(matrixCopy);
            double cos;
            double sin;
            if ((matrixCopy[max[0]][max[0]] - matrixCopy[max[1]][max[1]]) != 0) {
                double u = (2 * matrixCopy[max[0]][max[1]]) / (matrixCopy[max[0]][max[0]] - matrixCopy[max[1]][max[1]]);
                double sqr = Math.sqrt(1 + u * u);
                cos = Math.sqrt(1.0 / 2 * (1 + 1.0 / sqr));
                sin = Math.sqrt(1.0 / 2 * (1 - 1.0 / sqr)) * Math.signum(u);
            } else {
                cos = 1 / Math.sqrt(2);
                sin = -1 / Math.sqrt(2);
            }
            Double[][] t = getT(matrixCopy.length, max[0], max[1], cos, sin);
            T = multiply(T, t);
            matrixCopy = multiply(matrixCopy, max[0], max[1], cos, sin);
        }
        Double[] eigenvalues = new Double[matrixCopy.length];
        for (int i = 0; i < matrixCopy.length; i++) {
            eigenvalues[i] = matrixCopy[i][i];
        }
        Map<String, Object> result = new HashMap<>();
        result.put("eigenvalues", eigenvalues);
        result.put("eigenvectors", T);
        return result;
    }

    // Обчислення суми квадратів поза діагональних елементів матриці
    public static Double getSquareSum(Double[][] matrix) {
        double sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i != j) {
                    sum += matrix[i][j] * matrix[i][j];
                }
            }
        }
        return sum;
    }

    // Пошук індексів максимального за модулем елемента
    public static int[] getIndicesOfMax(Double[][] matrix) {
        int[] result = {0, 1};
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix.length; j++) {
                if (Math.abs(matrix[i][j]) > Math.abs(matrix[result[0]][result[1]])) {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        return result;
    }

    // Побудова матриці повороту
    public static Double[][] getT(int size, int i, int j, double cos, double sin) {
        Double[][] T = new Double[size][size];
        for (Double[] doubles : T) {
            Arrays.fill(doubles, 0.0);
        }
        for (int k = 0; k < T.length; k++) {
            T[k][k] = 1.0;
        }
        T[i][i] = cos;
        T[j][j] = cos;
        T[i][j] = -sin;
        T[j][i] = sin;
        return T;
    }

    // Виведення матриці на екран
    public static void print(Double[][] matrix) {
        for (Double[] doubles : matrix) {
            for (double v : doubles) {
                System.out.printf("%6.2f", v);
            }
            System.out.print(System.lineSeparator());
        }
    }

    // Виведення вектора на екран
    public static void print(Double[] vector) {
        System.out.print("(");
        for (double v : vector) {
            System.out.printf("%12.10f ", v);
        }
        System.out.print(")" + System.lineSeparator());
    }

    // Транспонування матриці
    public static Double[][] transpose(Double[][] matrix) {
        Double[][] result = matrix.clone();
        for (int i = 0; i < result.length; i++) {
            result[i] = matrix[i].clone();
        }
        for (int i = 0; i < result.length; i++) {
            for (int j = i + 1; j < result.length; j++) {
                double temp = result[i][j];
                result[i][j] = result[j][i];
                result[j][i] = temp;
            }
        }
        return result;
    }

    // Головна функція
    public static void main(String[] args) {
        // Генерація випадкової симетричної матриці
        Double[][] matrix = generateMatrix();
        // Ініціалізація вектора одиницями
        Double[] y0 = getVector();
        System.out.print("y0 = ");
        print(y0);
        System.out.println("A = ");
        print(matrix);

        // Розв'язок методом степеневого ітераційного процесу
        Map<String, Object> result = solve1(matrix, y0);
        System.out.println("Номер ітерації: " + COUNTER);
        System.out.println("Максимальне за модулем власне значення: " + result.get("lambda"));
        System.out.print("Відповідний власний вектор: ");
        print((Double[]) result.get("vector"));
        System.out.print("Au-lambda*u = ");
        print(residual(matrix, (Double[]) result.get("vector"), (double) result.get("lambda")));
        System.out.println("||Au - lambda*u|| = " + getNorm(residual(matrix, (Double[]) result.get("vector"), (double) result.get("lambda"))));
        System.out.println(System.lineSeparator());

        // Розв'язок методом Якобі для знаходження всіх власних значень і векторів
        Map<String, Object> result2 = solve2(matrix);
        System.out.println("Номер ітерації: " + COUNTER);
        Double[] eigenvalues = (Double[]) result2.get("eigenvalues");
        System.out.println("Власне значення: ");
        print(eigenvalues);
        Double[][] eigenvectors = transpose((Double[][]) result2.get("eigenvectors"));
        for (int i = 0; i < eigenvalues.length; i++) {
            System.out.print(eigenvalues[i] + " - ");
            print(eigenvectors[i]);
        }
        System.out.print(System.lineSeparator());
        for (int i = 0; i < eigenvalues.length; i++) {
            System.out.print("r" + (i + 1) + " = ");
            print(subtract(multiply(matrix, eigenvectors[i]), multiply(eigenvectors[i], eigenvalues[i])));
        }
    }
}
