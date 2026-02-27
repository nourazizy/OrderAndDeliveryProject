package main.java.com.hit.server;

import main.java.com.hit.controller.*;
import java.util.HashMap;
import java.util.Map;

public class ControllerFactory {

    // Singleton instance - נטען מיד כשהאפליקציה (השרת) עולה, בדיוק לפי דרישות המרצה!
    private static final ControllerFactory instance = new ControllerFactory();

    // מפה שתחזיק את כל הבקרים שלנו
    private final Map<String, Object> controllersMap;

    // בנאי פרטי - מאתחל את כל הבקרים פעם אחת בלבד ושומר אותם בזיכרון
    private ControllerFactory() {
        controllersMap = new HashMap<>();
        controllersMap.put("customer", new CustomerController());
        controllersMap.put("product", new ProductController());
        controllersMap.put("order", new OrderController());
        controllersMap.put("delivery", new DeliveryController());
    }

    // פונקציה לקבלת המופע היחיד של ה-Factory
    public static ControllerFactory getInstance() {
        return instance;
    }

    // מתודת ה-Factory שמחזירה את הבקר הרלוונטי לפי השם שקיבלנו מהלקוח
    public Object getController(String domain) {
        return controllersMap.get(domain.toLowerCase());
    }
}
