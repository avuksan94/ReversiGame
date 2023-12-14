package hr.algebra.reversi2.constants;

import hr.algebra.reversi2.GameApplication;
import javafx.scene.paint.Color;


public class GameConstants {

    public static final int BOARD_SIZE = getBoardSize();
    public static final int DISK_SIZE = getDiskSize();
    public static final int CELL_SIZE = getCellSize();
    //Player Color
    public static final Color PLAYER1_COLOR = getPlayer1Color();
    public static final Color PLAYER2_COLOR = getPlayer2Color();
    public static final Color PLAYER1_POSSIBLE_MOVE = getPlayer1PossibleMoveColor();
    public static final Color PLAYER2_POSSIBLE_MOVE = getPlayer2PossibleMoveColor();
    //Player name
    public static final String PLAYER1_NAME = getPlayer1Name();
    public static final String PLAYER2_NAME = getPlayer2Name();
    //FILE PATHS
    public static final String FILE_NAME = getFileName();
    public static final String CHAT_FILE_NAME = getChatFileName();
    public static final String GAME_MOVES_FILE_NAME = getMovesFileName();

    public static int getBoardSize() {
        return GameApplication.getGameConfig().getBoardProperties().getBoardSize();
    }
    public static int getDiskSize() {
        return GameApplication.getGameConfig().getBoardProperties().getDiskSize();
    }

    public static int getCellSize() {
        return GameApplication.getGameConfig().getBoardProperties().getCellSize();
    }

    public static Color getPlayer1Color() {
        return parseColor(GameApplication.getGameConfig().getPlayerProperties().getPlayer1Color());
    }

    public static Color getPlayer2Color() {
        return parseColor(GameApplication.getGameConfig().getPlayerProperties().getPlayer2Color());
    }

    public static Color getPlayer1PossibleMoveColor() {
        return parseColor(GameApplication.getGameConfig().getPlayerProperties().getPlayer1PossibleMove());
    }

    public static Color getPlayer2PossibleMoveColor() {
        return parseColor(GameApplication.getGameConfig().getPlayerProperties().getPlayer2PossibleMove());
    }

    public static String getPlayer1Name() {
        return GameApplication.getGameConfig().getPlayerProperties().getPlayer1Name();
    }

    public static String getPlayer2Name() {
        return GameApplication.getGameConfig().getPlayerProperties().getPlayer2Name();
    }

    public static String getFileName() {
        return GameApplication.getGameConfig().getFileProperties().getFileName();
    }

    public static String getChatFileName() {
        return GameApplication.getGameConfig().getFileProperties().getChatFileName();
    }

    public static String getMovesFileName() {
        return GameApplication.getGameConfig().getFileProperties().getMovesFileName();
    }

    private static Color parseColor(String colorStr) {
        try {
            return Color.valueOf(colorStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Color.BLACK; // Default color if error
        }
    }
}
