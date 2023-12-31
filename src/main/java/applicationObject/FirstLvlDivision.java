package applicationObject;

public class FirstLvlDivision {
    //This represents State or provence
    private int divisionId;
    private String divisionName;
    public int countryIdOfDivision;
    private String countryNameOfDivision;


    public FirstLvlDivision(int divisionId, String divisionName, int countryIdOfDivision, String countryNameOfDivision) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryIdOfDivision = countryIdOfDivision;
        this.countryNameOfDivision = countryNameOfDivision;
    }


    public int getDivisionId() { return divisionId; }
    public void setDivisionId(int divisionId) { this.divisionId = divisionId; }

    public String getDivisionName() { return divisionName; }
    public void setDivisionName(String divisionName) { this.divisionName = divisionName; }

    public int getCountryIdOfDivision() { return countryIdOfDivision; }
    public void setCountryIdOfDivision(int countryIdOfDivision) { this.countryIdOfDivision = countryIdOfDivision; }

    public String getCountryNameOfDivision() { return countryNameOfDivision; }
    public void setCountryNameOfDivision(String countryNameOfDivision) { this.countryNameOfDivision = countryNameOfDivision; }
}

