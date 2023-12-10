package devTools;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

//tools made for javaFX by Cody
public class FxToolsC {

    //TODO [Extra] create a method that turns an FX button red

    //Method to sanitize input to prevent SQL injection
    public static String sanitizeInput(String input) {
        // For simplicity, this example just removes single quotes
        String initInput = input;
        input = input.replaceAll("\"", "");
        input = input.replaceAll("'", "");
        input = input.replaceAll(";", "");
        input = input.replaceAll("\\\\" + "--", ""); //This regex not expected.
        String sanitizedInput = input;

        if (sanitizedInput.contains("\"")) {
            DevToolC.println("Attempted login containing \", possible sql injection with string: \n" + input);
        }

        DevToolC.println("Sanitized input " + initInput + " -> " + sanitizedInput);
        return sanitizedInput;

    }

}
