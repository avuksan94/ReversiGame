package hr.algebra.reversi2.Utils;

import hr.algebra.reversi2.boards.GameBoardReversi;
import hr.algebra.reversi2.constants.GameConstants;
import hr.algebra.reversi2.state.GameState;
import hr.algebra.reversi2.state.SerializableColor;

public class GameStateUtils {
    public static GameState createGameState(GameBoardReversi reversi){
        GameState gameState = new GameState();

        gameState.setPlayerTurn(reversi.getPlayerTurn());
        gameState.setPlayer1Score(reversi.player1ScoreProperty().get());
        gameState.setPlayer2Score(reversi.player2ScoreProperty().get());
        gameState.setValidMoves(reversi.getValidMoves());

        gameState.setDiskColors(new SerializableColor[GameConstants.BOARD_SIZE][GameConstants.BOARD_SIZE]);

        for (int row = 0; row < GameConstants.BOARD_SIZE; row++) {
            for (int col = 0; col < GameConstants.BOARD_SIZE; col++) {
                gameState.getDiskColors()[row][col] = SerializableColor.fromColor(DiskUtils.getDiskColor(reversi.getCells()[row][col]));
            }
        }

        return gameState;
    }
}
