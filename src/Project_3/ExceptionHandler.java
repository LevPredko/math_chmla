package Project_3;

class ExceptionHandler {
    public static void handleException(CustomException e) {
        System.err.println("An error occurred: " + e.getMessage());
    }
}