package conference;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ConferenceService extends Remote {
    int registerParticipant(Participant participant) throws RemoteException;
    List<Participant> getParticipants() throws RemoteException;
}
