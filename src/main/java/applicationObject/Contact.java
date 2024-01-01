package applicationObject;

public class Contact extends ApplicationObject{
    public int contactId;
    public String contactName;
    public String contactEmail;

    public Contact (int contactId, String contactName, String contactEmail) {

        this.id = contactId;
        this.name = contactName;

        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    //Getter and Setter methods for contactId
    public int getContactId() { return contactId; }
    public void setContactId(int contactId) {
        this.contactId = contactId;
        this.id = contactId;
    }

    //Getter and Setter methods for contactName
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) {
        this.contactName = contactName;
        this.name = contactName;
    }

    //Getter and Setter methods for contactId
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
}
