package applicationObject;

import javafx.collections.ObservableList;

public class Country extends ApplicationObject{
    private int countryId;
    private String countryName;
    private ObservableList<String> childFDLNames;


    public Country(int countryID, String countryName) {
        this.countryId = countryID;
        this.countryName = countryName;
    }

    //Getter and Setter methods for countryId
    public int getCountryId() { return countryId; }
    public void setCountryId(int countryID) {  this.countryId = countryID; }

    //Getter and Setter methods for countryName
    public String getCountryName() { return countryName; }
    public void setCountryName(int countryID) {  this.countryId = countryID; }

    // Getter & setter methods for getting Child FDL to display in things like combo boxes
    public ObservableList<String> getChildFDLNames() {
        return childFDLNames;
    }
    public void setChildFDLNames(ObservableList<String> childFDLNames) {
        this.childFDLNames = childFDLNames;
    }



}
