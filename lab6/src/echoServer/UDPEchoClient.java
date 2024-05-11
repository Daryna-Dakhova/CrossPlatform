package echoServer;

import java.io.*;
import java.net.*;

public class UDPEchoClient {
    public final static int PORT = 1501;

    public static void main(String[] args) {
        String hostname = "localhost";
        if (args.length > 0) {
            hostname = args[0];
        }
        try {
            InetAddress serverAddress = InetAddress.getByName(hostname);
            DatagramSocket socket = new DatagramSocket();

            // Відправка повідомлень на сервер
            Thread sender = new SenderThread(socket, serverAddress, PORT);
            sender.start();

            // Отримання відповідей від сервера
            Thread receiver = new ReceiverThread(socket);
            receiver.start();
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (SocketException ex) {
            System.err.println(ex);
        }
    }
}



