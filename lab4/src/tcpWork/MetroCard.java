package tcpWork;

public class MetroCard {
    private int cardID;
    private User user;
    private String college;
    private double balance;

    public MetroCard(int cardID, User user, String college, double balance) {
        this.cardID = cardID;
        this.user = user;
        this.college = college;
        this.balance = balance;
    }

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "MetroCard{" +
                "cardID=" + cardID +
                ", user=" + user +
                ", college='" + college + '\'' +
                ", balance=" + balance +
                '}';
    }
}
