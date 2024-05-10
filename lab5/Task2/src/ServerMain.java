import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) {
        int serverPort = 54321; // Порт сервера

        try {
            // Створення серверного сокета
            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.out.println("Server started");

            while (true) {
                // Очікування підключення клієнта
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");

                // Створення об'єктного потоку вводу для отримання даних від клієнта
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

                // Отримання назви класу завдання та самого завдання від клієнта
                String className = (String) inputStream.readObject();
                Executable task = (Executable) inputStream.readObject();

                // Виміряємо час початку виконання задачі
                long startTime = System.nanoTime();

                // Виконання завдання
                Object result = task.execute();
                System.out.println("Factorial of " + ((FactorialTask) task).getNumber() + " is: " + result);

                // Виміряємо час завершення виконання задачі
                long endTime = System.nanoTime();

                // Обчислюємо час виконання в мілісекундах
                double elapsedTime = (endTime - startTime) / 1e6; // переведення наносекунд в мілісекунди

                // Створення об'єкту результату з часом виконання і відправка його клієнту
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                outputStream.writeObject(new ResultImpl(result, elapsedTime));

                // Закриття з'єднання
                inputStream.close();
                outputStream.close();
                clientSocket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
