package applicationObject;

public class Customer {

    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private String customerDivisionName;

    private int divisionId;

    public Customer(int customerId,
                    String customerName,
                    String customerAddress,
                    String customerPostalCode,
                    String customerPhoneNumber,
                    int divisionId,
                    String customerDivisionName) {

        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.divisionId = divisionId;
        this.customerDivisionName = customerDivisionName;
    }


    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerAddress() { return customerAddress; }
    public void setCustomerAddress(String address) { this.customerAddress = address; }

    public String getCustomerPostalCode() { return customerPostalCode; }
    public void setCustomerPostalCode(String postalCode) { this.customerPostalCode = postalCode; }

    public String getCustomerPhoneNumber() { return customerPhoneNumber; }
    public void setCustomerPhoneNumber(String phone) { this.customerPhoneNumber = phone; }

    public Integer getCustomerDivisionId() { return divisionId; }
    public void setCustomerDivisionId(Integer divisionId) { this.divisionId = divisionId; }

    public String getCustomerDivisionName() { return customerDivisionName; }
    public void setCustomerDivisionName(String customerDivisionName) { this.customerDivisionName = customerDivisionName; }

}
