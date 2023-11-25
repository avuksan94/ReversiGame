package hr.algebra.reversi2.Utils;

import hr.algebra.reversi2.GameApplication;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

import java.rmi.RemoteException;
import java.util.List;

public class MessageUtils {

    public static void startChatMessagesRefreshThread(TextArea chatTextArea) {
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1000),
                        event -> {
                            if (GameApplication.remoteChatService != null) {
                                try {
                                    List<String> chatMessages = GameApplication.remoteChatService.getChatState().getAllMessages();
                                    Platform.runLater(() -> {
                                        chatTextArea.clear();
                                        for (String message : chatMessages) {
                                            chatTextArea.appendText(message + "\n");
                                        }
                                    });
                                } catch (RemoteException e) {
                                    e.printStackTrace(); // Log or handle the RemoteException
                                }
                            }
                        }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
