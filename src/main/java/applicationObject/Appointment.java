package applicationObject;

import applicationTools.TimeTool;
import applicationTools.CChoulesDevTools;
import dataAccessObject.AppointmentDAO;
import dataAccessObject.ContactDAO;
import dataAccessObject.CustomerDAO;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Represents an appointment within the scheduling application.
 * This is retrieved from appointments table in DB
 * Note: appointment = ap for naming
 */
public class Appointment extends ApplicationObject{
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

    /**
     * Appointment Constructor
     *
     * @param apTitle       title of the appointment
     * @param apType        type/category of the appointment
     * @param apLocation    location of the appointment
     * @param apID          unique identifier for the appointment
     * @param apDescription description of the appointment
     * @param apStart       start date and time of the appointment
     * @param apEnd         end date and time of the appointment
     * @param apCustomerID  Id of the associated customer
     * @param apContactID   Id of the associated contact
     * @param apUserID      Id of the user associated with the appointment
     */
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

        this.id = apID;
        this.name = apTitle;


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
        this.name = apTitle;
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

    //Getter and Setter methods for apId
    public int getApId() {
        return apID;
    }
    public void setApID(int apID) {
        this.apID = apID;
        this.id = apID;
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
    public int getApCustomerId() { return apCustomerID; }
    public void setApCustomerID(int customerID) {
        this.apCustomerID = customerID;
    }

    //Getter and Setter methods for contactID
    public int getApContactId() {
        return apContactID;
    }
    public void setApContactID(int contactID) {
        this.apContactID = contactID;
    }

    //Getter and Setter methods for userId
    public int getApUserId() {
        return apUserID;
    }
    public void setApUserID(int userID) {
        this.apUserID = userID;
    }

    /**
     * Getter method for contact name accessed from ContactDAO.
     *
     * @return The name of the contact.
     * @throws SQLException If an SQL exception occurs during the database query.
     */
    public String getApContactName() throws SQLException {
        Contact contact = ContactDAO.findNameOfContactFromId(apContactID);
        //TODO [Extra] Be handle null  here.
        assert contact != null;
        String nameOfContact = contact.getContactName();
        if (nameOfContact == null) {
            return "Name Not Found";
        }
        CChoulesDevTools.println("Found name of Contact: " + nameOfContact);
        return nameOfContact;
    }

    /**
     * Getter method for Customer name accessed from CustomerDAO.
     *
     * @return The name of the Customer.
     * @throws SQLException If an SQL exception occurs during the database query.
     */
    public String getApCustomerName() throws SQLException {
        Customer customer = CustomerDAO.findCustomerFromId(apCustomerID);
        //TODO [Extra] Be handle null  here.
        assert customer != null;
        String nameOfContact = customer.getCustomerName();
        if (nameOfContact == null) {
            return "Name Not Found";
        }
        CChoulesDevTools.println("Found name of Contact: " + nameOfContact);
        return nameOfContact;
    }

//I added these remaining methods primarily so it reads well when calling.\\
    /**
     * Checks if the appointment is within business hours.
     *
     * @return True if the appointment is within business hours; false otherwise.
     */
    public boolean isInBusinessHours(){

        boolean end = TimeTool.isBusinessHours(this.apEnd);
        boolean start = TimeTool.isBusinessHours(this.apStart);

        return (end && start);
    }

    /**
     * Checks if the appointment is within one day.
     *
     * @return True if the appointment is within one day; false otherwise.
     */
    public boolean isWithinOneDay(){
        //This must use business hours
        //This is important because some places will pass into two different local dates in the same HQ day

        ZonedDateTime startZ = TimeTool.getTimeAsHqTime(this.apStart);
        ZonedDateTime endZ = TimeTool.getTimeAsHqTime(this.apEnd);

        boolean sameDay = (startZ.getDayOfYear() == endZ.getDayOfYear());
        boolean sameYear = (startZ.getYear() == endZ.getYear());

        return (sameDay && sameYear);
    }

    /**
     * Checks if the appointment does not overlap with other appointments for the same customer.
     *
     * @return True if the appointment does not overlap; false otherwise.
     * @throws SQLException If an SQL exception occurs during the database query.
     */
    public boolean isNotOverlapping() throws SQLException {

        ObservableList<Appointment> apListByCustomer = AppointmentDAO.getAllAppointmentsByCustomer(this.apCustomerID);
        //To prevent overlap detection with itself
        apListByCustomer.remove(this);

        //Missing Skill IDE Selected atomic here when isOverlapping and I do not know why (something about "atomicity of operations")
        AtomicBoolean isOverlapping = new AtomicBoolean(false);

        //Trying Lambda
        apListByCustomer.forEach(appointment -> {
            //If the neither the appointment is before or after then is is during
            if(!(this.getApEnd().isBefore(appointment.apStart) || appointment.apEnd.isBefore(this.getApStart()))){
                //Missing Skill lambdas cannot return for the methods containing them
                //return false;
                isOverlapping.set(true);
            }
        });

        return !isOverlapping.get();
    }

}
