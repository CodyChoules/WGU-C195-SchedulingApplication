package controller;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Test {

    private static final ZoneId easternTimeZone = ZoneId.of("America/New_York");
    private static final LocalTime businessEndAtEastern = LocalTime.of(22, 0);

    public static void main(String[] args) {
        // Get the current date in Eastern Time
        ZonedDateTime currentDateTimeInEastern = ZonedDateTime.now(easternTimeZone);

        // Set the business end time in Eastern Time
        ZonedDateTime businessEndDateTimeInEastern = currentDateTimeInEastern.with(businessEndAtEastern);

        // Convert the business end time to local time
        ZonedDateTime businessEndDateTimeLocal = businessEndDateTimeInEastern.withZoneSameInstant(ZoneId.systemDefault());

        // Extract the LocalTime from the ZonedDateTime
        LocalTime businessEndAtLocal = businessEndDateTimeLocal.toLocalTime();

        System.out.println("Business End Time at Eastern Time: " + businessEndAtEastern);
        System.out.println("Business End Time at Local Time: " + businessEndAtLocal);
    }
}
