package hr.algebra.reversi2.messages;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessageState implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> allMessages;

    public MessageState() {
        allMessages = new ArrayList<>();
    }

    public void addMessage(String message) {
        allMessages.add(message);
    }

    public List<String> getAllMessages() {
        return allMessages;
    }

    public void setAllMessages(List<String> allMessages) {
        this.allMessages = allMessages;
    }

    @Override
    public String toString() {
        return "MessageState{" +
                "allMessages=" + allMessages +
                '}';
    }
}
