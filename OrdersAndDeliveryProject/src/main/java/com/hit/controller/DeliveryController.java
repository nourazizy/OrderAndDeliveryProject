package main.java.com.hit.controller;

import main.java.com.hit.dm.Delivery;
import main.java.com.hit.service.OrderAndDeliveryService;
import main.java.com.hit.ShortestPath.DijkstraAlgoShortestPathImpl;
import java.util.List;

public class DeliveryController {

    private OrderAndDeliveryService service;

    public DeliveryController() {
        // אתחול ה-Service עם האלגוריתם והנתיב לקבצים
        String path = "src/main/resources";
        this.service = new OrderAndDeliveryService(new DijkstraAlgoShortestPathImpl<>(), path);
    }

    // --- SAVE (קיים אצלך) ---
    public boolean saveDelivery(Delivery delivery) {
        // כאן יכולה להיות לוגיקה נוספת (כמו חישוב מסלול) לפני השמירה
        service.saveDelivery(delivery);
        return true; // נחזיר אינדיקציה להצלחה
    }

    // --- DELETE (חובה להוסיף לפי עמוד 16) ---
    public void deleteDelivery(Delivery delivery) {
        service.deleteDelivery(delivery);
    }

    // --- GET (חובה להוסיף לפי עמוד 16) ---
    // אפשר לשלוף לפי מזהה, או את כל הרשימה - תלוי בצורך
    public List<Delivery> getAllDeliveries() {
        return service.getAllDeliveries();
    }

    // דוגמה לשליפה ספציפית (למשל לפי ID של ההזמנה)
    public Delivery getDeliveryByOrderId(long orderId) {
        List<Delivery> all = service.getAllDeliveries();
        for (Delivery d : all) {
            if (d.getDeliveryOrder().getOrderId() == orderId) {
                return d;
            }
        }
        return null;
    }

    public Delivery getDelivery(long id) { return service.getDelivery(id); }
}
