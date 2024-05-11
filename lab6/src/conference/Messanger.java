package conference;

public interface Messanger {
    void start();
    void stop();
    void send(String message); // Додали параметр для повідомлення
}