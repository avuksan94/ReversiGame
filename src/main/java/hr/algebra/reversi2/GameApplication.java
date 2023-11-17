package hr.algebra.reversi2;

import hr.algebra.reversi2.enums.PlayerRole;
import hr.algebra.reversi2.multiplayer.GameClient;
import hr.algebra.reversi2.multiplayer.GameServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {
    public static PlayerRole player;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("gameMain-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
        stage.setTitle("Reversi Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //if it stops working or i get bugs im gonna comment this out
        System.out.println("Player: " + args[0]);
        player = PlayerRole.valueOf(args[0]);

        if (PlayerRole.Player1.name().equals(player.name())) {
            System.out.println("Starting as Player 1 (Server)");
            new Thread(GameServer::startServer).start();
        } else if (PlayerRole.Player2.name().equals(player.name())) {
            System.out.println("Starting as Player 2 (Client)");
            new Thread(() -> {
                try {
                    Thread.sleep(1000); // Small delay to ensure server is up
                    GameClient.startClient();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        launch();
    }
}