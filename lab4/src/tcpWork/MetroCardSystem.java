package tcpWork;

import java.util.HashMap;

public class MetroCardSystem {
    private HashMap<Integer, MetroCard> cardRegistry;

    public MetroCardSystem() {
        cardRegistry = new HashMap<>();
    }

    // Функція для видачі нової картки та реєстрації її в системі
    public void issueNewCard(int cardID, User user, String college) {
        if (!cardRegistry.containsKey(cardID)) {
            MetroCard newCard = new MetroCard(cardID, user, college, 0.0);
            cardRegistry.put(cardID, newCard);
            System.out.println("Картка з ID " + cardID + " видається та реєструється в системі.");
        } else {
            System.out.println("Цей ID вже використовується. Спробуйте інший.");
        }
    }

    // Функція для отримання інформації про клієнта
    public void getClientInfo(int cardID) {
        if (cardRegistry.containsKey(cardID)) {
            MetroCard card = cardRegistry.get(cardID);
            System.out.println("Інформація про карту з ID " + cardID + ":");
            System.out.println(card);
        } else {
            System.out.println("Карта з ID " + cardID + " не зареєстрована в системі.");
        }
    }

    // Функція для поповнення рахунку
    public void topUpBalance(int cardID, double amount) {
        if (cardRegistry.containsKey(cardID)) {
            MetroCard card = cardRegistry.get(cardID);
            card.setBalance(card.getBalance() + amount);
            System.out.println("Баланс картки з ID " + cardID + " поповнено на " + amount + ".");
        } else {
            System.out.println("Карта з ID " + cardID + " не зареєстрована в системі.");
        }
    }

    // Функція для оплати поїздки
    public void payForTrip(int cardID, double fare) {
        if (cardRegistry.containsKey(cardID)) {
            MetroCard card = cardRegistry.get(cardID);
            if (card.getBalance() >= fare) {
                card.setBalance(card.getBalance() - fare);
                System.out.println("Оплата поїздки на суму " + fare + " одиниць для картки з ID " + cardID + ".");
            } else {
                System.out.println("Недостатньо коштів на картці для оплати поїздки.");
            }
        } else {
            System.out.println("Карта з ID " + cardID + " не зареєстрована в системі.");
        }
    }

    // Функція для отримання залишку коштів на картці
    public void checkBalance(int cardID) {
        if (cardRegistry.containsKey(cardID)) {
            MetroCard card = cardRegistry.get(cardID);
            System.out.println("Залишок коштів на картці з ID " + cardID + ": " + card.getBalance());
        } else {
            System.out.println("Карта з ID " + cardID + " не зареєстрована в системі.");
        }
    }
}
