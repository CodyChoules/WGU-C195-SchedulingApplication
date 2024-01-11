package dataAccessObject;


import applicationObject.Country;
import applicationObject.FirstLvlDivision;
import applicationTools.JDBTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) class for handling operations related to
 * First Level Divisions in the database.
 */
public class FirstLvlDivisionDAO {

    /**
     * Retrieves all First Level Divisions from the database.
     * @return ObservableList containing all First Level Divisions.
     * @throws SQLException If a SQL exception occurs during data retrieval.
     */
    public static ObservableList<FirstLvlDivision> getAllFirstLvlDivisions() throws SQLException {
        ObservableList<FirstLvlDivision> firstLvlDivisionsObservableList = FXCollections.observableArrayList();
        String sqlFLD = "SELECT * from first_level_divisions";
        String sqlCountryName = "SELECT Country from client_schedule.countries WHERE ? = ?";

        PreparedStatement preparedStatementForFLD = JDBTools.getConnection().prepareStatement(sqlFLD);
        PreparedStatement preparedStatementForCountryNames = JDBTools.getConnection().prepareStatement(sqlCountryName);
        ResultSet resultSet = preparedStatementForFLD.executeQuery();
        while (resultSet.next()) {
            int divisionId = resultSet.getInt("Division_ID");
            String divisionName = resultSet.getString("Division");
            int countryIdOfDivision = resultSet.getInt("Country_ID");
            preparedStatementForCountryNames.setString(1, "Country_ID"); //TODO [] Test
            preparedStatementForCountryNames.setString(2, String.valueOf(countryIdOfDivision));
            ResultSet countryNameOfDivisionResults = preparedStatementForCountryNames.executeQuery();
            String countryNameOfDivision = countryNameOfDivisionResults.toString();
            FirstLvlDivision firstLvlDivision = new FirstLvlDivision(divisionId, divisionName, countryIdOfDivision,countryNameOfDivision);
            firstLvlDivisionsObservableList.add(firstLvlDivision);
        }
        return firstLvlDivisionsObservableList;
    }

    /**
     * Retrieves the names of all First Level Divisions.
     * @return ObservableList containing the names of all First Level Divisions.
     * @throws SQLException If a SQL exception occurs during data retrieval.
     */
    public static ObservableList<String> getFLD_Names() throws SQLException {
        //Get all First Level Division Names


        ObservableList<String> observableList = FXCollections.observableArrayList();

        for (FirstLvlDivision firstLvlDivision: getAllFirstLvlDivisions()) {
            observableList.add(firstLvlDivision.getDivisionName());
        }

        return observableList;
    }

    /**
     * Retrieves the ID of a First Level Division by its name.
     * @param name The name of the First Level Division.
     * @return The ID of the First Level Division.
     * @throws SQLException If a SQL exception occurs during data retrieval.
     */
    public static int getFLD_IdByName(String name) throws SQLException {


        ObservableList<String> observableList = FXCollections.observableArrayList();

        for (FirstLvlDivision firstLvlDivision: getAllFirstLvlDivisions()) {
            if (firstLvlDivision.getDivisionName().equals(name)) {
                return firstLvlDivision.getDivisionId();
            }
        }

        return 0;
    }

    /**
     * Retrieves the names of First Level Divisions by the country name.
     * @param countryName The name of the country.
     * @return ObservableList containing the names of First Level Divisions for the specified country.
     * @throws SQLException If a SQL exception occurs during data retrieval.
     */
    public static ObservableList<String> getFDL_NamesByCountry(String countryName) throws SQLException {
        //FDL : First_Level_Division (Country or state)


        ObservableList<String> fdlNames = FXCollections.observableArrayList();
        //TODO [Extra] Note: This is not like other objects or lists very confusing to initialize and not intuitive at all. Remember me.

        for (FirstLvlDivision fdl: FirstLvlDivisionDAO.getAllFirstLvlDivisions()) {
            if (fdl.getCountryNameOfDivision() == countryName)
                fdlNames.add(fdl.getDivisionName());
        }

        return fdlNames;
    }

}
