package main.java.com.hit.dm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*holds tha main information about the delivery address*/
public class Delivery implements Serializable
{
    private String storesAddress; /*the store that the order was bought from*/
    private Order deliveryOrder; /*the customers order with all needed details*/
    private Integer ExpectedTime; /*In how long the customer will get his order*/
    private List<String> path; /*the shortest path or way they should go through*/
    private Date deliveryDate; /*the date that the order will be delivered at*/

    /*constructor*/
    public Delivery(String storesAddress, Order deliveryOrder)
    {
        this.storesAddress = storesAddress;
        this.deliveryOrder = deliveryOrder;
        this.ExpectedTime = 0; /*will be calculated in another function*/
        this.path = null; /*we will find by one of the shortest path algorithm*/
        this.deliveryDate = new Date();
    }

    /*Setters - functions that will update information about the object Delivery*/
    public void setStoresAddress(String storesAddress)
    {
        this.storesAddress = storesAddress;
    }

    public void setDeliveryOrder(Order deliveryOrder)
    {
        this.deliveryOrder = deliveryOrder;
    }

    public void setExpectedTime(Integer ExpectedTime)
    {
        this.ExpectedTime = ExpectedTime;
    }

    public void setPath(List<String> path)
    {
        this.path = path;
    }

    public void setDeliveryDate(Date deliveryDate)
    {
        this.deliveryDate = deliveryDate;
    }

    /*Getters - returns what private parameters contains*/
    public String getStoresAddress()
    {
        return storesAddress;
    }

    public Order getDeliveryOrder()
    {
        return deliveryOrder;
    }

    public Integer getExpectedTime()
    {
        return ExpectedTime;
    }

    public List<String> getPath()
    {
        return path;
    }

    public Date getDeliveryDate()
    {
        return deliveryDate;
    }

    /*helps us when we want to print the information of a specific delivery*/
    @Override
    public String toString() {
        return "Delivery{" +
                "storesAddress='" + storesAddress + '\'' +
                ", deliveryAddress='" +deliveryOrder.getDeliveryAddress() + '\'' +
                ", OrderId=" + deliveryOrder.getOrderId() +
                ", ExpectedTime=" + ExpectedTime + "mins" +
                ", deliveryDate=" + deliveryDate +
                ", path=" + path +
                '}';
    }
}
