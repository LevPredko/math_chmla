package Project_3;

public class Main {
    public static void main(String[] args) {
        try {
            Solver solver = new Solver();
            double[] result = solver.solveEquations();
            OutputPrinter printer = new OutputPrinter();
            printer.printResult(result);
        } catch (CustomException e) {
            ExceptionHandler.handleException(e);
        }
    }
}
