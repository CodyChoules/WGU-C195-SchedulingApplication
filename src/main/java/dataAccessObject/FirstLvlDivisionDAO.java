package dataAccessObject;


import applicationObject.Country;
import applicationObject.FirstLvlDivision;
import applicationTools.JDBTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLvlDivisionDAO extends applicationObject.FirstLvlDivision {

    public FirstLvlDivisionDAO(int divisionId, String divisionName, int countryIdOfDivision, String countryNameOfDivision) {
        super(divisionId, divisionName, countryIdOfDivision, countryNameOfDivision);
    }

    public static ObservableList<FirstLvlDivisionDAO> getAllFirstLvlDivisions() throws SQLException {
        ObservableList<FirstLvlDivisionDAO> firstLvlDivisionsObservableList = FXCollections.observableArrayList();
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
            FirstLvlDivisionDAO firstLvlDivision = new FirstLvlDivisionDAO(divisionId, divisionName, countryIdOfDivision,countryNameOfDivision);
            firstLvlDivisionsObservableList.add(firstLvlDivision);
        }
        return firstLvlDivisionsObservableList;
    }

    public static ObservableList<String> getFLD_Names() throws SQLException {
        //Get all First Level Division Names


        ObservableList<String> observableList = FXCollections.observableArrayList();

        for (FirstLvlDivision firstLvlDivision: getAllFirstLvlDivisions()) {
            observableList.add(firstLvlDivision.getDivisionName());
        }

        return observableList;
    }

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
