import java.util.ArrayList;
import java.util.List;

public class ActiveUsers {
    private List<User> userList;

    public ActiveUsers() {
        this.userList = new ArrayList<>();
    }

    public void add(User user) {
        userList.add(user);
    }

    public int size() {
        return userList.size();
    }

    @Override
    public String toString() {
        return "ActiveUsers{" +
                "userList=" + userList +
                '}';
    }
}
