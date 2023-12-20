package applicationObject;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Appointment {
    //Note: appointment = ap for naming
    private String apTitle;
    private String apType;
    private String apLocation;
    private int apID;
    private String apDescription;
    private LocalDateTime apStart;
    private LocalDateTime apEnd;
    public int apCustomerID;
    public int apContactID;
    public int apUserID;

    public Appointment(
                       String apTitle,
                       String apType,
                       String apLocation,
                       int apID,
                       String apDescription,
                       LocalDateTime apStart,
                       LocalDateTime apEnd,
                       int apCustomerID,
                       int apContactID,
                       int apUserID
    ) {
        this.apTitle = apTitle;
        this.apType = apType;
        this.apLocation = apLocation;
        this.apID = apID;
        this.apDescription = apDescription;
        this.apStart = apStart;
        this.apEnd = apEnd;
        this.apCustomerID = apCustomerID;
        this.apContactID = apContactID;
        this.apUserID = apUserID;
    }

    //Getter and Setter methods for apTitle
    public String getApTitle() {
        return apTitle;
    }
    public void setApTitle(String apTitle) {
        this.apTitle = apTitle;
    }

    //Getter and Setter methods for apType
    public String getApType() {
        return apType;
    }
    public void setApType(String apType) {
        this.apType = apType;
    }

    //Getter and Setter methods for apLocation
    public String getApLocation() {
        return apLocation;
    }
    public void setApLocation(String apLocation) {
        this.apLocation = apLocation;
    }

    //Getter and Setter methods for apID
    public int getApID() {
        return apID;
    }
    public void setApID(int apID) {
        this.apID = apID;
    }

    //Getter and Setter methods for apDescription
    public String getApDescription() {
        return apDescription;
    }
    public void setApDescription(String apDescription) {
        this.apDescription = apDescription;
    }

    //Getter and Setter methods for apStart
    public LocalDateTime getApStart() {
        return apStart;
    }
    public void setApStart(LocalDateTime apStart) {
        this.apStart = apStart;
    }

    //Getter and Setter methods for apEnd
    public LocalDateTime getApEnd() {
        return apEnd;
    }
    public void setApEnd(LocalDateTime apEnd) {
        this.apEnd = apEnd;
    }

    //Getter and Setter methods for customerID
    public int getApCustomerID() { return apCustomerID; }
    public void setApCustomerID(int customerID) {
        this.apCustomerID = customerID;
    }

    //Getter and Setter methods for contactID
    public int getApContactID() {
        return apContactID;
    }
    public void setApContactID(int contactID) {
        this.apContactID = contactID;
    }

    //Getter and Setter methods for userID
    public int getApUserID() {
        return apUserID;
    }
    public void setApUserID(int userID) {
        this.apUserID = userID;
    }



}
