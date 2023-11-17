package hr.algebra.reversi2.multiplayer;

import hr.algebra.reversi2.Utils.NetworkingUtils;
import hr.algebra.reversi2.controller.GameController;
import hr.algebra.reversi2.state.GameState;
import javafx.application.Platform;

import java.io.IOException;

public class GameClient {
    public static void startClient() throws IOException {
        NetworkingUtils.initializeConnection(false);
        // Starting a new thread for listening to server updates
        new Thread(() -> {
            while (true) {
                GameState gameState = NetworkingUtils.receiveGameState();
                if (gameState != null) {
                    System.out.println("GameState received from server.");
                    Platform.runLater(() -> GameController.refreshGameBoard(gameState));
                } else {
                    break;
                }
            }
        }).start();
    }
}
