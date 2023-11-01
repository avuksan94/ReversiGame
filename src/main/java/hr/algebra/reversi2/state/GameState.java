package hr.algebra.reversi2.state;

import hr.algebra.reversi2.Utils.DiskUtils;
import hr.algebra.reversi2.boards.GameBoardReversi;
import hr.algebra.reversi2.constants.GameConstants;

import java.io.Serializable;

public class GameState implements Serializable {

    private int playerTurn;
    private SerializableColor[][] diskColors;  // SerializableColor instead of Color, because color is not Serializable
    private int player1Score;
    private int player2Score;
    private int validMoves;

    public GameState(GameBoardReversi board) {
        this.playerTurn = board.getPlayerTurn();
        this.player1Score = board.player1ScoreProperty().get();
        this.player2Score = board.player2ScoreProperty().get();
        this.validMoves = board.getValidMoves();

        diskColors = new SerializableColor[GameConstants.BOARD_SIZE][GameConstants.BOARD_SIZE];

        for (int row = 0; row < GameConstants.BOARD_SIZE; row++) {
            for (int col = 0; col < GameConstants.BOARD_SIZE; col++) {
                diskColors[row][col] = SerializableColor.fromColor(DiskUtils.getDiskColor(board.getCells()[row][col]));
            }
        }
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public SerializableColor[][] getDiskColors() {
        return diskColors;
    }

    public void setDiskColors(SerializableColor[][] diskColors) {
        this.diskColors = diskColors;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }

    public int getValidMoves() {
        return validMoves;
    }

    public void setValidMoves(int validMoves) {
        this.validMoves = validMoves;
    }

}
