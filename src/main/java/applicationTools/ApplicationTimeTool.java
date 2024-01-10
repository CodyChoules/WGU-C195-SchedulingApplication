package applicationTools;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ApplicationTimeTool {

    //Get current business hours of the eastern time to local time

    private static final LocalTime businessStart = LocalTime.of(8, 0);
    private static final LocalTime businessEnd = LocalTime.of(22, 0);



    //To get times\\  //was having trouble am doing som quick code to try again
    private static final ZoneId hqTimeZone = ZoneId.of("America/New_York"); //Not fully implemented!!! (not global)
    private static final LocalTime businessEndAtEastern = LocalTime.of(22, 0);//Not fully implemented!!! (not global)
    private static final LocalTime businessStartAtEastern = LocalTime.of(8, 0);//Not fully implemented!!! (not global)

    public static ZonedDateTime getTimeAsHqTime(ZonedDateTime zonedDateTime){
        return zonedDateTime.withZoneSameInstant(hqTimeZone);
    }
    public static ZonedDateTime getTimeAsHqTime(LocalDateTime localDateTime){
        //Overloading to use localDateTime
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        return zonedDateTime.withZoneSameInstant(hqTimeZone);
    }

    public static LocalTime getEndOfHoursAsLocal(){
        ZonedDateTime currentDateTimeInEastern = ZonedDateTime.now(hqTimeZone);
        ZonedDateTime businessEndDateTimeInEastern = currentDateTimeInEastern.with(businessEndAtEastern);
        //// Convert
        ZonedDateTime businessEndDateTimeLocal = businessEndDateTimeInEastern.withZoneSameInstant(ZoneId.systemDefault());
        ////Extract time

        return businessEndDateTimeLocal.toLocalTime();
    }
    public static String getEndOfHoursAsLocalAsString(){
        return formatWithAMPM(getEndOfHoursAsLocal());
    }
    public static LocalTime getStartOfHoursAsLocal(){
        ZonedDateTime currentDateTimeInEastern = ZonedDateTime.now(hqTimeZone);
        ZonedDateTime businessStartDateTimeInEastern = currentDateTimeInEastern.with(businessStartAtEastern);
        //// Convert
        ZonedDateTime businessStartDateTimeLocal = businessStartDateTimeInEastern.withZoneSameInstant(ZoneId.systemDefault());
        ////Extract time

        return businessStartDateTimeLocal.toLocalTime();
    }
    public static String getStartOfHoursAsLocalAsString(){
        return formatWithAMPM(getStartOfHoursAsLocal());
    }
    public static String formatWithAMPM(LocalTime localTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return localTime.format(formatter);
    }
    //\\Above is redundant but I could not get this original object to work for the timers so I had to start over from the ground up//\\





    //is time within business hours
    public static boolean isBusinessHours() {
        return isBusinessHours(LocalDateTime.now());
    }
    public static boolean isBusinessHours(LocalDateTime localDateTime) {
        //Convert to Eastern Time Zone
        LocalDateTime easternDateTime = localDateTime.atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("America/New_York"))
                .toLocalDateTime();

        //This is not used due to being open on weekends
//        //check if the day is a weekday
//        DayOfWeek dayOfWeek = easternDateTime.getDayOfWeek();
//        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
//            return false; // Weekend, not business hours
//        }

        //check if the time is within business hours 8 to 22
        LocalTime currentTime = easternDateTime.toLocalTime();

        return currentTime.isAfter(businessStart) && currentTime.isBefore(businessEnd);
    }

    public static LocalDateTime getCurrentBusinessTime() {

        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("America/New_York"))
                .toLocalDateTime();
    }



    private static String formatReadableDateTimeToString(LocalDateTime dateTime) {
        // Define a custom date-time formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a", Locale.ENGLISH);

        // Format the LocalDateTime using the custom formatter
        return dateTime.format(formatter);
    }


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
    private static void updateDateTimeHQ(Label label) {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(getCurrentBusinessTime(),ZoneId.of("America/New_York"));
        label.setText(formatZonedDateTime(zonedDateTime));
    }
    public static void setLabelToClock(Label label){

        int counter = 0;

        Timeline clock = new Timeline(
                new KeyFrame(Duration.ZERO, e -> updateDateTime(label)),
                new KeyFrame(Duration.seconds(1))
        );

        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    private static void updateDateTime(Label label) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        label.setText(formatZonedDateTime(zonedDateTime));
    }

    public static String formatZonedDateTime(ZonedDateTime zonedDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm:ss a");
        return zonedDateTime.format(formatter);
    }
    public static DateTimeFormatter formatFullReadable() {
        return DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm:ss a");
    }



}
