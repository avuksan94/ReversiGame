package hr.algebra.reversi2.Utils;

import hr.algebra.reversi2.boards.GameBoardReversi;
import hr.algebra.reversi2.constants.GameConstants;
import hr.algebra.reversi2.messages.MessageState;
import hr.algebra.reversi2.state.GameState;
import javafx.scene.control.TextArea;

import java.io.*;

public class FileUtils {
    public static void saveGame(GameBoardReversi board) {
        GameState gameStateToSave = new GameState(board);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GameConstants.FILE_NAME))) {
            oos.writeObject(gameStateToSave);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameState loadGame() {
        GameState recoveredGameState;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GameConstants.FILE_NAME))) {
            recoveredGameState = (GameState) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return recoveredGameState;
    }

    public static void saveChat(TextArea textArea) {
        MessageState chatStateToSave = new MessageState(TextUtils.createListFromString(textArea));

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GameConstants.CHAT_FILE_NAME))) {
            oos.writeObject(chatStateToSave);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MessageState loadChat() {
        MessageState recoveredChatState;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GameConstants.CHAT_FILE_NAME))) {
            recoveredChatState = (MessageState) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return recoveredChatState;
    }
}
