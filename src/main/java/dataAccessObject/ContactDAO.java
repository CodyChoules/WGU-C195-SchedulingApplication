package dataAccessObject;

import applicationTools.CChoulesDevTools;
import applicationTools.JDBTools;
import applicationObject.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactDAO {

    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ObservableList<Contact> contactObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from contacts";
        PreparedStatement preparedStatement = JDBTools.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int conId = resultSet.getInt("Contact_ID");
            String contactEmail = resultSet.getString("Email");
            String contactName = resultSet.getString("Contact_Name");
            Contact contact = new Contact(conId , contactName, contactEmail);
            contactObservableList.add(contact);
        }
        return contactObservableList;
    }

    public static int findContactIdFromName(String contactName) throws SQLException {
        PreparedStatement preparedStatement = JDBTools.getConnection().prepareStatement("SELECT * FROM contacts WHERE Contact_Name = ?");
        preparedStatement.setString(1, contactName);
        ResultSet resultSet = preparedStatement.executeQuery();
        int contactId = 0;
        while (resultSet.next()) {
            contactId = resultSet.getInt("Contact_ID");
        }
        return contactId;
    }

    public static Contact findNameOfContactFromId(int contactId) throws SQLException {
            ObservableList<Contact> allContacts = getAllContacts();

            for (Contact contact : allContacts) {
                CChoulesDevTools.println(contact.toString());
                if (contact.getContactId() == contactId) {
                    return contact;
                }
            }

            return null;
    }
}
