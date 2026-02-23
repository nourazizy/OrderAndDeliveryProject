package main.java.com.hit.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.HashMap;

public class HandleRequest implements Runnable {
    private Socket clientSocket;
    private Gson gson;

    public HandleRequest(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.gson = new Gson();
    }

    @Override
    public void run() {
        // שימוש ב-Decorators (Data Stream ו-Buffered) בדיוק לפי עמוד 18 ב-PDF של ניסים
        try (DataInputStream reader = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
             DataOutputStream writer = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()))) {

            // 1. קריאת ה-JSON מהלקוח (בפורמט UTF)
            String requestJson = reader.readUTF();  /******CHECK THIS LINE********/
            System.out.println("HandleRequest: Received JSON: " + requestJson);

            // 2. פיענוח ה-JSON למפה (Map) בעזרת GSON
            Map<String, Object> requestMap = gson.fromJson(requestJson, new TypeToken<Map<String, Object>>(){}.getType());

            // שליפת ה-headers וה-body
            Map<String, String> headers = (Map<String, String>) requestMap.get("headers");
            String action = (headers != null) ? headers.get("action") : "";
            Object body = requestMap.get("body");

            // 3. לוגיקת הטיפול בבקשה
            Map<String, Object> responseMap = new HashMap<>();

            if ("calculatePath".equals(action)) {
                // כאן תחברו בהמשך את ה-OrderAndDeliveryService שלכם
                responseMap.put("status", "success");
                responseMap.put("data", "Shortest path calculated successfully");
            } else if ("ping".equals(action)) {
                responseMap.put("status", "success");
                responseMap.put("message", "pong");
            } else {
                responseMap.put("status", "error");
                responseMap.put("message", "Unknown action: " + action);
            }

            // 4. הפיכת התשובה ל-JSON ושליחה חזרה ב-writeUTF
            String responseJson = gson.toJson(responseMap);
            writer.writeUTF(responseJson);  /*********CHECK THIS LINE*********/
            writer.flush(); // מוודא שהמידע יוצא מהבאפר לרשת

        } catch (IOException e) {
            System.err.println("HandleRequest Error: " + e.getMessage());
        } finally {
            // סגירת ה-Socket בסיום הטיפול
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
