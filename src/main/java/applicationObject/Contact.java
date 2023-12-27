package applicationObject;

public class Contact {
    public int contactId;
    public String contactName;
    public String contactEmail;

    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactId = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    //Getter and Setter methods for contactId
    public int getContactId() { return contactId; }
    public void setContactId(int contactId) { this.contactId = contactId; }

    //Getter and Setter methods for contactName
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    //Getter and Setter methods for contactId
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
}
