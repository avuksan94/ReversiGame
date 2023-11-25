package hr.algebra.reversi2.messages;

import java.rmi.RemoteException;

public class RemoteMessageServiceImpl implements RemoteMessageService {

    private MessageState messageState;

    public RemoteMessageServiceImpl() {
        messageState = new MessageState();
    }

    @Override
    public void updateChat(MessageState messageState) throws RemoteException {
        this.messageState.getAllMessages().addAll(messageState.getAllMessages());
    }

    @Override
    public MessageState getChatState() throws RemoteException {
        return messageState;
    }
}
