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

public class CountryDAO extends applicationObject.Country {
    //TODO [l] TEST ME
    public CountryDAO(int countryId, String countryName) {
        super(countryId, countryName);
    }

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

    public static ObservableList<String> getCountryNames() throws SQLException {
        //Get all country Names


        ObservableList<String> countryNames = FXCollections.observableArrayList();
        //TODO [Extra] Note: This is not like other objects or lists very confusing to initialize and not intuitive at all. Remember me.

        for (Country country: getAllCountries()) {
            countryNames.add(country.getCountryName());
        }

        return countryNames;
    }

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
