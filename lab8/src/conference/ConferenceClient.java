package conference;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class ConferenceClient {
    private JFrame frame;
    private JTextField textFieldHost;
    private JTextField textFieldPort;
    private JTextField nameField;
    private JTextField emailField;
    private JTextArea infoArea;
    private ConferenceService service;

    public ConferenceClient() {
        frame = new JFrame("Conference Registration");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel hostLabel = new JLabel("Host:");
        hostLabel.setBounds(10, 10, 80, 25);
        frame.add(hostLabel);

        textFieldHost = new JTextField("localhost");
        textFieldHost.setBounds(100, 10, 160, 25);
        frame.add(textFieldHost);

        JLabel portLabel = new JLabel("Port:");
        portLabel.setBounds(10, 40, 80, 25);
        frame.add(portLabel);

        textFieldPort = new JTextField("1099");
        textFieldPort.setBounds(100, 40, 160, 25);
        frame.add(textFieldPort);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 70, 80, 25);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(100, 70, 160, 25);
        frame.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 100, 80, 25);
        frame.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(100, 100, 160, 25);
        frame.add(emailField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(10, 130, 250, 25);
        frame.add(registerButton);

        JButton getInfoButton = new JButton("Get Info");
        getInfoButton.setBounds(10, 160, 250, 25);
        frame.add(getInfoButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(10, 190, 250, 25);
        frame.add(clearButton);

        JButton finishButton = new JButton("Finish");
        finishButton.setBounds(10, 220, 250, 25);
        frame.add(finishButton);

        infoArea = new JTextArea();
        infoArea.setBounds(10, 250, 360, 100);
        frame.add(infoArea);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerParticipant();
            }
        });

        getInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getParticipantsInfo();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }

    private void connectToServer() {
        try {
            String host = textFieldHost.getText();
            int port = Integer.parseInt(textFieldPort.getText());
            Registry registry = LocateRegistry.getRegistry(host, port);
            service = (ConferenceService) registry.lookup("ConferenceService");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error connecting to server", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void registerParticipant() {
        try {
            if (service == null) {
                connectToServer();
            }

            String name = nameField.getText();
            String email = emailField.getText();
            Participant participant = new Participant(name, email);
            int count = service.registerParticipant(participant);
            JOptionPane.showMessageDialog(frame, "Registered successfully! Total participants: " + count);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error registering participant", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getParticipantsInfo() {
        try {
            if (service == null) {
                connectToServer();
            }

            List<Participant> participants = service.getParticipants();
            StringBuilder info = new StringBuilder("Participants:\n");
            for (Participant participant : participants) {
                info.append("Name: ").append(participant.getName()).append(", Email: ").append(participant.getEmail()).append("\n");
            }
            infoArea.setText(info.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error getting participants info", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        infoArea.setText("");
    }

    public static void main(String[] args) {
        new ConferenceClient();
    }
}
