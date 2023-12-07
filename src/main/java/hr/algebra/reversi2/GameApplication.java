package hr.algebra.reversi2;

import hr.algebra.reversi2.controller.GameController;
import hr.algebra.reversi2.enums.ConfigurationKey;
import hr.algebra.reversi2.enums.PlayerRole;
import hr.algebra.reversi2.jndi.ConfigurationReader;
import hr.algebra.reversi2.messages.RemoteMessageService;
import hr.algebra.reversi2.messages.RemoteMessageServiceImpl;
import hr.algebra.reversi2.multiplayer.GameClient;
import hr.algebra.reversi2.multiplayer.GameServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class GameApplication extends Application {
    public static PlayerRole player;
    public static RemoteMessageService remoteChatService;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("gameMain-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
        stage.setTitle("Reversi Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("Player: " + args[0]);
        player = PlayerRole.valueOf(args[0]);

        if (PlayerRole.Player1.name().equals(player.name())) {
            System.out.println("Starting as Player 1 (Server)");
            new Thread(() -> {
                startRMIServer();
                GameServer.startServer();
            }).start();
        } else if (PlayerRole.Player2.name().equals(player.name())) {
            System.out.println("Starting as Player 2 (Client)");
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    GameController.managePanes(false);
                    GameClient.startClient();
                    remoteChatService = startRMIClient();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException | NotBoundException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        launch();
    }

    //https://docs.oracle.com/javase/8/docs/api/java/rmi/registry/Registry.html
    public static void startRMIServer() {
        try {
            remoteChatService = new RemoteMessageServiceImpl();
            RemoteMessageService stub = (RemoteMessageService) UnicastRemoteObject.exportObject(remoteChatService, 0);
            Registry registry = LocateRegistry.createRegistry(ConfigurationReader.getInstance().readIntegerValueForKey(ConfigurationKey.RMI_PORT));
            registry.rebind(RemoteMessageService.REMOTE_OBJECT_NAME, stub);
            System.err.println("RMI Server is up and ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public static RemoteMessageService startRMIClient() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(ConfigurationReader.getInstance().readStringValueForKey(ConfigurationKey.HOST), ConfigurationReader.getInstance().readIntegerValueForKey(ConfigurationKey.RMI_PORT));
        return (RemoteMessageService) registry.lookup(RemoteMessageService.REMOTE_OBJECT_NAME);
    }
}