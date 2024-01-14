package applicationTools;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
/**
 * Utility class for handling time-related operations in this specific application.
 */
public class TimeTool {

    private static final LocalTime businessStart = LocalTime.of(8, 0);
    private static final LocalTime businessEnd = LocalTime.of(22, 0);



    //To get times\\  //was having trouble am doing som quick code to try again
    private static final ZoneId hqTimeZone = ZoneId.of("America/New_York"); //Not fully implemented!!! (not global)
    private static final LocalTime businessEndAtEastern = LocalTime.of(22, 0);//Not fully implemented!!! (not global)
    private static final LocalTime businessStartAtEastern = LocalTime.of(8, 0);//Not fully implemented!!! (not global)
    /**
     * Converts the provided ZonedDateTime to the Time Zone of Business Head Quarters.
     * @param zonedDateTime The ZonedDateTime to convert.
     * @return The ZonedDateTime at HQ
     */
    public static ZonedDateTime getTimeAsHqTime(ZonedDateTime zonedDateTime){
        return zonedDateTime.withZoneSameInstant(hqTimeZone);
    }
    /**
     * Converts the provided LocalDateTime to the Time Zone of Business Head Quarters.
     * @param localDateTime The LocalDateTime to convert.
     * @return The ZonedDateTime at HQ
     */
    public static ZonedDateTime getTimeAsHqTime(LocalDateTime localDateTime){
        //Overloading to use localDateTime
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        return zonedDateTime.withZoneSameInstant(hqTimeZone);
    }

    /**
     * Gets the end of business hours in the local time zone.
     * @return The end of business hours as LocalTime.
     */
    public static LocalTime getEndOfHoursAsLocal(){
        ZonedDateTime currentDateTimeInEastern = ZonedDateTime.now(hqTimeZone);
        ZonedDateTime businessEndDateTimeInEastern = currentDateTimeInEastern.with(businessEndAtEastern);
        //// Convert
        ZonedDateTime businessEndDateTimeLocal = businessEndDateTimeInEastern.withZoneSameInstant(ZoneId.systemDefault());
        ////Extract time

        return businessEndDateTimeLocal.toLocalTime();
    }
    /**
     * Gets the end of business hours in the local time zone as a formatted string.
     * @return The end of business hours as a formatted string.
     */
    public static String getEndOfHoursAsLocalAsString(){
        return formatWithAMPM(getEndOfHoursAsLocal());
    }

    /**
     * Gets the start of business hours in the local time zone.
     * @return The start of business hours as LocalTime.
     */
    public static LocalTime getStartOfHoursAsLocal(){
        ZonedDateTime currentDateTimeInEastern = ZonedDateTime.now(hqTimeZone);
        ZonedDateTime businessStartDateTimeInEastern = currentDateTimeInEastern.with(businessStartAtEastern);
        //// Convert
        ZonedDateTime businessStartDateTimeLocal = businessStartDateTimeInEastern.withZoneSameInstant(ZoneId.systemDefault());
        ////Extract time

        return businessStartDateTimeLocal.toLocalTime();
    }

    /**
     * Gets the start of business hours in the local time zone as a formatted string.
     * @return The start of business hours as a formatted string.
     */
    public static String getStartOfHoursAsLocalAsString(){
        return formatWithAMPM(getStartOfHoursAsLocal());
    }

    /**
     * Formats a LocalTime with AM/PM indication.
     * @param localTime The LocalTime to format.
     * @return The formatted string.
     */
    public static String formatWithAMPM(LocalTime localTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return localTime.format(formatter);
    }
    //\\Above is redundant but I could not get this original object to work for the timers so I had to start over from the ground up//\\


