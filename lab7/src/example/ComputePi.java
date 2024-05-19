package example;

import java.math.BigDecimal;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ComputePi {
    public static void main(String[] args) {
        try {
            String host = "localhost"; // Адреса RMI сервера
            Registry registry = LocateRegistry.getRegistry(host);
            Compute comp = (Compute) registry.lookup("Compute");

            // Обчислення значення e з точністю 50 знаків після коми
            BigDecimal e = comp.executeTask(new ETask(50));
            System.out.println("Computed e: " + e);

            // Обчислення значення π (pi) з точністю 50 знаків після коми
            BigDecimal pi = comp.executeTask(new Pi(50));
            System.out.println("Computed Pi: " + pi);

        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }
}
