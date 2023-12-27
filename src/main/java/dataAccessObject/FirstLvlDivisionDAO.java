package dataAccessObject;


import applicationTools.JDBTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLvlDivisionDAO extends applicationObject.FirstLvlDivision {

    public FirstLvlDivisionDAO(int divisionID, String divisionName, int country_ID) {
        super(divisionID, divisionName, country_ID);
    }

    public static ObservableList<FirstLvlDivisionDAO> getAllFirstLvlDivisions() throws SQLException {
        ObservableList<FirstLvlDivisionDAO> firstLvlDivisionsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from first_level_divisions";
        PreparedStatement ps = JDBTools.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int country_ID = rs.getInt("COUNTRY_ID");
            FirstLvlDivisionDAO firstLvlDivision = new FirstLvlDivisionDAO(divisionID, divisionName, country_ID);
            firstLvlDivisionsObservableList.add(firstLvlDivision);
        }
        return firstLvlDivisionsObservableList;
    }

}
