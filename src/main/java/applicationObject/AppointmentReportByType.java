package applicationObject;

public class AppointmentReportByType {
    public String apType;
    public int apTotal;

    public AppointmentReportByType(
            String apType,
            int apTotal) {
        this.apType = apType;
        this.apTotal = apTotal;
    }

    public String getApType() { return apType; }

    public int getApTotal() { return apTotal; }

}
