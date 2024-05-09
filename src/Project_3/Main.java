package Project_3;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
    static float maxDifference(float[] f, float[] s) {
        float difference = Math.abs(f[0] - s[0]);
        for (int i = 1; i < f.length; i++) {
            if (Math.abs(f[i] - s[i]) > difference) {
                difference = Math.abs(f[i] - s[i]);
            }
        }
        return difference;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Розмір матриці: ");
        int n = scanner.nextInt();
        float h = 1.0f / n;
        float kapa1 = 0;
        float mu1;
        float mu2;
        float[] a = new float[n + 1];
        float[] b = new float[n + 1];
        float[] c = new float[n + 1];
        float[] f = new float[n + 1];
        float[] xx = new float[n + 1];
        float[] yy = new float[n + 1];
        float[] y = new float[n + 1];
        a[0] = 0;
        a[n] = 0;
        b[0] = 0;
        b[n] = 0;
        c[0] = 0;
        c[n] = 0; // знач для коеф
        xx[0] = 0;
        xx[n] = 1;
        yy[0] = 1;
        yy[n] = 0.5f;
        y[0] = 1;
        y[n] = 0.5f; // знач для век
        for (int i = 1; i < n; i++) {
            a[i] = 1 / (h * h) + (1 + i * h) / (2 * h);
            b[i] = 1 / (h * h) - (1 + i * h) / (2 * h);
            c[i] = 1 + 2 / (h * h);
            f[i] = -2 / ((1 + i * h) * (1 + i * h) * (1 + i * h));
            xx[i] = i * h;
            yy[i] = 1 / (xx[i] + 1);
            y[i] = 0;
        }
        mu1 = y[0];
        mu2 = y[n];
        f[0] = mu1;
        f[n] = mu2;
        for (int i = 1; i < n + 1; i++) {
            System.out.println(a[i] + "\t" + b[i] + "\t" + c[i] + "\t" + f[i]);
        }
        float[] alpha = new float[n + 2];
        alpha[0] = 0;
        alpha[1] = kapa1;
        for (int i = 1; i < n + 1; i++) {
            alpha[i + 1] = b[i] / (c[i] - a[i] * alpha[i]);
        }
        float[] beta = new float[n + 2];
        beta[0] = 0;
        beta[1] = mu1;
        for (int i = 1; i < n + 1; i++) {
            beta[i + 1] = (f[i] + a[i] * beta[i]) / (c[i] - a[i] * alpha[i]);
        }

        for (int i = n - 1; i > 0; i--) {
            y[i] = alpha[i + 1] * y[i + 1] + beta[i + 1];
        }

        for (int i = 0; i < n + 1; i++) {
            System.out.println(y[i] + "\t|\t" + yy[i] + "\t|\t" + Math.abs(y[i] - yy[i]));
        }
        double maxDifference = maxDifference(y, yy);
        DecimalFormat df = new DecimalFormat("#.#########");
        String formatted = df.format(maxDifference);
        System.out.println("Максимальна різниця між точним і чисельним розв'язком: " + formatted);
    }
}
