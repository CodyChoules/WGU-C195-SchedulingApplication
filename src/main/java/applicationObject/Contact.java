package applicationObject;


/**
 * This is the class for the Contacts from the DataBase
 * I actually do know for sure what they are in terms of Customer relationship.
 */
public class Contact extends ApplicationObject{
    private int contactId;
    private String contactName;
    private String contactEmail;

    /**
     * Constructs a new Contact object with the specified parameters.
     *
     * @param contactId    The unique identifier for the contact.
     * @param contactName  The name of the contact.
     * @param contactEmail The email address of the contact.
     */
    public Contact (int contactId, String contactName, String contactEmail) {

        this.id = contactId;
        this.name = contactName;

        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Gets the contact's Id
     *
     * @return Id
     */
    public int getContactId() { return contactId; }
    /**
     * Sets the id
     *
     * @param contactId The new id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
        this.id = contactId;
    }

    /**
     * Gets the name of the contact.
     *
     * @return The name of the contact.
     */
    public String getContactName() { return contactName; }
    /**
     * Sets the name of the contact.
     *
     * @param contactName The new name for the contact.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
        this.name = contactName;
    }

    //Getter and Setter methods for contactId\\
    // These ended up not being used in the application
    // Though they are mentioned in the DB
    /**
     * Gets the email of the contact.
     * Note: This method is not used in the application.
     *
     * @return The email address of the contact.
     */
    public String getContactEmail() { return contactEmail; }
    /**
     * Sets the email of the contact.
     * Note: This method is not used in the application.
     *
     * @param contactEmail The new email address for the contact.
     */
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
}
