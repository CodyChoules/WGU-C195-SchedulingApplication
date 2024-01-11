package dataAccessObject;

import applicationObject.Country;
import applicationObject.FirstLvlDivision;
import applicationTools.CChoulesDevTools;
import applicationTools.JDBTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * Data Access Object (DAO) class for handling operations related to Country in the database.
 */
public class CountryDAO extends applicationObject.Country {
    //Constructor for CountryDAO
    public CountryDAO(int countryId, String countryName) {
        super(countryId, countryName);
    }

    /**
     * Retrieves all countries from the database.
     * @return ObservableList of Country objects.
     * @throws SQLException If a SQL exception occurs during data retrieval.
     */
    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> countryObservableList = FXCollections.observableArrayList();
        String sql = "SELECT Country_ID, Country from countries";
        String sqlForFDL = "SELECT Division FROM client_schedule.first_level_divisions where Country_ID = ?";
        ResultSet resultSet =  JDBTools.getConnection().prepareStatement(sql).executeQuery();
        PreparedStatement preparedStatementForFDL = JDBTools.getConnection().prepareStatement(sqlForFDL);

        //TODO [cu] update country & CountryDAO to include a list of child FDL names

        while (resultSet.next()) {
            String countryName = resultSet.getString("Country");
            int countryId = resultSet.getInt("Country_ID");
            CountryDAO country = new CountryDAO(countryId, countryName);
            preparedStatementForFDL.setString(1, String.valueOf(countryId));
            ObservableList<String> listOfCountryChildFLDs = FXCollections.observableArrayList();
            ResultSet resultSetFDL =  preparedStatementForFDL.executeQuery();
            while (resultSetFDL.next()) {
                listOfCountryChildFLDs.add(resultSetFDL.getString("Division"));
            }
            country.setChildFDLNames(listOfCountryChildFLDs);
            countryObservableList.add(country);

        }

        return countryObservableList;
    }

    /**
     * Retrieves the names of all countries from the database.
     * @return ObservableList of country names.
     * @throws SQLException If a SQL exception occurs during data retrieval.
     */
    public static ObservableList<String> getCountryNames() throws SQLException {
        //Get all country Names


        ObservableList<String> countryNames = FXCollections.observableArrayList();
        //TODO [Extra] Note: This is not like other objects or lists very confusing to initialize and not intuitive at all. Remember me.

        for (Country country: getAllCountries()) {
            countryNames.add(country.getCountryName());
        }

        return countryNames;
    }

    /**
     * Retrieves the names of all First Level Divisions (FDLs) for a given country from the database.
     * @param countryName The name of the country.
     * @return ObservableList of FDL names for the specified country.
     * @throws SQLException If a SQL exception occurs during data retrieval.
     */
    public static ObservableList<String> getFDLNamesByCountry(String countryName) throws SQLException {
        //FDL : First_Level_Division (Country or state)


        ObservableList<String> fdlNames = FXCollections.observableArrayList();
        //TODO [Extra] Note: This is not like other objects or lists very confusing to initialize and not intuitive at all. Remember me.

        for (FirstLvlDivision fdl: FirstLvlDivisionDAO.getAllFirstLvlDivisions()) {
            if (fdl.getCountryNameOfDivision() == countryName)
            fdlNames.add(fdl.getDivisionName());
        }

        return fdlNames;
    }

    /**
     * Retrieves a Country object by its name.
     * @param countryName The name of the country.
     * @return The Country object found by the specified name.
     * @throws SQLException If a SQL exception occurs during data retrieval.
     */
    public static Country getCountryByName(String countryName) throws SQLException {
        ObservableList<Country> listOfCountries = CountryDAO.getAllCountries();
        Country foundCountry = new Country(0, "NO COUNTRY FOUND");
        foundCountry.setChildFDLNames(FirstLvlDivisionDAO.getFLD_Names());

        for (Country findingCountry : listOfCountries){

            if (countryName != null && countryName.equalsIgnoreCase(findingCountry.getCountryName())){
                CChoulesDevTools.println("found: " + findingCountry.getCountryName());
                foundCountry = findingCountry;
            }

        }

        CChoulesDevTools.println("Returning:" + foundCountry);
        return foundCountry;

    }

}
