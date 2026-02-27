package com.hit.client.controller;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.hit.client.model.ClientNetworkManager;
import com.hit.dm.Customer;
import com.hit.server.Request;

import java.util.HashMap;
import java.util.Map;

public class MainController {

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField customerNameField;

    @FXML
    private Label statusLabel;

    private ClientNetworkManager networkManager;
    private Gson gson;

    public MainController() {
        this.networkManager = new ClientNetworkManager();
        this.gson = new Gson();
    }

    @FXML
    private void handleSaveCustomer() {
        try {
            // 1. קריאת הנתונים מהמסך (ודאו שהבנאי של Customer מתאים לשלכם)
            long id = Long.parseLong(customerIdField.getText());
            String name = customerNameField.getText();
            Customer customer = new Customer(id, name, "לא הוזן", "לא הוזן", "לא הוזן"); // שנו את הפרמטרים אם הבנאי שלכם שונה

            // 2. אריזת הלקוח בתוך Request
            Map<String, String> headers = new HashMap<>();
            headers.put("action", "customer/save");
            Request<Customer> request = new Request<>(headers, customer);

            // 3. הפיכה ל-JSON ושליחה לשרת
            String jsonRequest = gson.toJson(request);
            statusLabel.setText("Sending to server...");

            String jsonResponse = networkManager.sendRequestAndGetResponse(jsonRequest);

            // 4. הצגת התשובה על המסך
            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                statusLabel.setText("Success! Server replied.");
                // אפשר לנקות את השדות אחרי הצלחה
                customerIdField.clear();
                customerNameField.clear();
            } else {
                statusLabel.setText("Error: No response from server.");
            }

        } catch (NumberFormatException e) {
            statusLabel.setText("Error: ID must be a number.");
        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
        }
    }
}