import org.junit.runner.*;
import org.junit.runner.notification.Failure;

/**
 * A test runner for the jUnit tests
 * This handles running the tests and printing a test summary to the screen
 */
public class TestRunner {
    // Colours
    static String ANSI_RESET = "\u001B[0m";
    static String ANSI_BLACK = "\u001B[30m";
    static String ANSI_RED = "\u001B[31m";
    static String ANSI_GREEN = "\u001B[32m";
    static String ANSI_YELLOW = "\u001B[33m";
    static String ANSI_BLUE = "\u001B[34m";
    static String ANSI_PURPLE = "\u001B[35m";
    static String ANSI_CYAN = "\u001B[36m";
    static String ANSI_WHITE = "\u001B[37m";

    /**
     * Executes the tests
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(SeleniumTest.class);
        generateReport(result);
    }

    /**
     * Generates a report on the screen to inform users of test results
     * @param result The result object from the jUnit tests
     */
    public static void generateReport(Result result) {
        System.out.println(ANSI_GREEN + "\n\n===================================================");
        System.out.println("    Junit Test Results");
        System.out.println("===================================================" + ANSI_RESET);
        System.out.println("Test summary:");
        System.out.println("    " + String.valueOf(result.getRunCount() + " tests ran"));
        System.out.println("    " + String.valueOf(result.getFailureCount()) + " tests failed");
        System.out.println("    " + String.valueOf(result.getIgnoreCount()) + " tests ignored");
        System.out.println("    Tests ran in " + String.valueOf(result.getRunTime()) + " milliseconds");

        System.out.println("");

        for (Failure fail : result.getFailures()) {
            System.out.println(fail.toString());
        }

        if (result.wasSuccessful()) {
            System.out.println("All tests passed");
        }
        System.out.println(ANSI_GREEN + "===================================================" + ANSI_RESET + "\n\n");
    }
}
