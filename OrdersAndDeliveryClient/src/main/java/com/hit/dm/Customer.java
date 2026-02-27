package com.hit.dm;

import java.io.Serializable;

/*holds tha main information about the Customer*/
public class Customer implements Serializable
{
    private long customerId; /*the Customers ID Number*/
    private String customerName; /*the customers name to know what name to write on the order*/
    private String customerPhoneNumber; /*the phone number to call him when the delivery is getting closer*/
    private String customerAddress; /*the address that the order will be delivered to*/
    private String customerBirthday;

    /*Customer's class Constructor*/
    public Customer(long customerId, String customerName, String customerPhoneNumber, String customerAddress, String customerBirthday)
    {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerAddress = customerAddress;
        this.customerBirthday = customerBirthday;
    }

    /*all the functions that updates specific information about the customer*/
    public void setCustomerId(long customerId)
    {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber)
    {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public void setCustomerAddress(String customerAddress)
    {
        this.customerAddress = customerAddress;
    }

    public void setCustomerBirthday(String customerBirthday)
    {
        this.customerBirthday = customerBirthday;
    }

    /*all the functions that returns specific information about the customer*/
    public long getCustomerId()
    {
        return customerId;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public String getCustomerPhoneNumber()
    {
        return customerPhoneNumber;
    }

    public String getCustomerAddress()
    {
        return customerAddress;
    }

    public String getCustomerBirthday()
    {
        return customerBirthday;
    }

    /*helps us when we want to print the information of the customer*/
    @Override
    public String toString() {
        return "👤 Customer ID: " + customerId + "\n" +
                "   Name: " + customerName + "\n" +
                "   Phone: " + customerPhoneNumber + "\n" +
                "   Address: " + customerAddress + "\n" +
                "   Birthday: " + customerBirthday;
    }
}
