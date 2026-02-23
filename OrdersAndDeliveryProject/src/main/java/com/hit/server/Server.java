package main.java.com.hit.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private int port;
    private boolean isServerRunning;
    private ExecutorService threadPool; // מנהל את הת'רדים בצורה יעילה

    public Server(int port) {
        this.port = port;
        this.isServerRunning = true;
        // יצירת ThreadPool שיכול לטפל בעד 10 לקוחות במקביל (ניתן לשינוי)
        this.threadPool = Executors.newFixedThreadPool(10);
    }

    @Override
    public void run() {
        System.out.println("Server is starting on port " + port + "...");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is up and waiting for clients...");

            while (isServerRunning) {
                try {
                    // השרת עוצר כאן ומחכה שלקוח (Client) יתחבר
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New client connected: " + clientSocket.getInetAddress());

                    // ברגע שלקוח התחבר, אנחנו שולחים אותו לטיפול ב-Thread נפרד
                    // המחלקה HandleRequest היא זו שתבצע את הלוגיקה (חלק ג')
                    threadPool.execute(new HandleRequest(clientSocket));

                } catch (IOException e) {
                    if (isServerRunning) {
                        System.err.println("Error accepting client connection: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + port + ": " + e.getMessage());
        } finally {
            stopServer();
        }
    }

    // פונקציה לעצירה מסודרת של השרת
    public void stopServer() {
        isServerRunning = false;
        if (threadPool != null) {
            threadPool.shutdown();
        }
        System.out.println("Server has stopped.");
    }
}