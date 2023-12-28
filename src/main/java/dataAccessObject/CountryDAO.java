package dataAccessObject;

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

    public static ObservableList<CountryDAO> getCountries() throws SQLException {
        ObservableList<CountryDAO> countryObservableList = FXCollections.observableArrayList();
        String preparedStatement = "SELECT Country_ID, Country from countries";
        ResultSet resultSet =  JDBTools.getConnection().prepareStatement(preparedStatement).executeQuery();

        while (resultSet.next()) {
            String countryName = resultSet.getString("Country");
            int countryId = resultSet.getInt("Country_ID");
            CountryDAO country = new CountryDAO(countryId, countryName);
            countryObservableList.add(country);
        }

        return countryObservableList;
    }

}
