package Project_3;

class OutputPrinter {
    public void printResult(double[] result) {
        for (int i = 0; i <= result.length - 1; i++) {
            System.out.println("y[" + i + "] = " + result[i]);
        }
    }
}