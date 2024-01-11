package applicationTools;


import java.util.function.UnaryOperator;

/**
 * Utility class containing tools for JavaFX applications created by Cody Choules.
 */
public class CChoulesJTools {
    //"Java Tools Cody Choules

    //TODO [Extra] create a method that turns an FX button red

    /**
     * Method to sanitize input to prevent SQL injection
     *
     * @param input The input string to be sanitized.
     * @return The sanitized input string.
     */
    public static String sanitizeInput(String input) {
        //TODO [c] practice lambda expression here - I example I followed used UnaryOperator
        //Review UnaryOperator...
        UnaryOperator<String> sanitizer = str -> str
                .replaceAll("\"", "")
                .replaceAll("'", "")
                .replaceAll(";", "")
                .replaceAll("\\\\" + "--", ""); //This regex not expected.

        String sanitizedInput = sanitizer.apply(input);

        if (sanitizedInput.contains("\"")) {
            CChoulesDevTools.println("Attempted login containing \", possible SQL injection with string: \n" + input);
        }

        CChoulesDevTools.println("Sanitized input " + input + " >-to-<" + sanitizedInput);
        return sanitizedInput;
    }

//    public static void main(String[] args) {
//        //main.Main for quick tests on tools.
//        CChoulesDevTools.toolsOn();
//        String input = "\"This is a test\"; with 'a SQL injection' \\-- attempt";
//        CChoulesDevTools.println(sanitizeInput(input));
//    }

}
