package applicationTools;

import java.sql.Timestamp;
import java.time.*;


/**
 * Utility class for handling time operations using time zones.
 */
public class TimeGetterTool {


    /**
     * converts given time to UTC from local
     * @param time time in local
     *
     * @return the UTC time
     */
    public static LocalDateTime convertLocalToUtcTime(LocalDateTime time) {
        ZoneId utcZone = ZoneId.of("UTC");

        ZonedDateTime zd = time.atZone(ZoneId.systemDefault());
        LocalDateTime utc = zd.toLocalDateTime().minusSeconds(zd.getOffset().getTotalSeconds());
        return utc;


    }

    /**
     * This is a method used to combat a bug in alerts where now is not coming up in testing properly.
     * @return Now
     */
    //A work around in attempt to correct a bug
    public static LocalDateTime currentTimeWithSystemDefault() {

        //
        LocalDateTime time = LocalDateTime.now();
        ZonedDateTime zd = time.atZone(ZoneId.systemDefault());
        LocalDateTime utc = zd.toLocalDateTime().minusSeconds(zd.getOffset().getTotalSeconds());
        return utc;

    }


    /**
     * Method to convert given time to Eastern Time where HQ is located from UTC TIME.
     * @param utcTime the input in UTC time
     * @return The time at HQ for given UTC time.
     */
    // Method to convert time to Eastern Time (ET)
    public static LocalDateTime convertUtcToEasternTime(LocalDateTime utcTime) {
        ZoneId easternTimeZone = ZoneId.of("America/New_York");

        ZonedDateTime zd = utcTime.atZone(easternTimeZone);
        LocalDateTime et = zd.toLocalDateTime().plusSeconds(zd.getOffset().getTotalSeconds());
        return et;
    }

    /**
     * A method to convert UTC time to local time.
     * @param utcTime Input in UTC.
     * @return Time for Input in local time.
     */
    // Method to convert time to Eastern Time (ET)
    public static LocalDateTime convertUtcToLocalTime(LocalDateTime utcTime) {
        ZoneId localZone = ZoneId.systemDefault();

        ZonedDateTime zd = utcTime.atZone(localZone);
        LocalDateTime et = zd.toLocalDateTime().plusSeconds(zd.getOffset().getTotalSeconds());
        return et;
    }

    public static void devToolPrint(){
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime utcTime = convertLocalToUtcTime(currentTime);
        LocalDateTime currentTimeFromUTC = convertUtcToLocalTime(utcTime);

        LocalDateTime easternTime = convertUtcToEasternTime(utcTime);

        CChoulesDevTools.println("Current Time Zone: " + ZoneId.systemDefault().getId());

        CChoulesDevTools.println("Eastern Time: " + TimeTool.formatReadableDateTimeToString(easternTime));


        CChoulesDevTools.println("UTC Time: " + TimeTool.formatReadableDateTimeToString(utcTime));
        CChoulesDevTools.println("UTC Time: " + utcTime);

        CChoulesDevTools.println("Local Time: " + TimeTool.formatReadableDateTimeToString(currentTime));
        CChoulesDevTools.println("Local Time: " + TimeTool.formatReadableDateTimeToString(currentTimeFromUTC));

        CChoulesDevTools.println("timestamp  " + Timestamp.valueOf(currentTime));
        CChoulesDevTools.println("timestamp  " + Timestamp.valueOf(easternTime));
        CChoulesDevTools.println("timestamp  " + Timestamp.valueOf(utcTime));
    }

    public static void main(String[] args) {
        // Example usage
        CChoulesDevTools.toolsOn();

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime utcTime = convertLocalToUtcTime(currentTime);
        LocalDateTime currentTimeFromUTC = convertUtcToLocalTime(utcTime);

        LocalDateTime easternTime = convertUtcToEasternTime(utcTime);

        CChoulesDevTools.println("Current Time Zone: " + ZoneId.systemDefault().getId());

        CChoulesDevTools.println("Eastern Time: " + TimeTool.formatReadableDateTimeToString(easternTime));


        CChoulesDevTools.println("UTC Time: " + TimeTool.formatReadableDateTimeToString(utcTime));
        CChoulesDevTools.println("UTC Time: " + utcTime);

        CChoulesDevTools.println("Local Time: " + TimeTool.formatReadableDateTimeToString(currentTime));
        CChoulesDevTools.println("Local Time: " + TimeTool.formatReadableDateTimeToString(currentTimeFromUTC));

        CChoulesDevTools.println("timestamp  " + Timestamp.valueOf(currentTime));
        CChoulesDevTools.println("timestamp  " + Timestamp.valueOf(easternTime));
        CChoulesDevTools.println("timestamp  " + Timestamp.valueOf(utcTime));
    }
}



