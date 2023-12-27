package dataAccessObject;

import applicationObject.Appointment;
import applicationTools.JDBTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class AppointmentDAO {

    /**
     * Observablelist for all appointments in database.
     * @throws SQLException
     * @return appointmentsObservableList
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
            LocalDateTime apStart = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime apEnd = resultSet.getTimestamp("End").toLocalDateTime();
            int apCustomerID = resultSet.getInt("Customer_ID");
            int apUserID = resultSet.getInt("User_ID");
            int apContactID = resultSet.getInt("Contact_ID");




            //Note: appointment now follows front end order
            Appointment appointment = new Appointment(apTitle, apType, apLocation, apID, apDescription, apStart, apEnd, apCustomerID, apContactID, apUserID);
            appointmentsObservableList.add(appointment);
        }

        return appointmentsObservableList;
    }

    public static int deleteAppointment(int customer, Connection connection) throws SQLException {
        String query = "DELETE FROM appointments WHERE Appointment_ID=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, customer);
        int result = ps.executeUpdate();
        ps.close();
        return result;
    }

    public static int updateAppointment(Appointment updatedAppointment, Connection connection) throws SQLException {
        String query = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, updatedAppointment.getApTitle());
        preparedStatement.setString(2, updatedAppointment.getApDescription());
        preparedStatement.setString(3, updatedAppointment.getApLocation());
        preparedStatement.setString(4, updatedAppointment.getApType());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(updatedAppointment.getApStart()));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(updatedAppointment.getApEnd()));
        preparedStatement.setInt(7, updatedAppointment.getApCustomerId());
        preparedStatement.setInt(8, updatedAppointment.getApUserId());
        preparedStatement.setInt(9, updatedAppointment.getApContactId());
        preparedStatement.setInt(10, updatedAppointment.getApId());

        int result = preparedStatement.executeUpdate();

        //Closing to not tie up DB Resources
        preparedStatement.close();
        return result;
    }

    public static int addAppointment(Appointment updatedAppointment, Connection connection) throws SQLException {
        String query = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, updatedAppointment.getApTitle());
        preparedStatement.setString(2, updatedAppointment.getApDescription());
        preparedStatement.setString(3, updatedAppointment.getApLocation());
        preparedStatement.setString(4, updatedAppointment.getApType());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(updatedAppointment.getApStart()));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(updatedAppointment.getApEnd()));
        preparedStatement.setInt(7, updatedAppointment.getApCustomerId());
        preparedStatement.setInt(8, updatedAppointment.getApUserId());
        preparedStatement.setInt(9, updatedAppointment.getApContactId());


        int result = preparedStatement.executeUpdate();

        //Closing to not tie up DB Resources
        preparedStatement.close();
        return result;
    }

    public static int generateUniqueId() {

        return 1;
    }



}

