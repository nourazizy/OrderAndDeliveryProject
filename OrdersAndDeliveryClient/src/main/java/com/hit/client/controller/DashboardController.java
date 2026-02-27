package com.hit.client.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.hit.client.model.ClientNetworkManager;
import com.hit.dm.Customer;
import com.hit.dm.Product;
import com.hit.dm.Order;
import com.hit.dm.Delivery;
import com.hit.server.Request;
import com.hit.server.Response;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardController {

    // --- מסך לקוחות ---
    @FXML private TextField custId;
    @FXML private TextField custName;
    @FXML private TextField custPhone;
    @FXML private TextField custAddress;
    @FXML private TextField custDob;
    @FXML private Label customerStatus;

    // --- מסך מוצרים ---
    @FXML private TextField prodId;
    @FXML private TextField prodName;
    @FXML private TextField prodPrice;
    @FXML private Label productStatus;

    // --- מסך הזמנות ---
    @FXML private TextField orderId;
    @FXML private TextField orderCustomerId;
    @FXML private Label orderStatus;

    // --- מסך משלוחים ---
    @FXML private TextField deliveryId;
    @FXML private TextField storeAddress;
    @FXML private TextField destAddress;
    @FXML private Label deliveryStatus;

    private ClientNetworkManager networkManager;
    private Gson gson;

    public DashboardController() {
        this.networkManager = new ClientNetworkManager();
        this.gson = new Gson();
    }

    // ==========================================
    //            לקוחות
    // ==========================================
    @FXML
    private void handleSaveCustomer() {
        try {
            long id = Long.parseLong(custId.getText());
            Customer customer = new Customer(id, custName.getText(), custPhone.getText(), custAddress.getText(), custDob.getText());
            sendRequest("customer/save", customer, customerStatus, "Saved successfully!");
            custId.clear(); custName.clear(); custPhone.clear(); custAddress.clear(); custDob.clear();
        } catch (Exception e) { customerStatus.setText("Error: " + e.getMessage()); }
    }

    @FXML
    private void handleLoadCustomers() {
        try {
            String jsonResponse = fetchRawData("customer/get");
            Type type = new TypeToken<Response<List<Customer>>>(){}.getType();
            Response<List<Customer>> response = gson.fromJson(jsonResponse, type);

            if (response != null && response.getBody() != null && !response.getBody().isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (Customer c : response.getBody()) { sb.append(c.toString()).append("\n\n"); }
                showPopup("✨ Customers List", sb.toString());
                customerStatus.setText("Loaded successfully!");
            } else { showPopup("✨ Customers List", "No customers found."); }
        } catch (Exception e) { customerStatus.setText("Load Error: " + e.getMessage()); }
    }

    @FXML
    private void handleFindCustomer() {
        try {
            long id = Long.parseLong(custId.getText());
            Customer tempCustomer = new Customer(id, "", "", "", "");
            String jsonResponse = fetchRawDataWithBody("customer/find", tempCustomer);

            Type type = new TypeToken<Response<Customer>>(){}.getType();
            Response<Customer> response = gson.fromJson(jsonResponse, type);

            if (response != null && response.getBody() != null) {
                showPopup("🔍 Found Customer", response.getBody().toString());
                customerStatus.setText("Customer found!");
            } else { showPopup("🔍 Not Found", "No customer found with ID: " + id); }
        } catch (Exception e) { customerStatus.setText("Error: Please enter a valid ID to search."); }
    }

    @FXML
    private void handleDeleteCustomer() {
        try {
            long id = Long.parseLong(custId.getText());
            Customer customer = new Customer(id, "", "", "", "");
            sendRequest("customer/delete", customer, customerStatus, "Deleted successfully!");
            custId.clear();
        } catch (Exception e) { customerStatus.setText("Error: Please provide a valid ID to delete."); }
    }

    // ==========================================
    //            מוצרים
    // ==========================================
    @FXML
    private void handleSaveProduct() {
        try {
            int code = Integer.parseInt(prodId.getText());
            Product product = new Product(prodName.getText(), "General Brand", code, Double.parseDouble(prodPrice.getText()), 1.0);
            sendRequest("product/save", product, productStatus, "Saved successfully!");
            prodId.clear(); prodName.clear(); prodPrice.clear();
        } catch (Exception e) { productStatus.setText("Error: " + e.getMessage()); }
    }

    @FXML
    private void handleLoadProducts() {
        try {
            String jsonResponse = fetchRawData("product/get");
            Type type = new TypeToken<Response<List<Product>>>(){}.getType();
            Response<List<Product>> response = gson.fromJson(jsonResponse, type);

            if (response != null && response.getBody() != null && !response.getBody().isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (Product p : response.getBody()) { sb.append(p.toString()).append("\n\n"); }
                showPopup("📦 Products List", sb.toString());
                productStatus.setText("Loaded successfully!");
            } else { showPopup("📦 Products List", "No products found."); }
        } catch (Exception e) { productStatus.setText("Load Error: " + e.getMessage()); }
    }

    @FXML
    private void handleFindProduct() {
        try {
            int code = Integer.parseInt(prodId.getText());
            Product tempProduct = new Product("", "", code, 0, 0);
            String jsonResponse = fetchRawDataWithBody("product/find", tempProduct);

            Type type = new TypeToken<Response<Product>>(){}.getType();
            Response<Product> response = gson.fromJson(jsonResponse, type);

            if (response != null && response.getBody() != null) {
                showPopup("🔍 Found Product", response.getBody().toString());
                productStatus.setText("Product found!");
            } else { showPopup("🔍 Not Found", "No product found with Code: " + code); }
        } catch (Exception e) { productStatus.setText("Error: Please enter a valid Code to search."); }
    }

    @FXML
    private void handleDeleteProduct() {
        try {
            int code = Integer.parseInt(prodId.getText());
            Product product = new Product("", "", code, 0, 0);
            sendRequest("product/delete", product, productStatus, "Deleted successfully!");
            prodId.clear();
        } catch (Exception e) { productStatus.setText("Error: Please provide a valid Product Code to delete."); }
    }

    // ==========================================
    //            הזמנות
    // ==========================================
    @FXML
    private void handleSaveOrder() {
        try {
            long oId = Long.parseLong(orderId.getText());
            long cId = Long.parseLong(orderCustomerId.getText());
            Order order = new Order(oId, new Customer(cId, "Temp", "", "", ""));
            sendRequest("order/save", order, orderStatus, "Saved successfully!");
            orderId.clear(); orderCustomerId.clear();
        } catch (Exception e) { orderStatus.setText("Error: " + e.getMessage()); }
    }

    @FXML
    private void handleLoadOrders() {
        try {
            String jsonResponse = fetchRawData("order/get");
            Type type = new TypeToken<Response<List<Order>>>(){}.getType();
            Response<List<Order>> response = gson.fromJson(jsonResponse, type);

            if (response != null && response.getBody() != null && !response.getBody().isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (Order o : response.getBody()) { sb.append(o.toString()).append("\n\n"); }
                showPopup("🛒 Orders List", sb.toString());
                orderStatus.setText("Loaded successfully!");
            } else { showPopup("🛒 Orders List", "No orders found."); }
        } catch (Exception e) { orderStatus.setText("Load Error: " + e.getMessage()); }
    }

    @FXML
    private void handleFindOrder() {
        try {
            long oId = Long.parseLong(orderId.getText());
            Order tempOrder = new Order(oId, new Customer(0, "", "", "", ""));
            String jsonResponse = fetchRawDataWithBody("order/find", tempOrder);

            Type type = new TypeToken<Response<Order>>(){}.getType();
            Response<Order> response = gson.fromJson(jsonResponse, type);

            if (response != null && response.getBody() != null) {
                showPopup("🔍 Found Order", response.getBody().toString());
                orderStatus.setText("Order found!");
            } else { showPopup("🔍 Not Found", "No order found with ID: " + oId); }
        } catch (Exception e) { orderStatus.setText("Error: Please enter a valid Order ID to search."); }
    }

    @FXML
    private void handleDeleteOrder() {
        try {
            long oId = Long.parseLong(orderId.getText());
            Order order = new Order(oId, new Customer(0, "", "", "", ""));
            sendRequest("order/delete", order, orderStatus, "Deleted successfully!");
            orderId.clear();
        } catch (Exception e) { orderStatus.setText("Error: Please provide a valid Order ID to delete."); }
    }

    // ==========================================
    //            משלוחים
    // ==========================================
    @FXML
    private void handleSaveDelivery() {
        try {
            long dId = Long.parseLong(deliveryId.getText());
            Delivery delivery = new Delivery(storeAddress.getText(), new Order(dId, new Customer(0, "Temp", "", destAddress.getText(), "")));
            sendRequest("delivery/save", delivery, deliveryStatus, "Saved successfully!");
            deliveryId.clear(); storeAddress.clear(); destAddress.clear();
        } catch (Exception e) { deliveryStatus.setText("Error: " + e.getMessage()); }
    }

    @FXML
    private void handleLoadDeliveries() {
        try {
            String jsonResponse = fetchRawData("delivery/get");
            Type type = new TypeToken<Response<List<Delivery>>>(){}.getType();
            Response<List<Delivery>> response = gson.fromJson(jsonResponse, type);

            if (response != null && response.getBody() != null && !response.getBody().isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (Delivery d : response.getBody()) { sb.append(d.toString()).append("\n\n"); }
                showPopup("🚚 Deliveries & Paths", sb.toString());
                deliveryStatus.setText("Loaded successfully!");
            } else { showPopup("🚚 Deliveries & Paths", "No deliveries found."); }
        } catch (Exception e) { deliveryStatus.setText("Load Error: " + e.getMessage()); }
    }

    @FXML
    private void handleFindDelivery() {
        try {
            long dId = Long.parseLong(deliveryId.getText());
            Delivery tempDelivery = new Delivery("", new Order(dId, new Customer(0, "", "", "", "")));
            String jsonResponse = fetchRawDataWithBody("delivery/find", tempDelivery);

            Type type = new TypeToken<Response<Delivery>>(){}.getType();
            Response<Delivery> response = gson.fromJson(jsonResponse, type);

            if (response != null && response.getBody() != null) {
                showPopup("🔍 Found Delivery", response.getBody().toString());
                deliveryStatus.setText("Delivery found!");
            } else { showPopup("🔍 Not Found", "No delivery found for Order ID: " + dId); }
        } catch (Exception e) { deliveryStatus.setText("Error: Please enter a valid ID to search."); }
    }

    @FXML
    private void handleDeleteDelivery() {
        try {
            long dId = Long.parseLong(deliveryId.getText());
            Delivery delivery = new Delivery("", new Order(dId, new Customer(0, "", "", "", "")));
            sendRequest("delivery/delete", delivery, deliveryStatus, "Deleted successfully!");
            deliveryId.clear();
        } catch (Exception e) { deliveryStatus.setText("Error: Please provide a valid Delivery ID to delete."); }
    }

    // ==========================================
    //         פונקציות עזר
    // ==========================================

    private <T> void sendRequest(String action, T body, Label statusLabel, String successMessage) {
        Map<String, String> headers = new HashMap<>();
        headers.put("action", action);
        Request<T> request = new Request<>(headers, body);
        networkManager.sendRequestAndGetResponse(gson.toJson(request));
        statusLabel.setText(successMessage);
    }

    private String fetchRawData(String action) {
        Map<String, String> headers = new HashMap<>();
        headers.put("action", action);
        Request<String> request = new Request<>(headers, "");
        return networkManager.sendRequestAndGetResponse(gson.toJson(request));
    }

    // פונקציה חדשה שמאפשרת לשלוח בקשת "GET" אבל עם אובייקט בתוך הגוף (כדי שהשרת ידע מה לחפש)
    private <T> String fetchRawDataWithBody(String action, T body) {
        Map<String, String> headers = new HashMap<>();
        headers.put("action", action);
        Request<T> request = new Request<>(headers, body);
        return networkManager.sendRequestAndGetResponse(gson.toJson(request));
    }

    // ==========================================
    //   חלון הפופ-אפ המעוצב והמורחב!
    // ==========================================
    private void showPopup(String title, String content) {
        Stage popupStage = new Stage();
        popupStage.initStyle(StageStyle.TRANSPARENT);

        VBox card = new VBox(20);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(40)); // הגדלנו את הריפוד הפנימי שיהיה יותר אוויר
        card.setStyle("-fx-background-color: white; -fx-background-radius: 20;");

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(25);
        dropShadow.setOffsetY(10);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.25));
        card.setEffect(dropShadow);

        Label titleLabel = new Label(title);
        // הגדלנו מעט את הכותרת שתתאים לגודל החדש של החלון
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: linear-gradient(to right, #2980b9, #8e44ad); -fx-font-family: 'Segoe UI', sans-serif;");

        TextArea textArea = new TextArea(content);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        // הנה ההגדלה המשמעותית של תיבת הטקסט!
        textArea.setPrefHeight(450); // גובה קודם: 350
        textArea.setPrefWidth(650);  // רוחב קודם: 500

        textArea.setStyle(
                "-fx-font-family: 'Segoe UI', sans-serif; " +
                        "-fx-font-size: 16px; " +
                        "-fx-text-fill: #2c3e50; " +
                        "-fx-control-inner-background: #f8f9fa; " +
                        "-fx-background-color: transparent; " +
                        "-fx-border-color: #dcdde1; " +
                        "-fx-border-radius: 12; " +
                        "-fx-background-radius: 12; " +
                        "-fx-padding: 15;"
        );

        Button closeBtn = new Button("Close");
        closeBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #2980b9, #8e44ad); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 16px; " +
                        "-fx-padding: 12 40; " +
                        "-fx-background-radius: 30; " +
                        "-fx-cursor: hand;"
        );
        closeBtn.setOnAction(e -> popupStage.close());

        card.getChildren().addAll(titleLabel, textArea, closeBtn);

        StackPane rootPane = new StackPane(card);
        rootPane.setStyle("-fx-background-color: transparent; -fx-padding: 40;");

        Scene scene = new Scene(rootPane);
        scene.setFill(Color.TRANSPARENT);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }
}