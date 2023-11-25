package hr.algebra.reversi2.Utils;

import hr.algebra.reversi2.constants.ConfigurationConstants;
import hr.algebra.reversi2.state.GameState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class NetworkingUtils {
    private static Socket socket;
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;

    //overload for the client
    public static void initializeConnection(boolean isServer) {
        if (isServer) {
            throw new IllegalStateException("Server must call initializeConnection with a client Socket");
        }

        try {
            // Creating a socket address from the client port
            InetAddress localAddress = InetAddress.getByName(ConfigurationConstants.HOST);
            InetSocketAddress socketAddress = new InetSocketAddress(localAddress, ConfigurationConstants.CLIENT_PORT);

            socket = new Socket();
            socket.bind(socketAddress);
            socket.connect(new InetSocketAddress(ConfigurationConstants.HOST, ConfigurationConstants.SERVER_PORT));

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initializeConnection(boolean isServer, Socket clientSocket) {
        try {
            if (isServer) {
                socket = clientSocket;
            } else {
                socket = new Socket(ConfigurationConstants.HOST, ConfigurationConstants.SERVER_PORT);
            }
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendGameState(GameState gameState) {
        if (outputStream != null) {
            try {
                outputStream.writeObject(gameState);
                //https://medium.com/@gabriellamedas/remember-to-flush-your-streams-a0ce23043947
                outputStream.flush();
                System.err.println("GameState sent.");
            } catch (IOException e) {
                e.printStackTrace();
                closeConnection();
            }
        } else {
            System.err.println("Cannot send GameState: outputStream is null. Is the connection initialized?");
        }
    }

    public static GameState receiveGameState() {
        if (inputStream != null) {
            try {
                return (GameState) inputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                closeConnection();
                return null;
            }
        } else {
            System.err.println("Cannot receive GameState: inputStream is null. Is the connection initialized?");
            return null;
        }
    }

    private static void closeConnection() {
        try {
            if (outputStream != null) outputStream.close();
            if (inputStream != null) inputStream.close();
            if (socket != null) socket.close();
            System.out.printf("Closing connection!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
