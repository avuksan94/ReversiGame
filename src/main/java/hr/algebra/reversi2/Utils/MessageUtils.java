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
    private static final Object chatRefreshLock = new Object();
    private static boolean isChatLoading = false;

    public static void startChatMessagesRefreshThread(TextArea chatTextArea) {
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(1000),
                        event -> {
                            synchronized (chatRefreshLock) {
                                if (GameApplication.remoteChatService != null && !isChatLoading) {
                                    try {
                                        List<String> chatMessages = GameApplication.remoteChatService.getChatState().getAllMessages();
                                        Platform.runLater(() -> {
                                            chatTextArea.clear();
                                            for (String message : chatMessages) {
                                                chatTextArea.appendText(message + "\n");
                                            }
                                        });
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public static void setChatLoading(boolean isLoading) {
        synchronized (chatRefreshLock) {
            isChatLoading = isLoading;
        }
    }

    /*
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
     */
}
