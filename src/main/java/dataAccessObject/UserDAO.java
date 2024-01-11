package dataAccessObject;

import applicationObject.User;
import applicationTools.CChoulesDevTools;
import applicationTools.JDBTools;
import main.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) class for handling operations related to
 * users in the database during login.
 */
public class UserDAO {


    //Missing Skill,
    //Skill Found: DAO Structure:
    //A DAO is often extending a parent class in the examples I have seen
    //so we will

    //TODO [c] create a method to validate login input.
    /**
     * Validates the login input.
     *
     * @param username The username to validate.
     * @param password The password to validate.
     * @return True if the login input is valid, false otherwise.
     */
    public static boolean validateUserLogin(String username,String password){
        try {

            //TODO [c] Vulnerable to SQL injection attack using string directly, correct this.
            //This is the query string that is vulnerable.
            //String sqlQuery = "SELECT * FROM users WHERE user_name = '" + username + "' AND password = '" + password +"'";
            //Missing Skill:
            //Skill found: SQL parameters
            //SQL parameters are indicated with ? inside SQL query strings. According to my
            //source the SQL engine handles parameters properly preventing SQL injection.
            //In addition, TODO [Extra] we should validate and sanitize user inputs throughout the project.
            //Attempt to correct:
            String sqlQuery = "SELECT * FROM users WHERE user_name = ? AND password = ?";

            //Prepares statement
            PreparedStatement preparedStatement = JDBTools.getConnection().prepareStatement(sqlQuery);
            //note: because prepareStatement contains SQLException try catch is required.

            //Setting sql parameters to prevent SQL injection.
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            //Worked, continue research on how to prevent other types of sql injection.

            ResultSet resultSet = preparedStatement.executeQuery();

            //checks to see if any results were stored in result set.
            if (! resultSet.next()) {
                CChoulesDevTools.println("No results found in Data Base using user input.");
                return false;
            }


            //TODO [Extra] create logic to check for duplicate accounts
//            int rowCount = 0;
//
//            while (resultSet.next()) {
//                // Designed to check for duplicates
//
//                // Increment the counter for each row
//                rowCount++;
//            }
//
//            if (rowCount>1){
//                System.out.println("Potential fatal error or attack, Account duplicate found, contact IT or DataBase Technician");
//                return false;
//            }

            if (resultSet.getString("User_Name").equals(username)){
                if (resultSet.getInt("User_ID") > -1){
                        CChoulesDevTools.println("validateUserLogin -> found and validated login input");
                        return true;
                }
            }

        } catch (SQLException exception) {
            CChoulesDevTools.println("Failed to validate login Input due to connection or error.");
            CChoulesDevTools.println(exception.toString());
            return false;
        }

        return false;

    }
    /**
     * Validates the login input and sets the User object. This is an overload.
     * @param username The username to validate.
     * @param password The password to validate.
     * @param user     The User object to set upon successful validation.
     * @return True if the login input is valid, false otherwise.
     */
    public static boolean validateUserLogin(String username,String password, User user){
        //overLoad method to set user along with validation
        try {
            String sqlQuery = "SELECT * FROM users WHERE " +
                    /*1*/"user_name = ? " +
                    "AND " +
                    /*2*/"password = ?";

            PreparedStatement preparedStatement = JDBTools.getConnection().prepareStatement(sqlQuery);

            //Setting sql parameters to prevent SQL injection.
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            //checks to see if any results were stored in result set.
            if (! resultSet.next()) {
                CChoulesDevTools.println("No results found in Data Base using user input.");
                return false;
            }


            //TODO [Extra] create logic to check for duplicate accounts; code found in under-load method
            String foundName = resultSet.getString("User_Name");
            int foundInt = resultSet.getInt("User_ID");

            if (foundName.equals(username)){ //This is just to double check user name match
                if (foundInt > -1){
                    CChoulesDevTools.println("validateUserLogin -> found and validated login input");
                    Main.currentUser.setName(foundName);
                    user.setName(foundName);
                    Main.currentUser.setId(foundInt);
                    user.setId(foundInt);
                    return true;
                }
            }
        } catch (SQLException exception) {
            CChoulesDevTools.println("Failed to validate login Input due to connection or error.");
            CChoulesDevTools.println(exception.toString());
            return false;
        }
        return false;

    }
    //TODO [c] Test this method in Login

}
