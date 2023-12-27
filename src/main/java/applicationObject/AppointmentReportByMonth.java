package applicationObject;

public class AppointmentReportByMonth {
    public String apMonth;
    public int apTotal;

    public AppointmentReportByMonth(String apMonth, int apTotal) {
        this.apMonth = apMonth;
        this.apTotal = apTotal;
    }

    public String getApMonth() { return apMonth; }

    public int getApTotal() { return apTotal; }
}
