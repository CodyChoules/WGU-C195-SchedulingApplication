package applicationObject;

import javafx.collections.ObservableList;
//Done

/**
 * Represents a Country object from the DB.
 */
public class Country extends ApplicationObject{
    private int countryId;
    private String countryName;
    private ObservableList<String> childFDLNames;

    /**
     * Constructs a new Country object with the specified countryId and countryName.
     *
     * @param countryId   The unique identifier for the country.
     * @param countryName The name of the country.
     */
    public Country(int countryId, String countryName) {
        this.id = countryId;
        this.name = countryName;

        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**
     * Retrieves the countryId.
     *
     * @return The countryId.
     */
    public int getCountryId() { return countryId; }

    /**
     * Sets the countryId
     *
     *
     * @param countryId The unique identifier for the country.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
        this.id = countryId;
    }

    /**
     * Retrieves the countryName.
     *
     * @return The countryName.
     */
    public String getCountryName() { return countryName; }

    /**
     * Sets the countryName
     *
     * @param countryName The name of the country.
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
        this.name = countryName;
    }

    /**
     * Retrieves the list of child Foreign Data Link names associated with the country.
     *
     * @return The list of child FDL names.
     */
    public ObservableList<String> getChildFDLNames() {
        return childFDLNames;
    }
    /**
     * Sets the list of child FDL names associated with the country.
     *
     * @param childFDLNames The list of child FDL names.
     */
    public void setChildFDLNames(ObservableList<String> childFDLNames) {
        this.childFDLNames = childFDLNames;
    }



}
