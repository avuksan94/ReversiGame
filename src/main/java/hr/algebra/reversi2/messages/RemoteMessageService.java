package hr.algebra.reversi2.messages;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteMessageService extends Remote {
    String REMOTE_OBJECT_NAME = "hr.algebra.reversi2.rmi.service";
    void updateChat(MessageState messageState) throws RemoteException;
    MessageState getChatState() throws RemoteException;
}
