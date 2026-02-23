package main.java.com.hit.dao;

import main.java.com.hit.dm.Order;

import java.io.IOException;
import java.util.List;

public class OrderDao extends AbstractDao<Order>
{
    public OrderDao(String filePath, String fileName)
    {
        super(filePath, fileName);
    }

    @Override
    public Order find(long id) throws IOException
    {
        List<Order> orders = getAll();
        for (Order order : orders)
        {
            if (order.getOrderId() == id) {
                return order;
            }
        }
        return null;
    }
}
