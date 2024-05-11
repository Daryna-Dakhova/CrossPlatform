package echoServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

// Клас ReceiverThread, що реалізує потік для отримання повідомлень від сервера
class ReceiverThread extends Thread {
    private final DatagramSocket socket;

    public ReceiverThread(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[65507];
        while (true) {
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(dp);
                String received = new String(dp.getData(), 0, dp.getLength(), "UTF-8");
                System.out.println("Received from server: " + received);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}
