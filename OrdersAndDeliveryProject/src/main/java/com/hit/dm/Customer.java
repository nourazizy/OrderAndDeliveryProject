package main.java.com.hit.dm;

import java.io.Serializable;
import java.util.Objects;

public class Customer implements Serializable {
    private long customerId;
    private String customerName;
    private String customerPhoneNumber;
    private String customerAddress;
    private String customerBirthday;

    public Customer(long customerId, String customerName, String customerPhoneNumber, String customerAddress, String customerBirthday) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerAddress = customerAddress;
        this.customerBirthday = customerBirthday;
    }

    public void setCustomerId(long customerId) { this.customerId = customerId; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setCustomerPhoneNumber(String customerPhoneNumber) { this.customerPhoneNumber = customerPhoneNumber; }
    public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }
    public void setCustomerBirthday(String customerBirthday) { this.customerBirthday = customerBirthday; }

    public long getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public String getCustomerPhoneNumber() { return customerPhoneNumber; }
    public String getCustomerAddress() { return customerAddress; }
    public String getCustomerBirthday() { return customerBirthday; }

    // פונקציה קריטית כדי שהמחיקה תעבוד!
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerId == customer.customerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }

    @Override
    public String toString() {
        return "👤 Customer ID: " + customerId + "\n" +
                "   Name: " + customerName + "\n" +
                "   Phone: " + customerPhoneNumber + "\n" +
                "   Address: " + customerAddress + "\n" +
                "   Birthday: " + customerBirthday;
    }
}