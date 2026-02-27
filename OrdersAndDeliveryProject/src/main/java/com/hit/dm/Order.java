package main.java.com.hit.dm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {
    private final long orderId;
    private double ordersTotalCost;
    private final Date orderDate;
    private final List<Product> productsList;
    private boolean delivered;
    private final Customer customerDetails;

    public Order(long newOrderId, Customer newCustomerDetails) {
        orderId = newOrderId;
        productsList = new ArrayList<>();
        customerDetails = newCustomerDetails;
        orderDate = new Date();
        ordersTotalCost = 0;
        delivered = false;
    }

    public void setOrdersTotalCost(double ordersTotalCost) { this.ordersTotalCost = ordersTotalCost; }
    public void setDelivered(boolean delivered) { this.delivered = delivered; }
    public void addProductToList(Product product) { productsList.add(product); }
    public void removeProductFromList(Product product) { productsList.remove(product); }

    public List<Product> getOrdersList() { return productsList; }
    public String getDeliveryAddress() { return customerDetails.getCustomerAddress(); }
    public String getCustomersPhoneNumber() { return customerDetails.getCustomerPhoneNumber(); }
    public long getCustomerId() { return customerDetails.getCustomerId(); }
    public String getCustomerName() { return customerDetails.getCustomerName(); }
    public Date getOrderDate() { return orderDate; }
    public double getOrdersTotalCost() { return ordersTotalCost; }
    public long getOrderId() { return orderId; }
    public boolean getIsDelivered() { return delivered; }

    // חובה למחיקה!
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

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