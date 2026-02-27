package main.java.com.hit.dao;

import main.java.com.hit.dm.Delivery;
import java.io.IOException;
import java.util.List;

public class DeliveryDao extends AbstractDao<Delivery>
{
    public DeliveryDao(String filePath, String fileName) { super(filePath, fileName); }

    @Override
    public Delivery find(long id) throws IOException {
        for (Delivery delivery : getAll()) {
            if(delivery.getDeliveryOrder().getOrderId() == id) return delivery;
        }
        return null;
    }

    @Override
    protected Delivery findToRemove(List<Delivery> list, Delivery item) {
        for (Delivery d : list) {
            if (d.getDeliveryOrder().getOrderId() == item.getDeliveryOrder().getOrderId()) return d;
        }
        return null;
    }
}