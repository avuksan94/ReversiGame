package hr.algebra.reversi2.multiplayer;

import hr.algebra.reversi2.Utils.NetworkingUtils;
import hr.algebra.reversi2.constants.ConfigurationConstants;
import hr.algebra.reversi2.controller.GameController;
import hr.algebra.reversi2.state.GameState;
import javafx.application.Platform;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    public static void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(ConfigurationConstants.SERVER_PORT)) {
            System.err.println("Server -> listening on port: " + serverSocket.getLocalPort());
            while (true) { // Keep listening for new clients
                Socket clientSocket = serverSocket.accept();
                System.err.println("Client -> connected from port: " + clientSocket.getPort());
                new Thread(() -> processClient(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processClient(Socket clientSocket) {
        // Initializing networking utils with the client socket
        NetworkingUtils.initializeConnection(true, clientSocket);

        while (true) {
            GameState gameState = NetworkingUtils.receiveGameState();
            if (gameState != null) {
                System.err.println("Received a new game state from the client: " + gameState);
                System.err.println("Received a new game state from the client: " + clientSocket.getPort());
                Platform.runLater(() -> GameController.refreshGameBoard(gameState));
            } else {
                break;
            }
        }
    }
}
