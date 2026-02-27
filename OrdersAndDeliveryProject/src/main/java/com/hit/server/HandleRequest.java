package main.java.com.hit.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.com.hit.controller.*;
import main.java.com.hit.dm.*;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HandleRequest implements Runnable {

    private Socket clientSocket;
    private Gson gson;

    public HandleRequest(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.gson = new Gson();
    }

    @Override
    public void run() {
        try {
            // הגדרת ערוצי קריאה וכתיבה (בדיוק לפי ההוראות במסמך הדרישות)
            Scanner reader = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

            if (reader.hasNextLine()) {
                String requestJson = reader.nextLine();
                System.out.println("HandleRequest: Received JSON: " + requestJson);

                // 1. חילוץ ה-action מה-Headers
                Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
                Map<String, Object> requestMap = gson.fromJson(requestJson, mapType);
                Map<String, String> headers = (Map<String, String>) requestMap.get("headers");

                String action = headers.get("action"); // לדוגמה: "customer/save"

                // 2. הכנת אובייקט התשובה הכללי
                Response<Object> response = new Response<>();
                Map<String, String> responseHeaders = new HashMap<>();
                responseHeaders.put("action", action);
                response.setHeaders(responseHeaders);

                // 3. ניתוב הבקשה (שימוש ב-Factory Pattern!)
                try {
                    String[] parts = action.split("/");
                    String domain = parts[0]; // customer / product / order / delivery
                    String command = parts[1]; // save / delete / get / find

                    // ===== כאן מתבצעת הקריאה ל-Factory! =====
                    Object rawController = ControllerFactory.getInstance().getController(domain);

                    if (rawController == null) {
                        response.setBody("Error: Unknown domain (" + domain + ").");
                    } else {
                        switch (domain) {
                            case "customer":
                                CustomerController customerController = (CustomerController) rawController;
                                if (command.equals("save")) {
                                    Type reqType = new TypeToken<Request<Customer>>(){}.getType();
                                    Request<Customer> req = gson.fromJson(requestJson, reqType);
                                    customerController.saveCustomer(req.getBody());
                                    response.setBody("Customer saved successfully.");
                                } else if (command.equals("delete")) {
                                    Type reqType = new TypeToken<Request<Customer>>(){}.getType();
                                    Request<Customer> req = gson.fromJson(requestJson, reqType);
                                    customerController.deleteCustomer(req.getBody());
                                    response.setBody("Customer deleted successfully.");
                                } else if (command.equals("get")) {
                                    response.setBody(customerController.getAllCustomers());
                                } else if (command.equals("find")) {
                                    Type reqType = new TypeToken<Request<Customer>>(){}.getType();
                                    Request<Customer> req = gson.fromJson(requestJson, reqType);
                                    Customer found = customerController.getCustomer(req.getBody().getCustomerId());
                                    response.setBody(found);
                                }
                                break;

                            case "product":
                                ProductController productController = (ProductController) rawController;
                                if (command.equals("save")) {
                                    Type reqType = new TypeToken<Request<Product>>(){}.getType();
                                    Request<Product> req = gson.fromJson(requestJson, reqType);
                                    productController.saveProduct(req.getBody());
                                    response.setBody("Product saved successfully.");
                                } else if (command.equals("delete")) {
                                    Type reqType = new TypeToken<Request<Product>>(){}.getType();
                                    Request<Product> req = gson.fromJson(requestJson, reqType);
                                    productController.deleteProduct(req.getBody());
                                    response.setBody("Product deleted successfully.");
                                } else if (command.equals("get")) {
                                    response.setBody(productController.getAllProducts());
                                } else if (command.equals("find")) {
                                    Type reqType = new TypeToken<Request<Product>>(){}.getType();
                                    Request<Product> req = gson.fromJson(requestJson, reqType);
                                    Product found = productController.getProduct(req.getBody().getProductCode());
                                    response.setBody(found);
                                }
                                break;

                            case "order":
                                OrderController orderController = (OrderController) rawController;
                                if (command.equals("save")) {
                                    Type reqType = new TypeToken<Request<Order>>(){}.getType();
                                    Request<Order> req = gson.fromJson(requestJson, reqType);
                                    orderController.saveOrder(req.getBody());
                                    response.setBody("Order saved successfully.");
                                } else if (command.equals("delete")) {
                                    Type reqType = new TypeToken<Request<Order>>(){}.getType();
                                    Request<Order> req = gson.fromJson(requestJson, reqType);
                                    orderController.deleteOrder(req.getBody());
                                    response.setBody("Order deleted successfully.");
                                } else if (command.equals("get")) {
                                    response.setBody(orderController.getAllOrders());
                                } else if (command.equals("find")) {
                                    Type reqType = new TypeToken<Request<Order>>(){}.getType();
                                    Request<Order> req = gson.fromJson(requestJson, reqType);
                                    Order found = orderController.getOrder(req.getBody().getOrderId());
                                    response.setBody(found);
                                }
                                break;

                            case "delivery":
                                DeliveryController deliveryController = (DeliveryController) rawController;
                                if (command.equals("save")) {
                                    Type reqType = new TypeToken<Request<Delivery>>(){}.getType();
                                    Request<Delivery> req = gson.fromJson(requestJson, reqType);
                                    deliveryController.saveDelivery(req.getBody());
                                    response.setBody("Delivery saved successfully.");
                                } else if (command.equals("delete")) {
                                    Type reqType = new TypeToken<Request<Delivery>>(){}.getType();
                                    Request<Delivery> req = gson.fromJson(requestJson, reqType);
                                    deliveryController.deleteDelivery(req.getBody());
                                    response.setBody("Delivery deleted successfully.");
                                } else if (command.equals("get")) {
                                    response.setBody(deliveryController.getAllDeliveries());
                                } else if (command.equals("find")) {
                                    Type reqType = new TypeToken<Request<Delivery>>(){}.getType();
                                    Request<Delivery> req = gson.fromJson(requestJson, reqType);
                                    Delivery found = deliveryController.getDelivery(req.getBody().getDeliveryOrder().getOrderId());
                                    response.setBody(found);
                                }
                                break;
                        }
                    }

                } catch (Exception e) {
                    response.setBody("Error processing request: " + e.getMessage());
                }

                // 4. המרת התשובה בחזרה ל-JSON ושליחה ללקוח
                String responseJson = gson.toJson(response);
                writer.println(responseJson);
            }

        } catch (IOException e) {
            System.err.println("HandleRequest Error: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}