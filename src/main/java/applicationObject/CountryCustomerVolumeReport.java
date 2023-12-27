package applicationObject;

public class CountryCustomerVolumeReport {

    private int countryVolume;
    private String countryName;
    public String apMonth;
    public int apTotal;


    public CountryCustomerVolumeReport(String countryName, int countryCustomerVolume) {
        this.countryVolume = countryCustomerVolume;
        this.countryName = countryName;
    }

    public String getCountryName() { return countryName; }
    public void setCountryName(String countryName) { this.countryName = countryName; }

    public int getCountryVolume() { return countryVolume; }
    public void setCountryVolume(int countryVolume) { this.countryVolume = countryVolume; }

}

