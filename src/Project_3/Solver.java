package Project_3;

class Solver {
    public double[] solveEquations() throws CustomException {
        int n = 4;
        double[] a = new double[n];
        double[] b = new double[n];
        double[] c = new double[n];
        double[] f = new double[n];
        double[] y = new double[n + 1];

        a[0] = 0; b[0] = 1; c[0] = -1; f[0] = 0;
        a[1] = 0; b[1] = 1; c[1] = -2; f[1] = 0;
        a[2] = 0; b[2] = 1; c[2] = -2; f[2] = 0;
        a[3] = 0; b[3] = 1; c[3] = 0;  f[3] = 2;

        double[] alpha = new double[n];
        double[] beta = new double[n];
        alpha[0] = -c[0] / b[0];
        beta[0] = f[0] / b[0];

        for (int i = 1; i < n; i++) {
            double denominator = b[i] + a[i] * alpha[i - 1];
            if (denominator == 0) {
                throw new CustomException("Denominator is zero!");
            }
            alpha[i] = -c[i] / denominator;
            beta[i] = (f[i] - a[i] * beta[i - 1]) / denominator;
        }

        y[n] = (f[n - 1] - a[n - 1] * beta[n - 2]) / (b[n - 1] + a[n - 1] * alpha[n - 2]);
        for (int i = n - 1; i >= 0; i--) {
            y[i] = alpha[i] * y[i + 1] + beta[i];
        }
        return y;
    }
}