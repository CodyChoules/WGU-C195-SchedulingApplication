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
    private static final ZoneId easternTimeZone = ZoneId.of("America/New_York");
    private static final LocalTime businessEndAtEastern = LocalTime.of(22, 0);
    private static final LocalTime businessStartAtEastern = LocalTime.of(8, 0);

    public static LocalTime getEndOfHoursAsLocal(){
        ZonedDateTime currentDateTimeInEastern = ZonedDateTime.now(easternTimeZone);
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
        ZonedDateTime currentDateTimeInEastern = ZonedDateTime.now(easternTimeZone);
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

        LocalDateTime easternDateTime = localDateTime.atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("America/New_York"))
                .toLocalDateTime();

        return easternDateTime;

    }



    //what is the current time of buisness hours


    private static String formatReadableDateTime(LocalDateTime dateTime) {
        // Define a custom date-time formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a", Locale.ENGLISH);

        // Format the LocalDateTime using the custom formatter
        return dateTime.format(formatter);
    }

    //Test Code
    public static void main(String[] args) {

        LocalDateTime currentBusinessTime = getCurrentBusinessTime();
        CChoulesDevTools.println("Current Business Time: " + formatReadableDateTime(currentBusinessTime));
        CChoulesDevTools.println("Is within business hours? " + isBusinessHours(currentBusinessTime));

        LocalDateTime currentDateTime = LocalDateTime.now();

        String formattedDateTime = formatReadableDateTime(currentDateTime);

        CChoulesDevTools.println("Formatted Date and Time: " + formattedDateTime);
    }

    private Label dateTimeLabel = new Label();

    public static void setLabelToHQClock(Label label){

        int counter = 0;

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
