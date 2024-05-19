package conference;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ConferenceServiceImpl extends UnicastRemoteObject implements ConferenceService {
    private List<Participant> participants;

    protected ConferenceServiceImpl() throws RemoteException {
        participants = new ArrayList<>();
    }

    @Override
    public synchronized int registerParticipant(Participant participant) throws RemoteException {
        participants.add(participant);
        return participants.size();
    }

    @Override
    public synchronized List<Participant> getParticipants() throws RemoteException {
        return new ArrayList<>(participants);
    }
}
