package conference;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

public class GUI implements UITasks {
    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;
    private JTextField groupField;
    private JTextField portField;
    private JTextField nameField;
    private JButton connectButton;
    private JButton disconnectButton;
    private JButton clearButton;
    private JButton exitButton;
    private JButton sendButton;
    private Messanger messanger;

    public GUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.WEST);
        panel.setLayout(new GridLayout(0, 1));

        JLabel groupLabel = new JLabel("Group:");
        panel.add(groupLabel);

        groupField = new JTextField();
        panel.add(groupField);
        groupField.setColumns(10);

        JLabel portLabel = new JLabel("Port:");
        panel.add(portLabel);

        portField = new JTextField();
        panel.add(portField);
        portField.setColumns(5);

        JLabel nameLabel = new JLabel("Name:");
        panel.add(nameLabel);

        nameField = new JTextField();
        panel.add(nameField);
        nameField.setColumns(10);

        connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String group = groupField.getText();
                    int port = Integer.parseInt(portField.getText());
                    String name = nameField.getText();

                    if (!group.isEmpty() && port > 0 && port < 65536 && !name.isEmpty()) {
                        InetAddress addr = InetAddress.getByName(group);
                        messanger = new MessangerImpl(addr, port, name, GUI.this);
                        messanger.start();
                        connectButton.setBackground(Color.GREEN); // Зміна кольору кнопки при з'єднанні
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid input! Please provide valid group, port, and name.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error connecting to the server: " + ex.getMessage());
                }
            }
        });
        panel.add(connectButton);

        disconnectButton = new JButton("Disconnect");
        disconnectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (messanger != null) {
                    messanger.stop();
                    connectButton.setBackground(null); // Зміна кольору кнопки при роз'єднанні
                }
            }
        });
        panel.add(disconnectButton);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });
        panel.add(clearButton);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(exitButton);

        textField = new JTextField();
        frame.getContentPane().add(textField, BorderLayout.NORTH);
        textField.setColumns(30);

        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (messanger != null) {
                    String message = textField.getText();
                    messanger.send(message);
                }
            }
        });
        frame.getContentPane().add(sendButton, BorderLayout.SOUTH);
    }

    @Override
    public String getMessage() {
        return textField.getText();
    }

    @Override
    public void setText(String txt) {
        textArea.append(txt + "\n");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI window = new GUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
