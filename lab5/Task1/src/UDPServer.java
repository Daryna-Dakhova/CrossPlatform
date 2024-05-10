import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

public class UDPServer {
    private DatagramSocket socket = null;
    private DatagramPacket packet = null;
    private Set<User> userList = new HashSet<>();

    public UDPServer(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public void work(int bufferSize) {
        try {
            System.out.println("Server start...");
            while (true) {
                getUserData(bufferSize);
                log(packet.getAddress(), packet.getPort());
                sendUserData();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        } finally {
            System.out.println("Server end...");
            socket.close();
        }
    }

    private void log(InetAddress address, int port) {
        System.out.println("Request from: " + address.getHostAddress() +
                " port: " + port);
    }

    private void clear(byte[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }
    }

    private void getUserData(int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        User usr = new User(packet.getAddress(), packet.getPort());
        userList.add(usr);
        clear(buffer);
    }

    private void sendUserData() throws IOException {
        for (User user : userList) {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bout);
            out.writeObject(user);
            byte[] buffer = bout.toByteArray();
            packet = new DatagramPacket(buffer, buffer.length, user.getAddress(), user.getPort());
            socket.send(packet);
        }
    }

    public static void main(String[] args) {
        (new UDPServer(1501)).work(256);
    }
}
