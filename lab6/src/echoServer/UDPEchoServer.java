package echoServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

// Клас UDPEchoServer, який реалізує серверну частину
public class UDPEchoServer extends UDPServer {
    private static final int DEFAULT_PORT = 1501; // Порт за замовчуванням
    private static final int TIMEOUT = 5000; // Таймаут очікування у мілісекундах
    private boolean running;
    private final int port;

    public UDPEchoServer(int port) {
        super(port);
        this.port = port;
        this.running = true;
    }

    public UDPEchoServer() {
        this(DEFAULT_PORT); // Виклик конструктора зі значенням за замовчуванням
    }

    @Override
    public void respond(DatagramSocket socket, DatagramPacket request) throws IOException {
        // Створення відповіді на основі отриманих даних від клієнта
        DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());

        // Відправлення відповіді клієнту через сокет
        socket.send(reply);
    }

    public void stopServer() {
        this.running = false;
    }

    public int getPort() {
        return port;
    }

    public static void main(String[] args) {
        // Порт 1501 використовується для сервера
        UDPEchoServer server = new UDPEchoServer();
        Thread serverThread = new Thread(server);
        serverThread.start();

        System.out.println("UDPEchoServer запущений на порті " + server.getPort() + "...");

        // Додано затримку перед завершенням роботи сервера
        try {
            Thread.sleep(30000); // Затримка у 30 секунд (можна змінити на потрібне значення)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Після закінчення затримки сервер завершує роботу
        server.stopServer();
        System.out.println("Сервер завершує роботу...");
    }

    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(port);
            socket.setSoTimeout(TIMEOUT); // Встановлення таймауту на отримання датаграм

            byte[] buffer = new byte[65507];
            while (running) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                try {
                    socket.receive(request);
                    respond(socket, request);
                } catch (java.net.SocketTimeoutException ex) {
                    // Пропускаємо помилку, якщо таймаут відбувся
                    continue;
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
