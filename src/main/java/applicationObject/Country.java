package applicationObject;

public class Country {
    private int countryId;
    private String countryName;


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
}
