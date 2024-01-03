package dataAccessObject;


import applicationObject.Appointment;
import applicationTools.JDBTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class AppointmentDAO {


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
            LocalDateTime apStart = JDBTools.convertFromUTC(resultSet.getTimestamp("Start"));
            LocalDateTime apEnd = JDBTools.convertFromUTC(resultSet.getTimestamp("End"));;
            int apCustomerID = resultSet.getInt("Customer_ID");
            int apUserID = resultSet.getInt("User_ID");
            int apContactID = resultSet.getInt("Contact_ID");




            //Note: appointment now follows front end order
            Appointment appointment = new Appointment(apTitle, apType, apLocation, apID, apDescription, apStart, apEnd, apCustomerID, apContactID, apUserID);
            appointmentsObservableList.add(appointment);
        }

        return appointmentsObservableList;
    }

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
                /*9*/"Contact_ID=? " +
                /*10*/"Last_Update=? " +
                /*11*/"Last_Updated_By=? " +
                /*12*/"WHERE Appointment_ID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, updatedAppointment.getApTitle());
        preparedStatement.setString(2, updatedAppointment.getApDescription());
        preparedStatement.setString(3, updatedAppointment.getApLocation());
        preparedStatement.setString(4, updatedAppointment.getApType());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.ofInstant(Instant.from(updatedAppointment.getApStart().atZone(ZoneId.systemDefault())), ZoneId.of("Z"))));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(JDBTools.convertToUTC(updatedAppointment.getApEnd())));
        preparedStatement.setInt(7, updatedAppointment.getApCustomerId());
        preparedStatement.setInt(8, updatedAppointment.getApUserId());
        preparedStatement.setInt(9, updatedAppointment.getApContactId());



        preparedStatement.setTimestamp(10, Timestamp.valueOf(JDBTools.convertToUTC(LocalDateTime.now())));
        preparedStatement.setString(11, main.Main.currentUser.getUserName());
        preparedStatement.setInt(12, updatedAppointment.getApId());

        int result = preparedStatement.executeUpdate();

        //Closing to not tie up DB Resources
        preparedStatement.close();
        return result;
    }

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
        preparedStatement.setTimestamp(5, Timestamp.valueOf(JDBTools.convertToUTC(updatedAppointment.getApStart())));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(JDBTools.convertToUTC(updatedAppointment.getApEnd())));
        preparedStatement.setInt(7, updatedAppointment.getApCustomerId());
        preparedStatement.setInt(8, updatedAppointment.getApUserId());
        preparedStatement.setInt(9, updatedAppointment.getApContactId());
        preparedStatement.setTimestamp(10, Timestamp.valueOf(JDBTools.convertToUTC(LocalDateTime.now())));
        preparedStatement.setTimestamp(11, Timestamp.valueOf(JDBTools.convertToUTC(LocalDateTime.now())));
        preparedStatement.setString(12, main.Main.currentUser.getUserName());
        preparedStatement.setString(13, main.Main.currentUser.getUserName());


        int result = preparedStatement.executeUpdate();

        //Closing to not tie up DB Resources
        preparedStatement.close();
        return result;
    }
}

