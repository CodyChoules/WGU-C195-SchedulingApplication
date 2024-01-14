package applicationTools;

import java.sql.Timestamp;
import java.time.*;

public class TimeGetterTool {


    // Method to convert time to Coordinated Universal Time (UTC)
    public static LocalDateTime convertLocalToUtcTime(LocalDateTime time) {
        ZoneId utcZone = ZoneId.of("UTC");

        ZonedDateTime zd = time.atZone(ZoneId.systemDefault());
        LocalDateTime utc = zd.toLocalDateTime().minusSeconds(zd.getOffset().getTotalSeconds());
        return utc;


    }



    // Method to convert time to Eastern Time (ET)
    public static LocalDateTime convertUtcToEasternTime(LocalDateTime utcTime) {
        ZoneId easternTimeZone = ZoneId.of("America/New_York");

        ZonedDateTime zd = utcTime.atZone(easternTimeZone);
        LocalDateTime et = zd.toLocalDateTime().plusSeconds(zd.getOffset().getTotalSeconds());
        return et;
    }

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



