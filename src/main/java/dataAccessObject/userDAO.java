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

            //This query
            String sqlQuery = "SELECT * FROM users WHERE user_name = '" + username + "' AND password = '" + password +"'";

            //Prepares statement
            PreparedStatement preparedStatement = JDBTools.getConnection().prepareStatement(sqlQuery);
            //note: because prepareStatement contains SQLException try catch is required.

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();


            if (resultSet.getString("User_Name").equals(username)){
                if (resultSet.getString("User_Name").equals(username)){
                    if (resultSet.getInt("User_ID") > -1){
                        DevToolC.println("validateUserLogin -> found and validated login input");
                        return true;
                    }
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }

        return false;

    }
    //TODO [c] Test this method in Login

}
