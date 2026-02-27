package main.java.com.hit.dm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Delivery implements Serializable {
    private String storesAddress;
    private Order deliveryOrder;
    private Integer ExpectedTime;
    private List<String> path;
    private Date deliveryDate;

    public Delivery(String storesAddress, Order deliveryOrder) {
        this.storesAddress = storesAddress;
        this.deliveryOrder = deliveryOrder;
        this.ExpectedTime = 0;
        this.path = null;
        this.deliveryDate = new Date();
    }

    public void setStoresAddress(String storesAddress) { this.storesAddress = storesAddress; }
    public void setDeliveryOrder(Order deliveryOrder) { this.deliveryOrder = deliveryOrder; }
    public void setExpectedTime(Integer ExpectedTime) { this.ExpectedTime = ExpectedTime; }
    public void setPath(List<String> path) { this.path = path; }
    public void setDeliveryDate(Date deliveryDate) { this.deliveryDate = deliveryDate; }

    public String getStoresAddress() { return storesAddress; }
    public Order getDeliveryOrder() { return deliveryOrder; }
    public Integer getExpectedTime() { return ExpectedTime; }
    public List<String> getPath() { return path; }
    public Date getDeliveryDate() { return deliveryDate; }

    // חובה למחיקה! מזהה משלוח לפי מספר ההזמנה שלו
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return deliveryOrder != null && delivery.deliveryOrder != null &&
                deliveryOrder.getOrderId() == delivery.deliveryOrder.getOrderId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryOrder != null ? deliveryOrder.getOrderId() : 0);
    }

    @Override
    public String toString() {
        String pathDisplay = (path != null && !path.isEmpty()) ? String.join(" ➔ ", path) : "Path not calculated yet";
        return "🚚 Delivery for Order ID: " + deliveryOrder.getOrderId() + "\n" +
                "   From Store: " + storesAddress + "\n" +
                "   To Customer: " + deliveryOrder.getDeliveryAddress() + "\n" +
                "   Expected Time: " + ExpectedTime + " mins\n" +
                "   Path: " + pathDisplay;
    }
}