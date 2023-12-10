package dataAccessObject;

import devTools.DevToolC;
import devTools.JDBTools;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userDAO {


    //Missing Skill,
    //Skill Found: DAO Structure:
    //A DAO is often extending a parent class in the examples I have seen
    //so we will

    //TODO [c] create a method to validate login input.
    /**
     * This method validates the login input.
     * @param password
     * @param username
     */
    public static boolean validateUserLogin(String username,String password){
        try {

            //TODO [cu] Vulnerable to SQL injection attack using string directly, correct this.
            //This is the query string that is vulnerable.
            //String sqlQuery = "SELECT * FROM users WHERE user_name = '" + username + "' AND password = '" + password +"'";
            //Missing Skill:
            //Skill found: SQL parameters
            //SQL parameters are indicated with ? inside SQL query strings. According to my
            //source the SQL engine handles parameters properly preventing SQL injection.
            //In addition, TODO [] we should validate and sanitize user inputs throughout the project.
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
                DevToolC.println("No results found in Data Base using user input.");
                return false;
            }


            //TODO [] create logic to check for duplicate accounts
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
                        DevToolC.println("validateUserLogin -> found and validated login input");
                        return true;
                }
            }

        } catch (SQLException exception) {
            DevToolC.println("Failed to validate login Input due to connection or error.");
            DevToolC.println(exception.toString());
            return false;
        }

        return false;

    }
    //TODO [c] Test this method in Login

}
