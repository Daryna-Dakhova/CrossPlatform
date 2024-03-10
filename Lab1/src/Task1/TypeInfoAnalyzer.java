package Task1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.*;

public class TypeInfoAnalyzer {

    public static String analyzeType(String typeName) {
        try {
            Class<?> clazz = Class.forName(typeName);
            return analyzeClass(clazz);
        } catch (ClassNotFoundException e) {
            return "Class not found: " + typeName;
        }
    }

    public static String analyzeClass(Class<?> clazz) {
        StringBuilder result = new StringBuilder();

        // Class name
        result.append("Class Name: ").append(clazz.getSimpleName()).append("\n");

        // Superclass
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null) {
            result.append("Superclass: ").append(superclass.getSimpleName()).append("\n");
        }

        // Implemented interfaces
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            result.append("Implemented Interfaces: ");
            for (Class<?> iface : interfaces) {
                result.append(iface.getSimpleName()).append(", ");
            }
            result.setLength(result.length() - 2); // Remove the last comma and space
            result.append("\n");
        }

        // Constructors
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        if (constructors.length > 0) {
            result.append("Constructors:\n");
            for (Constructor<?> constructor : constructors) {
                result.append("  ").append(constructor.getName()).append("\n");
            }
        }

        // Methods
        Method[] methods = clazz.getDeclaredMethods();
        if (methods.length > 0) {
            result.append("Methods:\n");
            for (Method method : methods) {
                result.append("  ").append(method.getReturnType().getSimpleName())
                        .append(" ").append(method.getName()).append("\n");
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        // Простий консольний вивід аналізу типу
        System.out.println("Console Output:");
        System.out.println(analyzeType("java.util.ArrayList"));

        TypeInfoAnalyzerGUI analyzerGUI = new TypeInfoAnalyzerGUI();
        analyzerGUI.display();
    }
}

class TypeInfoAnalyzerGUI {

    private JFrame frame;
    private JTextField inputField;
    private JTextArea resultTextArea;

    public TypeInfoAnalyzerGUI() {
        frame = new JFrame("Type Info Analyzer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        inputField = new JTextField();
        JButton analyzeButton = new JButton("Analyze");
        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JLabel("Enter type name:"), BorderLayout.WEST);
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(analyzeButton, BorderLayout.EAST);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultTextArea), BorderLayout.CENTER);

        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typeName = inputField.getText();
                String result = TypeInfoAnalyzer.analyzeType(typeName);
                resultTextArea.append(result + "\n\n");
                inputField.setText("");

                // Додано вивід на консоль
                System.out.println("Console Output:");
                System.out.println(result);
            }
        });
    }

    public void display() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
            }
        });
    }
}
