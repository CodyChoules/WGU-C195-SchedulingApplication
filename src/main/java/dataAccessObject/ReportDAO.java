package dataAccessObject;

import applicationObject.trash.CountryCustomerVolumeReport;
import applicationTools.JDBTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDateTime;

public class ReportDAO extends applicationObject.Appointment {
    //I am extending Application to us its get functions.
    public ReportDAO(
            String apTitle,
            String apType,
            String apLocation,
            int apID,
            String apDescription,
            LocalDateTime apStart,
            LocalDateTime apEnd,
            int apCustomerID,
            int apContactID,
            int apUserID) {
        super(
            apTitle,
            apType,
            apLocation,
            apID,
            apDescription,
            apStart,
            apEnd,
            apCustomerID,
            apContactID,
            apUserID);
    }

    public static ObservableList<CountryCustomerVolumeReport> getCountries() throws SQLException {
        //Creating a list to hold Country CustomerController Volumes for each country.
        ObservableList<CountryCustomerVolumeReport> customerVolumeReportObservableList = FXCollections.observableArrayList();
        //TODO [l] double check this sql
        String preparedStatement =
                "SELECT c.Country, COUNT(*) AS CountryVol " +
                "FROM customers cu " +
                "INNER JOIN first_level_divisions fld ON cu.Division_ID = fld.Division_ID " +
                "INNER JOIN countries c ON c.Country_ID = fld.Country_ID " +
                "WHERE cu.Division_ID = fld.Division_ID " +
                "GROUP BY fld.Country_ID " +
                "ORDER BY countryVol DESC;";
        PreparedStatement ps = JDBTools.getConnection().prepareStatement(preparedStatement);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            String countryName = resultSet.getString("Country");
            int countryVolume = resultSet.getInt("CountryVol");
            CountryCustomerVolumeReport report = new CountryCustomerVolumeReport(countryName, countryVolume);
            customerVolumeReportObservableList.add(report);
        }

        return customerVolumeReportObservableList;
    }

}

