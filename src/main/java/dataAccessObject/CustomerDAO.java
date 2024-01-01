package dataAccessObject;

import applicationObject.Customer;
import applicationTools.CChoulesDevTools;
import applicationTools.JDBTools;
import controller.Reports;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDateTime;

public class CustomerDAO {

    public static ObservableList<Customer> getAllCustomers() throws SQLException {

        String query = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division, customers.Create_Date from customers INNER JOIN  first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";



        String queryForCountryName = "SELECT Country FROM countries WHERE Country_ID = (SELECT Country_ID FROM first_level_divisions WHERE Division_ID = ?)";

        PreparedStatement preparedStatement = JDBTools.getConnection().prepareStatement(query);
        PreparedStatement preparedStatementForCountryName = JDBTools.getConnection().prepareStatement(queryForCountryName);
        ResultSet resultSet = preparedStatement.executeQuery();

        ObservableList<Customer> customersObservableList = FXCollections.observableArrayList();

        //TODO [l]TRY Lambda expression here
        while (resultSet.next()) {
            int customerId = resultSet.getInt("Customer_ID");
            int divisionId = resultSet.getInt("Division_ID");
            String customerName = resultSet.getString("Customer_Name");
            String customerAddress = resultSet.getString("Address");
            String customerPostalCode = resultSet.getString("Postal_Code");
            String customerPhone = resultSet.getString("Phone");
            String divisionName = resultSet.getString("Division");
            preparedStatementForCountryName.setString(1, String.valueOf(divisionId));
            ResultSet resultSetCountry = preparedStatementForCountryName.executeQuery();

            LocalDateTime createDate = resultSet.getTimestamp("Create_Date").toLocalDateTime();

            String countryName = "No Country Found";
            while (resultSetCountry.next()) {
                countryName = resultSetCountry.getString("Country");
                //TODO [Extra] set a marker to check for multiple values in case of a bug
            }

            Customer customer = new Customer(
                            customerId,
                            customerName,
                            customerAddress,
                            customerPostalCode,
                            customerPhone,
                            divisionId,
                            divisionName,
                            countryName,
                            createDate
            );

            customersObservableList.add(customer);

        }

        return customersObservableList;

    }
    
    

}

