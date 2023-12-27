package dataAccessObject;

import applicationObject.Customer;
import applicationTools.JDBTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class CustomerDAO {

    public static ObservableList<Customer> getAllCustomers(Connection connection) throws SQLException {

        String query = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division from customers INNER JOIN  first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";

        String query2 = "SELECT cu.Customer_ID, cu.Customer_Name, cu.Address, cu.Postal_Code, "
                + "cu.Phone, cu.Division_ID, fld.Division FROM customers cu "
                + "INNER JOIN first_level_divisions fld ON cu.Division_ID = fld.Division_ID";

        PreparedStatement preparedStatement = JDBTools.getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        ObservableList<Customer> customersObservableList = FXCollections.observableArrayList();

        //TODO []TRY Lambda expression here
        while (resultSet.next()) {
            int customerId = resultSet.getInt("Customer_ID");
            int divisionId = resultSet.getInt("Division_ID");
            String customerName = resultSet.getString("Customer_Name");
            String customerAddress = resultSet.getString("Address");
            String customerPostalCode = resultSet.getString("Postal_Code");
            String customerPhone = resultSet.getString("Phone");
            String divisionName = resultSet.getString("Division");

            Customer customer = new Customer(
                            customerId,
                            customerName,
                            customerAddress,
                            customerPostalCode,
                            customerPhone,
                            divisionId,
                            divisionName
            );

            customersObservableList.add(customer);

        }

        return customersObservableList;

    }


}

