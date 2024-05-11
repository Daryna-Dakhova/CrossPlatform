package conference;

import java.net.InetAddress;
import java.util.Scanner;

public class MulticastSenderReceiver {
    public static void main(String[] args) {
        try {
            InetAddress addr = InetAddress.getByName("224.0.0.1");
            int port = 3456;

            UITasks ui = new UITasks() {
                @Override
                public String getMessage() {
                    Scanner scanner = new Scanner(System.in);
                    return scanner.nextLine();
                }

                @Override
                public void setText(String txt) {
                    System.out.println(txt);
                }
            };

            System.out.print("Enter your name: ");
            Scanner scanner = new Scanner(System.in);
            String name = scanner.nextLine();

            Messanger messanger = new MessangerImpl(addr, port, name, ui);
            messanger.start();

            String input;
            while (!(input = scanner.nextLine()).equals("exit")) {
                messanger.send(input);
            }

            messanger.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
