package main.java.com.hit.dao;

import main.java.com.hit.dm.Delivery;

import java.io.IOException;
import java.util.List;

public class DeliveryDao extends AbstractDao<Delivery>
{
    public DeliveryDao(String filePath, String fileName)
    {
        super(filePath, fileName);
    }

    @Override
    public Delivery find(long id) throws IOException
    {
        List<Delivery> deliveries = getAll();
        for (Delivery delivery : deliveries)
        {
            if(delivery.getDeliveryOrder().getOrderId() == id)
            {
                return delivery;
            }
        }
        return null;
    }
}
