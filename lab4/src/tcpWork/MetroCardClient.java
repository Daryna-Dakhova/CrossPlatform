
package tcpWork;

import java.io.*;
import java.net.*;

public class MetroCardClient {
    private static final String SERVER_IP = "127.0.0.1"; // IP-адреса сервера
    private static final int SERVER_PORT = 12345; // Порт сервера

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected to server...");

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);

            String userInputString;
            while ((userInputString = userInput.readLine()) != null) {
                // Надсилаємо запит серверу
                outToServer.println(userInputString);

                // Отримуємо відповідь від сервера
                String serverResponse = serverInput.readLine();
                System.out.println("Server response: " + serverResponse);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