    /**
     * Checks if the current time (in the system's default time zone) is within business hours.
     * @return True if the current time is within business hours; false otherwise.
     */
    public static boolean isBusinessHours() {
        return isBusinessHours(LocalDateTime.now());
    }
    /**
     * Checks if the given LocalDateTime is within business hours in the Eastern Time Zone.
     * TODO [] consolidate this into the above code and utilize constants for HQ time zone.
     * @param localDateTime The LocalDateTime to check.
     * @return True if the given time is within business hours; false otherwise.
     */
    public static boolean isBusinessHours(LocalDateTime localDateTime) {
        //Convert to Eastern Time Zone
        //LocalDateTime easternDateTime = localDateTime.atZone(ZoneId.systemDefault())
          //      .withZoneSameInstant(ZoneId.of("America/New_York"))
            //    .toLocalDateTime();

        LocalDateTime thisTimeAtUTC = TimeGetterTool.convertLocalToUtcTime(localDateTime);

        LocalDateTime thisTimeAtEast = TimeGetterTool.convertUtcToEasternTime(thisTimeAtUTC);

        //This is not used due to being open on weekends
//        //check if the day is a weekday
//        DayOfWeek dayOfWeek = easternDateTime.getDayOfWeek();
//        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
//            return false; // Weekend, not business hours
//        }

        //check if the time is within business hours 8 to 22
        LocalTime currentTime = thisTimeAtEast.toLocalTime();
        CChoulesDevTools.toolsOn();
        CChoulesDevTools.println(currentTime.toString());
        CChoulesDevTools.println(thisTimeAtUTC.toString());
        CChoulesDevTools.println(businessStart.toString() + businessEnd.toString());

        return currentTime.isAfter(businessStart) && currentTime.isBefore(businessEnd);
    }

    public static LocalDateTime getCurrentBusinessTime() {

        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("America/New_York"))
                .toLocalDateTime();
    }


    /**
     *Formats the given LocalDateTime as a readable string with a custom date-time formatter.
     * @param dateTime The LocalDateTime to format.
     * @return The formatted string.
     */
    public static String formatReadableDateTimeToString(LocalDateTime dateTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a MMM d, yyyy", Locale.ENGLISH);

        return dateTime.format(formatter);
    }

    /**
     * Sets up a label to display the current time in the Eastern Time Zone
     * using a timeline with a 1-second interval.
     * This uses a lambda to update the clock over each KeyFrame-interval.
     * @param label The Label to update with the current time.
     */
    public static void setLabelToHQClock(Label label){
        //Found an example of this and wanted to try it out even though it is out of scope

        int counter = 0;

        //try Lambda
        Timeline clock = new Timeline(
                new KeyFrame(Duration.ZERO, e -> updateDateTimeHQ(label)),
                new KeyFrame(Duration.seconds(1))
        );

        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    /**
     * Sets up a label to display the current time using a timeline with a one-second interval.
     * @param label The Label to update with the current time.
     */
    public static void setLabelToClock(Label label){

        int counter = 0;

        Timeline clock = new Timeline(
                new KeyFrame(Duration.ZERO, e -> updateDateTime(label)),
                new KeyFrame(Duration.seconds(1))
        );

        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
     /**
     * Formats the given ZonedDateTime as a string using a custom date-time formatter.
     * @param zonedDateTime The ZonedDateTime to  format.
     * @return The formatted string with MMM dd, yyyy hh:mm:ss a
     */
    public static String formatZonedDateTime(ZonedDateTime zonedDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm:ss a");
        return zonedDateTime.format(formatter);
    }


    /**
     * Gets a DateTimeFormatter for a full readable date-time format.
     * @return A DateTimeFormatter MM dd, yyyy hh:mm:ss a
     */
    public static DateTimeFormatter formatFullReadable() {
        return DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm:ss a");
    }

    //HELPER METHODS : PRIVATE\\
    private static void updateDateTimeHQ(Label label) {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(getCurrentBusinessTime(),ZoneId.of("America/New_York"));
        label.setText(formatZonedDateTime(zonedDateTime));
    }
    private static void updateDateTime(Label label) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        label.setText(formatZonedDateTime(zonedDateTime));
    }


}
