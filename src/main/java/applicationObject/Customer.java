package applicationObject;

import applicationTools.CChoulesDevTools;

import java.time.LocalDateTime;

/**
 * Represents a Customer object from the DB.
 */
public class Customer extends ApplicationObject{

    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private String customerDivisionName;
    private String customerCountryName;
    private LocalDateTime createDate;

    private int divisionId;

    /**
     * Constructs a Customer object with the specified parameters.
     *
     * @param customerId          The Id for the customer.
     * @param customerName        The name of the customer.
     * @param customerAddress     The address of the customer.
     * @param customerPostalCode  The postal code of the customer.
     * @param customerPhoneNumber The phone number of the customer.
     * @param divisionId          The Id of the division to which the customer belongs.
     * @param customerDivisionName The name of the division to which the customer belongs.
     * @param customerCountryName The name of the country to which the customer belongs.
     * @param createDate          The date and time when the customer was created.
     */
    public Customer(int customerId,
                    String customerName,
                    String customerAddress,
                    String customerPostalCode,
                    String customerPhoneNumber,
                    int divisionId,
                    String customerDivisionName,
                    String customerCountryName,
                    LocalDateTime createDate) {

        this.id = customerId;
        this.name = customerName;

        this.createDate = createDate;

        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.divisionId = divisionId;
        this.customerDivisionName = customerDivisionName;
        this.customerCountryName = customerCountryName;
    }

    //GETTERS & SETTERS\\
    /**
     * Gets the creation date and time of the customer.
     * @return The creation date and time.
     */
    public LocalDateTime getCreateDate() { return createDate; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }

    /**
     * Gets the customerId of the customer.
     * @return customerId
     */
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    /**
     * Gets the customerName of the customer.
     * @return customerName
     */
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    /**
     * Gets the customerAddress of the customer.
     * @return customerAddress
     */
    public String getCustomerAddress() { return customerAddress; }
    public void setCustomerAddress(String address) { this.customerAddress = address; }

    /**
     * Gets the customerPostalCode of the customer.
     * @return customerPostalCode
     */
    public String getCustomerPostalCode() { return customerPostalCode; }
    public void setCustomerPostalCode(String postalCode) { this.customerPostalCode = postalCode; }

    /**
     * Gets the customerPhoneNumber of the customer.
     * @return customerPhoneNumber
     */
    public String getCustomerPhoneNumber() { return customerPhoneNumber; }
    public void setCustomerPhoneNumber(String phone) { this.customerPhoneNumber = phone; }

    /**
     * Gets the divisionId of the customer.
     * @return divisionId
     */
    public Integer getCustomerDivisionId() { return divisionId; }
    public void setCustomerDivisionId(Integer divisionId) { this.divisionId = divisionId; }

    /**
     * Gets the customerDivisionName of the customer.
     * @return customerDivisionName
     */
    public String getCustomerDivisionName() { return customerDivisionName; }
    public void setCustomerDivisionName(String customerDivisionName) { this.customerDivisionName = customerDivisionName; }

    /**
     * Gets the customerCountryName of the customer.
     * @return customerCountryName
     */
    public String getCustomerCountryName() { return customerCountryName; }
    public void setCustomerCountryName(String customerDivisionName) { this.customerCountryName = customerDivisionName; }


    public boolean isComplete() {
        CChoulesDevTools.println(
                this.customerId + "\n " +
                        this.customerName + "\n " +
                        this.customerAddress + "\n " +
                        this.customerPostalCode + "\n " +
                        this.customerPhoneNumber + "\n " +
                        this.divisionId + "\n " +
                        this.customerDivisionName + "\n " +
                        this.customerCountryName
        );

        return
                !this.customerName.isBlank() &&
                !this.customerAddress.isBlank() &&
                !this.customerPostalCode.isBlank() &&
                !this.customerPhoneNumber.isBlank() &&
                this.divisionId != 0 &&
                !this.customerDivisionName.isBlank() &&
                !this.customerCountryName.isBlank();
    }

}
