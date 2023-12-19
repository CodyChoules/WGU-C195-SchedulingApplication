package dataAccessObject;

import applicationObject.Appointment;
import applicationTools.JDBTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");




            //Note: appointment now follows front end order
            Appointment appointment = new Appointment(apTitle, apType, apLocation, apID, apDescription, apStart, apEnd, customerID, contactID, userID);
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


}

