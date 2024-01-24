package dataAccessObject;


import applicationObject.Appointment;
import applicationTools.CChoulesDevTools;
import applicationTools.JDBTools;
import applicationTools.TimeGetterTool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class AppointmentDAO {



    /**
     * Retrieves all appointments from the database.
     * @return ObservableList of all appointments.
     * @throws SQLException If a SQL exception occurs during data retrieval.
     */
    public static ObservableList<applicationObject.Appointment> getAllAppointments() throws SQLException {

        String sqlQuery = "SELECT * from client_schedule.appointments";

        ObservableList<Appointment> appointmentsObservableList = FXCollections.observableArrayList();

        JDBTools.openConnection();

        PreparedStatement preparedStatement = applicationTools.JDBTools.getConnection().prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            //Note: This order is not the same as front end but consistent with data base
            int apID = resultSet.getInt("Appointment_ID");
            String apTitle = resultSet.getString("Title");
            String apDescription = resultSet.getString("Description");
            String apLocation = resultSet.getString("Location");
            String apType = resultSet.getString("Type");
            String startString = resultSet.getString("Start");
            String endString = resultSet.getString("End");
            //LocalDateTime apStart = resultSet.getTimestamp("Start").toLocalDateTime();
            //to local date time is shifting my timestamp 2.5 hours down. Now 2.75, must be a db problem getting time string does not do this.
            //The 2.5 time shift makes no sense as I am testing it is -9 zone so it should be shifting 9 not 2
            //LocalDateTime apEnd = resultSet.getTimestamp("End").toLocalDateTime();
            int apCustomerID = resultSet.getInt("Customer_ID");
            int apUserID = resultSet.getInt("User_ID");
            int apContactID = resultSet.getInt("Contact_ID");

            //For some reason Time Stamp doesn't fall apart here
            LocalDateTime apStart = Timestamp.valueOf(startString).toLocalDateTime();
            LocalDateTime apEnd = Timestamp.valueOf(endString).toLocalDateTime();
            apStart = TimeGetterTool.convertUtcToLocalTime(apStart);
            apEnd = TimeGetterTool.convertUtcToLocalTime(apEnd);


            //Note: appointment now follows front end order
            Appointment appointment = new Appointment(apTitle, apType, apLocation, apID, apDescription, apStart, apEnd, apCustomerID, apContactID, apUserID);
            appointmentsObservableList.add(appointment);
        }

        return appointmentsObservableList;
    }
    /**
     * Retrieves all appointments for a specific customer from the database.
     * @param customerID The ID of the customer for whom to retrieve appointments.
     * @return ObservableList of appointments for the specified customer.
     * @throws SQLException If a SQL exception occurs during data retrieval.
     */
    public static ObservableList<applicationObject.Appointment> getAllAppointmentsByCustomer(int customerID) throws SQLException {


        ObservableList<Appointment> appointmentsObservableList = getAllAppointments();

        appointmentsObservableList.removeIf(appointment -> appointment.getApCustomerId() != customerID);

        return appointmentsObservableList;
    }

    /**
     * Deletes an appointment from the database.
     * @param appointmentId The ID of the appointment to delete.
     * @param connection The database connection.
     * @return The number of rows affected by the deletion.
     * @throws SQLException If a SQL exception occurs during deletion.
     */
    public static int deleteAppointment(int appointmentId, Connection connection) throws SQLException {
        String query = "DELETE FROM appointments WHERE Appointment_ID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, appointmentId);
        int result = preparedStatement.executeUpdate();
        preparedStatement.close();
        return result;
    }

    public static int updateAppointment(Appointment updatedAppointment, Connection connection) throws SQLException {
        String query = "UPDATE appointments SET " +
                /*1*/"Title=?, " +
                /*2*/"Description=?, " +
                /*3*/"Location=?, " +
                /*4*/"Type=?, " +
                /*5*/"Start=?, " +
                /*6*/"End=?, " +
                /*7*/"Customer_ID=?, " +
                /*8*/"User_ID=?, " +
                /*9*/"Contact_ID=?, " +
                /*10*/"Last_Update=?, " +
                /*11*/"Last_Updated_By=? " +
                /*12*/"WHERE Appointment_ID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, updatedAppointment.getApTitle());
        preparedStatement.setString(2, updatedAppointment.getApDescription());
        preparedStatement.setString(3, updatedAppointment.getApLocation());
        preparedStatement.setString(4, updatedAppointment.getApType());

        LocalDateTime apStart = updatedAppointment.getApStart();
        apStart = TimeGetterTool.convertLocalToUtcTime(apStart);
        LocalDateTime apEnd = updatedAppointment.getApEnd();
        apEnd = TimeGetterTool.convertLocalToUtcTime(apEnd);

        preparedStatement.setString(5,apStart.toString());
        preparedStatement.setString(6,apEnd.toString());
        preparedStatement.setInt(7, updatedAppointment.getApCustomerId());
        preparedStatement.setInt(8, updatedAppointment.getApUserId());
        preparedStatement.setInt(9, updatedAppointment.getApContactId());

        LocalDateTime utcUpDate = TimeGetterTool.convertLocalToUtcTime(LocalDateTime.now());

        preparedStatement.setTimestamp(10, Timestamp.valueOf(utcUpDate));
        preparedStatement.setString(11, main.Main.currentUser.getUserName());
        preparedStatement.setInt(12, updatedAppointment.getApId());

        int result = preparedStatement.executeUpdate();

        CChoulesDevTools.toolsOn();

        TimeGetterTool.devToolPrint();

        CChoulesDevTools.println("start " + updatedAppointment.getApStart().toLocalTime() + "-to->" + apStart.toLocalTime());
        CChoulesDevTools.println("E n d " + updatedAppointment.getApEnd().toLocalTime() + "-to->" + apEnd.toLocalTime());
        CChoulesDevTools.println("creat " + LocalDateTime.now().toLocalTime() + "-to->" + utcUpDate.toLocalTime());
        CChoulesDevTools.println("start as string " + apEnd.toString());




        CChoulesDevTools.toolsOff();

        //Closing to not tie up DB Resources
        preparedStatement.close();
        return result;
    }

    /**
     * Adds a new appointment to the database.
     * @param updatedAppointment The appointment to add.
     * @param connection The database connection.
     * @return The number of rows affected by the insertion.
     * @throws SQLException If a SQL exception occurs during insertion.
     */
    public static int addAppointment(Appointment updatedAppointment, Connection connection) throws SQLException {
        String query = "INSERT INTO appointments (" +
                /*1*/"Title, " +
                /*2*/"Description, " +
                /*3*/"Location, " +
                /*4*/"Type, " +
                /*5*/"Start, " +
                /*6*/"End, " +
                /*7*/"Customer_ID, " +
                /*8*/"User_ID, " +
                /*9*/"Contact_ID, " +
                /*10*/"Create_Date, " +
                /*11*/"Last_Update, " +
                /*12*/"Created_By, " +
                /*13*/"Last_Updated_By) " +
                "VALUES (?, ?, ?, ?, ?," +
                " ?, ?, ?, ?, ?," +
                " ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, updatedAppointment.getApTitle());
        preparedStatement.setString(2, updatedAppointment.getApDescription());
        preparedStatement.setString(3, updatedAppointment.getApLocation());
        preparedStatement.setString(4, updatedAppointment.getApType());

        LocalDateTime apStart = updatedAppointment.getApStart();
        apStart = TimeGetterTool.convertLocalToUtcTime(apStart);
        LocalDateTime apEnd = updatedAppointment.getApEnd();
        apEnd = TimeGetterTool.convertLocalToUtcTime(apEnd);

        preparedStatement.setString(5, apStart.toString());
        preparedStatement.setString(6, apEnd.toString());
        preparedStatement.setInt(7, updatedAppointment.getApCustomerId());
        preparedStatement.setInt(8, updatedAppointment.getApUserId());
        preparedStatement.setInt(9, updatedAppointment.getApContactId());

        LocalDateTime utcCreateDate = TimeGetterTool.convertLocalToUtcTime(LocalDateTime.now());


        preparedStatement.setString(10, utcCreateDate.toString());
        preparedStatement.setString(11, utcCreateDate.toString());
        preparedStatement.setString(12, main.Main.currentUser.getUserName());
        preparedStatement.setString(13, main.Main.currentUser.getUserName());


        int result = preparedStatement.executeUpdate();

        //Closing to not tie up DB Resources
        preparedStatement.close();
        return result;
    }
}

