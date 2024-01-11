package applicationTools;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Utility class for managing JDBC connections and handling time conversions.
 */
public class JDBTools {

    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String MYSQLJDBToolsDriver = "com.mysql.cj.jdbc.Driver"; //referencing the driver
    private static final String jdbToolsURL = protocol + vendorName + ipAddress + databaseName + "?connectionTimeZone=SERVER";

    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";
    public static Connection connection = null;

    /**
     * Initiates the Data Base connection.
     * @return connection
     */
    public static Connection openConnection() {
        CChoulesDevTools.println("Connecting to data base using " + ipAddress + " ...");
        try {
            Class.forName(MYSQLJDBToolsDriver);
            connection = DriverManager.getConnection(jdbToolsURL, username, password);
            CChoulesDevTools.println("Now connected to: \n " + connection.getClientInfo()
                                                + "\n" + connection.getCatalog()
                                                + "\n" + connection.getSchema()
                                                + "\n" + connection.toString());
        } catch (Exception e) {
            CChoulesDevTools.println("Connection to Data Base failed.");
        }
        return connection;
    }

    /**
     * Method to return the current connection.
     * @return connection.
     */
    public static Connection getConnection() {

        return connection;
    }

    /**
     * Method to close connection.
     */
    public static void closeConnection() {
        try {
            connection.close();

            CChoulesDevTools.println("Connection Closed");

        } catch (SQLException exception) {
            CChoulesDevTools.println("Error: " + exception.getMessage());
            //TODO [c] check the difference between these two option ^v
            //Both are useful so include both. PrintStack shows more info.
            exception.printStackTrace();
        }

    }

    /**
     * Converts a Timestamp to LocalDateTime in UTC.
     * TODO [] This should be refactored/relocated to TimeTool
     * @param timestamp The input Timestamp.
     * @return The corresponding LocalDateTime in UTC.
     */
    public static LocalDateTime convertToUTC(Timestamp timestamp){
        ZonedDateTime zonedDateTime = ZonedDateTime.of(timestamp.toLocalDateTime(), ZoneId.systemDefault());
                //convertToTimeZoneTime(timestamp);
        return zonedDateTime.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }
    /**
     * Converts a LocalDateTime to UTC.
     * TODO [] This should be refactored/relocated to TimeTool
     * @param timestamp The input LocalDateTime.
     * @return The corresponding LocalDateTime in UTC.
     */
    public static LocalDateTime convertToUTC(LocalDateTime timestamp){
        ZonedDateTime zonedDateTime = ZonedDateTime.of(timestamp, ZoneId.systemDefault());
                //convertToTimeZoneTime(timestamp);
        return zonedDateTime.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }
    /**
     *  Converts a Timestamp from UTC to LocalDateTime.
     *  TODO [] This should be refactored/relocated to TimeTool
     * @param timestamp The input Timestamp in UTC.
     * @return The corresponding LocalDateTime.
     */
    public static LocalDateTime convertFromUTC(Timestamp timestamp){
        return ZonedDateTime.of(timestamp.toLocalDateTime(), ZoneId.of("UTC")).toOffsetDateTime().atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();

    }

}
