package applicationObject;

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
    public int customerID;
    public int contactID;
    public int userID;

    public Appointment(
                       String apTitle,
                       String apType,
                       String apLocation,
                       int apID,
                       String apDescription,
                       LocalDateTime apStart,
                       LocalDateTime apEnd,
                       int customerID,
                       int contactID,
                       int userID
    ) {
        this.apTitle = apTitle;
        this.apType = apType;
        this.apLocation = apLocation;
        this.apID = apID;
        this.apDescription = apDescription;
        this.apStart = apStart;
        this.apEnd = apEnd;
        this.customerID = customerID;
        this.contactID = contactID;
        this.userID = userID;
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
    public int getCustomerID() {
        return customerID;
    }
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    //Getter and Setter methods for contactID
    public int getContactID() {
        return contactID;
    }
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    //Getter and Setter methods for userID
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }



}
