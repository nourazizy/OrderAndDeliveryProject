package com.hit.dm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*contains basic information about the order*/
public class Order implements Serializable
{
    private final long orderId; /*the order's serial number */
    private double ordersTotalCost; /*how much the order costs*/
    private final Date orderDate; /*in which date this order was made*/
    private final List<Product> productsList; /*the products list that the customer ordered*/
    private boolean delivered; /*is the customer got his oreder*/
    private final Customer customerDetails;

    public Order(long newOrderId, Customer newCustomerDetails)
    {
        orderId = newOrderId;
        productsList = new ArrayList<>();
        customerDetails = newCustomerDetails;
        orderDate = new Date();
        ordersTotalCost = 0;
        delivered = false;
    }

    /*Setters*/
    public void setOrdersTotalCost(double ordersTotalCost)
    {
        this.ordersTotalCost = ordersTotalCost;
    }

    public void setDelivered(boolean delivered)
    {
        this.delivered = delivered;
    }

    /*adds a new product to the list*/
    public void addProductToList(Product product)
    {
        productsList.add(product);
    }

    /*removes a product from the list*/
    public void removeProductFromList(Product product)
    {
        productsList.remove(product);
    }

    /*Getters*/
    public List<Product> getOrdersList()
    {
        return productsList;
    }

    public String getDeliveryAddress()
    {
        return customerDetails.getCustomerAddress();
    }

    public String getCustomersPhoneNumber()
    {
        return customerDetails.getCustomerPhoneNumber();
    }

    public long getCustomerId()
    {
        return customerDetails.getCustomerId();
    }

    public String getCustomerName()
    {
        return customerDetails.getCustomerName();
    }

    public Date getOrderDate()
    {
        return orderDate;
    }

    public double getOrdersTotalCost()
    {
        return ordersTotalCost;
    }

    public  long getOrderId()
    {
        return orderId;
    }

    public boolean getIsDelivered()
    {
        return delivered;
    }

    /*helps us when we want to print the information of a specific order*/
    @Override
    public String toString() {
        String deliveryStatus = delivered ? "Yes" : "No";
        return "🛒 Order ID: " + orderId + "\n" +
                "   Customer: " + customerDetails.getCustomerName() + "\n" +
                "   Date: " + orderDate + "\n" +
                "   Total Cost: ₪" + ordersTotalCost + " | Delivered: " + deliveryStatus + "\n" +
                "   Products in Order: " + productsList.size() + " items";
    }
}
