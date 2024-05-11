package echoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

// Клас SenderThread, що реалізує потік для відправки повідомлень на сервер
class SenderThread extends Thread {
    private final DatagramSocket socket;
    private final InetAddress serverAddress;
    private final int port;

    public SenderThread(DatagramSocket socket, InetAddress serverAddress, int port) {
        this.socket = socket;
        this.serverAddress = serverAddress;
        this.port = port;
    }

    @Override
    public void run() {
        try (BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {
            String message;
            while ((message = userInput.readLine()) != null) {
                if (message.equals("."))
                    break;
                byte[] data = message.getBytes("UTF-8");
                DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, port);
                socket.send(packet);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
