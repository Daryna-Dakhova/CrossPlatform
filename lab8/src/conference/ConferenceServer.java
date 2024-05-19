package conference;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ConferenceServer {
    public static void main(String[] args) {
        try {
            ConferenceServiceImpl service = new ConferenceServiceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ConferenceService", service);
            System.out.println("Conference Server is ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
