package com.hit.client.model;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/*זו המחלקה היחידה באפליקציה שמדברת ישירות עם השרת (Sockets).
 כל פעם שכפתור באפליקציה ירצה למשל לשמור לקוח,
 הוא ייצר JSON,
 יזרוק אותו למחלקה הזו,
 והמחלקה הזו תחזיר לו את התשובה מהשרת.*/

public class ClientNetworkManager {

    // כתובת השרת והפורט - חייב להיות תואם למה שהגדרתם ב-ServerDriver
    private final String host = "localhost";
    private final int port = 34567;

    // פונקציה שמקבלת JSON של בקשה, שולחת לשרת, ומחזירה JSON של תשובה
    public String sendRequestAndGetResponse(String jsonRequest) {
        String responseJson = "";

        // שימוש ב-Decorators כפי שנלמד
        try (Socket socket = new Socket(host, port);
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             Scanner reader = new Scanner(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Client sending: " + jsonRequest);

            // שליחת הבקשה
            writer.println(jsonRequest);

            // קבלת התשובה
            if (reader.hasNextLine()) {
                responseJson = reader.nextLine();
                System.out.println("Client received: " + responseJson);
            }

        } catch (IOException e) {
            System.err.println("Network Error: Could not connect to server. " + e.getMessage());
        }

        return responseJson;
    }
}
