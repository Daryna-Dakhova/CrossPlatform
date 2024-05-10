import java.io.*;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) {
        // Параметри сервера
        String serverAddress = "127.0.0.1"; // IP-адреса сервера
        int serverPort = 54321;  // Порт сервера

        try {
            // Встановлення з'єднання з сервером
            Socket socket = new Socket(serverAddress, serverPort);

            // Створення об'єктного потоку для відправлення об'єкта на сервер
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            // Формуємо об'єкт завдання для обчислення факторіалу
            int num = 37;
            FactorialTask task = new FactorialTask(num);

            // Відправляємо ім'я класу та сам об'єкт завдання на сервер
            outputStream.writeObject(FactorialTask.class.getName());
            outputStream.writeObject(task);

            // Створюємо об'єктний потік вводу для отримання результату
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            // Отримуємо і виводимо результати розрахунків
            Result r = (Result) inputStream.readObject();
            System.out.println("Result: " + r.output() + ", Time taken: " + r.scoreTime() + "ms");

            // Закриття з'єднання
            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
