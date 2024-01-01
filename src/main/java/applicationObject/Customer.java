package applicationObject;

import java.time.LocalDateTime;

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


    public LocalDateTime getCreateDate() { return createDate; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }

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

    public String getCustomerCountryName() { return customerCountryName; }
    public void setCustomerCountryName(String customerDivisionName) { this.customerCountryName = customerDivisionName; }

}
