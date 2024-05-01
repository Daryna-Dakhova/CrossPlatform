
package tcpWork;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class MetroCardServer {
    private static final int PORT = 12345;
    private HashMap<Integer, MetroCard> cardRegistry;

    public MetroCardServer() {
        cardRegistry = new HashMap<>();
    }

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                // Створення нового обробника клієнта
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String request;
                while ((request = in.readLine()) != null) {
                    // Обробка запиту в залежності від його типу

                    // Видача нової картки та реєстрація її в системі
                    if (request.startsWith("ISSUE_NEW_CARD")) {
                        String[] tokens = request.split(":");
                        int cardID = Integer.parseInt(tokens[1]);
                        String name = tokens[2];
                        String surName = tokens[3];
                        String sex = tokens[4];
                        String birthday = tokens[5];
                        String college = tokens[6];

                        User user = new User(name, surName, sex, birthday);
                        MetroCard newCard = new MetroCard(cardID, user, college, 0.0);
                        cardRegistry.put(cardID, newCard);

                        out.println("NEW_CARD_ISSUED");
                    }

                    // Отримання інформації про клієнта
                    else if (request.startsWith("GET_CLIENT_INFO")) {
                        int cardID = Integer.parseInt(request.split(":")[1]);
                        if (cardRegistry.containsKey(cardID)) {
                            MetroCard card = cardRegistry.get(cardID);
                            out.println(card);
                        } else {
                            out.println("CARD_NOT_FOUND");
                        }
                    }

                    // Поповнення рахунку
                    else if (request.startsWith("TOP_UP_BALANCE")) {
                        String[] tokens = request.split(":");
                        int cardID = Integer.parseInt(tokens[1]);
                        double amount = Double.parseDouble(tokens[2]);

                        if (cardRegistry.containsKey(cardID)) {
                            MetroCard card = cardRegistry.get(cardID);
                            card.setBalance(card.getBalance() + amount);
                            out.println("BALANCE_TOPPED_UP");
                        } else {
                            out.println("CARD_NOT_FOUND");
                        }
                    }

                    // Оплата поїздки
                    else if (request.startsWith("PAY_FOR_TRIP")) {
                        String[] tokens = request.split(":");
                        int cardID = Integer.parseInt(tokens[1]);
                        double fare = Double.parseDouble(tokens[2]);

                        if (cardRegistry.containsKey(cardID)) {
                            MetroCard card = cardRegistry.get(cardID);
                            if (card.getBalance() >= fare) {
                                card.setBalance(card.getBalance() - fare);
                                out.println("TRIP_PAID");
                            } else {
                                out.println("INSUFFICIENT_FUNDS");
                            }
                        } else {
                            out.println("CARD_NOT_FOUND");
                        }
                    }

                    // Отримання залишку коштів на картці
                    else if (request.startsWith("CHECK_BALANCE")) {
                        int cardID = Integer.parseInt(request.split(":")[1]);
                        if (cardRegistry.containsKey(cardID)) {
                            MetroCard card = cardRegistry.get(cardID);
                            out.println(card.getBalance());
                        } else {
                            out.println("CARD_NOT_FOUND");
                        }
                    }
                }

                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MetroCardServer server = new MetroCardServer();
        server.startServer();
    }
}
