package applicationTools;
import java.time.LocalDateTime;
        import java.time.format.DateTimeFormatter;

/**
 * Utility class for handling LocalDateTime operations in JavaFX applications.
 * TODO [] this was the first time class but I created a new one that does more, so this should be migrated to TimeTool
 */
public class LocalDateTimeApplicationTool {
    /**
     * Parses date and time strings and returns a LocalDateTime object.
     * @param dateString The date string to be parsed.
     * @param timeString The time string to be parsed.
     * @return LocalDateTime object representing the parsed date and time, or null if parsing fails.
     */
    public static LocalDateTime parseToLocalDateTime(String dateString, String timeString) {
        dateString = replaceSlash(dateString);
        if (dateString.matches("\\d{1,2}-\\d{1,2}-\\d{4}")) {
            dateString = adjustDateFormat(dateString);
        } else {
            CChoulesDevTools.println("Date input must be an acceptable format");
            return null;
        }
        String dateTimeString = dateString + " " + timeString;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        return LocalDateTime.parse(dateTimeString, formatter);
    }
    /**
     * Parses date and time strings for database storage.
     * @param dateString The date string to be parsed.
     * @param timeString The time string to be parsed.
     * @return LocalDateTime object representing the parsed date and time, or null if parsing fails.
     */
    public static LocalDateTime parseToLocalDateTimeForDB(String dateString, String timeString) {
        dateString = replaceSlash(dateString);
        if (dateString.matches("\\d{1,2}-\\d{1,2}-\\d{4}")) {
            dateString = adjustDateFormat(dateString);
        } else {
            CChoulesDevTools.println("Date input must be an acceptable format");
            return null;
        }
        String dateTimeString = dateString + " " + timeString;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        return LocalDateTime.parse(dateTimeString, formatter);
    }
    /**
     * Adjusts the date format to ensure consistent character format in the string
     * @param dateString The original date string.
     * @return The adjusted date string with leading zeros.
     */
    private static String adjustDateFormat(String dateString) {
        String[] parts = dateString.split("-");
        return String.format("%02d-%02d-%s", Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2]);
    }
    /**
     * Replaces slashes with hyphens in the input string for transition from input.
     * @param inputString The input string containing slashes.
     * @return The input string with slashes replaced by hyphens.
     */
    private static String replaceSlash(String inputString) {
        return inputString.replace('/', '-');
    }

}

