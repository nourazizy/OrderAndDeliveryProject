package main.java.com.hit.dao;

import main.java.com.hit.dm.Order;
import java.io.IOException;
import java.util.List;

public class OrderDao extends AbstractDao<Order>
{
    public OrderDao(String filePath, String fileName) { super(filePath, fileName); }

    @Override
    public Order find(long id) throws IOException {
        for (Order order : getAll()) {
            if (order.getOrderId() == id) return order;
        }
        return null;
    }

    @Override
    protected Order findToRemove(List<Order> list, Order item) {
        for (Order o : list) {
            if (o.getOrderId() == item.getOrderId()) return o;
        }
        return null;
    }
}