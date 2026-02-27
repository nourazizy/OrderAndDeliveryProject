package main.java.com.hit.controller;

import main.java.com.hit.dm.Order;
import main.java.com.hit.service.OrderAndDeliveryService;
import main.java.com.hit.ShortestPath.DijkstraAlgoShortestPathImpl;
import java.util.List;

public class OrderController {

    private OrderAndDeliveryService service;

    public OrderController() {
        // אתחול ה-Service
        String path = "src/main/resources";
        this.service = new OrderAndDeliveryService(new DijkstraAlgoShortestPathImpl<>(), path);
    }

    // --- שמירת הזמנה ---
    public boolean saveOrder(Order order) {
        service.saveOrder(order);
        return true;
    }

    // --- מחיקת הזמנה ---
    public void deleteOrder(Order order) {
        service.deleteOrder(order);
    }

    // --- שליפת הזמנות ---
    public List<Order> getAllOrders() {
        return service.getAllOrders();
    }

    public Order getOrder(long id) { return service.getOrder(id); }
}
