package applicationTools;
import java.time.LocalDateTime;
        import java.time.format.DateTimeFormatter;

public class LocalDateTimeApplicationTool {

    public static void main(String[] args) { //For Testing

        String dateString = "1-12/2023";
        String timeString = "14:30";

        LocalDateTime apStart = parseToLocalDateTime(dateString, timeString);

        System.out.println("Parsed & Converted LocalDateTime: " + apStart);
    }

    public static LocalDateTime parseToLocalDateTime(String dateString, String timeString) {
        dateString = replaceSlash(dateString);
        //TODO [c] create an if statement to call adjustDateFormat
        // Found: we can use String.matches to check string format \\d{x,y} means string decimal of length x or y.
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
    public static LocalDateTime parseToLocalDateTimeForDB(String dateString, String timeString) {
        dateString = replaceSlash(dateString);
        //TODO [c] create an if statement to call adjustDateFormat
        // Found: we can use String.matches to check string format \\d{x,y} means string decimal of length x or y.
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

    private static String adjustDateFormat(String dateString) {
        //TODO [c] find a way to format strings into date format
        // -Found: Works but am unsure how this regex works research further to solidify understanding.
        // -Research: "%02d" is a format specifier for an integer (d stands for decimal). It means that the integer should be formatted with at least 2 digits, and if it has fewer than 2 digits, leading zeros should be added. "%s" is any length string.
        String[] parts = dateString.split("-");
        return String.format("%02d-%02d-%s", Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2]);
    }

    private static String replaceSlash(String inputString) {
        return inputString.replace('/', '-');
    }


}

