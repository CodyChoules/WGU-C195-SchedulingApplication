package applicationObject;

public class FirstLvlDivision {
    //This represents State or provence
    private int divisionId;
    private String divisionName;
    public int countryIdOfDivision;


    public FirstLvlDivision(int divisionId, String divisionName, int countryIdOfDivision) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryIdOfDivision = countryIdOfDivision;
    }


    public int getDivisionId() { return divisionId; }
    public void setDivisionId(int divisionId) { this.divisionId = divisionId; }

    public String getDivisionName() { return divisionName; }
    public void setDivisionName(String divisionName) { this.divisionName = divisionName; }

    public int getCountryIdOfDivision() { return countryIdOfDivision; }
    public void setCountryIdOfDivision(int countryIdOfDivision) { this.countryIdOfDivision = countryIdOfDivision; }

}

