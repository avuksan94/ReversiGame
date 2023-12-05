package hr.algebra.reversi2.constants;

import javafx.scene.paint.Color;


public class GameConstants {
    public static final int BOARD_SIZE = 8;
    public static final int DISK_SIZE = 20;
    public static final int CELL_SIZE = 50;
    //Player Color
    public static final Color PLAYER1_COLOR = Color.BLACK;
    public static final Color PLAYER2_COLOR = Color.WHITE;
    public static final Color PLAYER1_POSSIBLE_MOVE = Color.YELLOW;
    public static final Color PLAYER2_POSSIBLE_MOVE = Color.LIGHTBLUE;
    //Player name
    public static final String PLAYER1_NAME = "Player1";
    public static final String PLAYER2_NAME = "Player2";

    //FILE PATHS
    public static final String FILE_NAME = "savedGameReversi.dat";
    public static final String CHAT_FILE_NAME = "savedChatReversi.dat";
}
