package applicationObject;

import javafx.collections.ObservableList;

public class Country extends ApplicationObject{
    private int countryId;
    private String countryName;
    private ObservableList<String> childFDLNames;


    public Country(int countryId, String countryName) {
        this.id = countryId;
        this.name = countryName;

        this.countryId = countryId;
        this.countryName = countryName;
    }

    //Getter and Setter methods for countryId
    public int getCountryId() { return countryId; }
    public void setCountryId(int countryId) {
        this.countryId = countryId;
        this.id = countryId;
    }

    //Getter and Setter methods for countryName
    public String getCountryName() { return countryName; }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
        this.name = countryName;
    }

    // Getter & setter methods for getting Child FDL to display in things like combo boxes
    public ObservableList<String> getChildFDLNames() {
        return childFDLNames;
    }
    public void setChildFDLNames(ObservableList<String> childFDLNames) {
        this.childFDLNames = childFDLNames;
    }



}
