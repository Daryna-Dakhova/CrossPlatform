package conference;

import javax.swing.JOptionPane;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.InetAddress;

public class MessangerImpl implements Messanger {
    private InetAddress addr;
    private int port;
    private String name;
    private UITasks ui;
    private MulticastSocket group;

    public MessangerImpl(InetAddress addr, int port, String name, UITasks ui) {
        this.addr = addr;
        this.port = port;
        this.name = name;
        this.ui = ui;
        try {
            group = new MulticastSocket(port);
            group.setTimeToLive(2);
            group.joinGroup(addr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        Thread t = new Receiver();
        t.start();
    }

    @Override
    public void stop() {
        // Реалізація методу stop()
        try {
            group.leaveGroup(addr);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Помилка від'єднання\n" +
                    e.getMessage());
        } finally {
            group.close();
        }
    }

    @Override
    public void send(String message) { // Змінено сигнатуру методу
        // Реалізація методу send()
        try {
            String msg = name + ": " + message;
            byte[] out = msg.getBytes();
            DatagramPacket pkt = new DatagramPacket(out, out.length, addr, port);
            group.send(pkt);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Помилка відправлення\n" +
                    e.getMessage());
        }
    }

    private class Receiver extends Thread {
        public void run() {
            try {
                byte[] in = new byte[512];
                DatagramPacket pkt = new DatagramPacket(in, in.length);
                while (true) {
                    group.receive(pkt);
                    ui.setText(new String(pkt.getData(), 0, pkt.getLength()));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Помилка прийому\n" +
                        e.getMessage());
            }
        }
    }
}