package applicationObject;


/**
 * Represents a First Level Division object such as a provence or a state from the DB.
 */
public class FirstLvlDivision extends ApplicationObject{
    //This represents State or provence

    private int divisionId;
    private String divisionName;
    public int countryIdOfDivision;
    private String countryNameOfDivision;

    /**
     * Constructs a FirstLvlDivision object with the specified attributes.
     *
     * @param divisionId             The ID of the division.
     * @param divisionName           The name of the division.
     * @param countryIdOfDivision    The country ID associated with the division.
     * @param countryNameOfDivision  The country name associated with the division.
     */
    public FirstLvlDivision(int divisionId, String divisionName, int countryIdOfDivision, String countryNameOfDivision) {

        this.id = divisionId;
        this.name = divisionName;

        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryIdOfDivision = countryIdOfDivision;
        this.countryNameOfDivision = countryNameOfDivision;
    }

    /**
     * Gets the ID of the first level division
     * @return The first level division ID.
     */
    public int getDivisionId() { return divisionId; }
    /**
     * Sets the ID of the division.
     * @param divisionId The division ID to set.
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
        this.id = divisionId;
    }
    /**
     * Gets the name of the division.
     * @return The division name.
     */
    public String getDivisionName() { return divisionName; }    /**
     /**
     * Sets the name of the division.
     * @param divisionName The division name to set.
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
        this.name = divisionName;
    }

    /**
     * Gets the country ID associated with the division.
     * @return The country ID of the division.
     */
    public int getCountryIdOfDivision() { return countryIdOfDivision; }
    /**
     * Sets the country ID associated withthe division.
     * @param countryIdOfDivision The country ID to set.
     */
    public void setCountryIdOfDivision(int countryIdOfDivision) { this.countryIdOfDivision = countryIdOfDivision; }
    /**
     * Gets the country name associated with the division.
     * @return The country name of the division.
     */
    public String getCountryNameOfDivision() { return countryNameOfDivision; }
    /**
     * Sets the country name associated with the division.
     * @param countryNameOfDivision The country name to set
     */
    public void setCountryNameOfDivision(String countryNameOfDivision) { this.countryNameOfDivision = countryNameOfDivision; }

    /**
     * Overrides the getId method in ApplicationObject class.
     * @return The ID of the division.
     */
    @Override
    public int getId() {
        return super.getId();
    }


    /**
     * Overrides the setId method in ApplicationObject class.
     * @param divisionId The ID to set for the division.
     */
    @Override
    public void setId(int divisionId) {
        super.setId(divisionId);
    }
}

