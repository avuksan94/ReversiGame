package hr.algebra.reversi2.boards;

import hr.algebra.reversi2.Utils.DialogUtils;
import hr.algebra.reversi2.Utils.DiskUtils;
import hr.algebra.reversi2.Utils.PlayerUtils;
import hr.algebra.reversi2.constants.GameConstants;
import hr.algebra.reversi2.enums.PlayerRole;
import hr.algebra.reversi2.logic.GameLogic;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;



//https://www.simplilearn.com/tutorials/data-structure-tutorial/two-dimensional-arrays
//https://www.redblobgames.com/grids/parts/
//https://docs.oracle.com/javase/8/javafx/api/javafx/beans/property/IntegerProperty.html
public class GameBoardReversi {
    private boolean loadingGame;
    private int playerTurn;
    private final Pane[][] cells;
    private final GridPane board;
    private final GameLogic gameLogic;
    private final IntegerProperty player1Score = new SimpleIntegerProperty(this, "player1Score", 0);
    private final IntegerProperty player2Score = new SimpleIntegerProperty(this, "player2Score", 0);
    private int validMoves;

    public GameBoardReversi() {
        board = new GridPane();
        board.setBackground(setGameBoardBackgroundColor());
        cells = new Pane[GameConstants.BOARD_SIZE][GameConstants.BOARD_SIZE];
        playerTurn = 0;
        validMoves = 0;
        System.out.println("Im in the constructor!!!");
        gameLogic = new GameLogic();
        initBoard();
        loadingGame = false;
    }
    public void clearBoardForLoad(){
        GameLogic.clearBoard(cells);
    }

    //https://jenkov.com/tutorials/javafx/region.html#:~:text=image%20as%20background.-,Set%20Background%20Color,setBackground(background)%3B
    private Background setGameBoardBackgroundColor(){
        BackgroundFill backgroundFill =
                new BackgroundFill(
                        Color.valueOf("#008000"),
                        new CornerRadii(0),
                        new Insets(0)
                );

        return new Background(backgroundFill);
    }

    private void initBoard() {
        System.out.println("Im in intiBoard and valid moves is: " + validMoves);
        for (int row = 0; row < GameConstants.BOARD_SIZE; row++) {
            for (int col = 0; col < GameConstants.BOARD_SIZE; col++) {
                Pane cell = new Pane();
                cell.setPrefSize(GameConstants.CELL_SIZE, GameConstants.CELL_SIZE);
                cell.setStyle("-fx-border-color: white;");

                int finalRow = row;
                int finalCol = col;
                cell.setOnMouseClicked(e -> {
                    gameLogic.clearHighlights(cells);

                    //--->
                    boolean moveSuccess = gameLogic.handleCellClick(finalRow, finalCol, playerTurn, cells);
                    if (moveSuccess) {
                        System.out.println("Move made!");
                        System.out.println("Clearing cells for player: " + PlayerUtils.getCurrentPlayerRole(playerTurn));

                        gameLogic.clearHighlights(cells);
                        playerTurn++;
                        player1Score.set(getPlayerScore(PlayerRole.Player1, cells));
                        player2Score.set(getPlayerScore(PlayerRole.Player2, cells));
                    }
                    PlayerRole nextPlayerRole = PlayerUtils.getCurrentPlayerRole(playerTurn);
                    validMoves = gameLogic.showValidMoves(nextPlayerRole, cells);

                    endGame();
                });

                cells[row][col] = cell;
                board.add(cell, col, row);
            }
        }

        placeBeginningDisks();

        PlayerRole initialPlayerRole = PlayerUtils.getCurrentPlayerRole(playerTurn);
        gameLogic.showValidMoves(initialPlayerRole, cells); //valid moves for the starting player
    }

    public int getPlayerScore(PlayerRole player, Pane[][] cells){
        return DiskUtils.countPlayerDisks(player,cells);
    }

    public GridPane getBoard() {
        return board;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public IntegerProperty player1ScoreProperty() {
        return player1Score;
    }

    public Pane[][] getCells() {
        return cells;
    }

    public IntegerProperty player2ScoreProperty() {
        return player2Score;
    }

    public int getValidMoves() {
        return validMoves;
    }

    public void resetTurns(){
        playerTurn = 0;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score.set(player1Score);
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score.set(player2Score);
    }

    public void setValidMoves(int validMoves) {
        this.validMoves = validMoves;
    }

    public boolean isLoadingGame() {
        return loadingGame;
    }

    public void setLoadingGame(boolean loadingGame) {
        this.loadingGame = loadingGame;
    }

    private void endGame() {
        System.out.println("Im in endgame: " + validMoves);
        if (loadingGame) {
            return;
        }
        if (!checkEndGame()) {
            return;
        }

        int playerOneScore = player1ScoreProperty().get();
        int playerTwoScore = player2ScoreProperty().get();

        if (playerOneScore == playerTwoScore) {
            DialogUtils.displayDraw();
            resetBoard(cells);
        } else if (playerOneScore > playerTwoScore) {
            DialogUtils.displayWinner("Player one");
            resetBoard(cells);
        } else {
            DialogUtils.displayWinner("Player two");
            resetBoard(cells);
        }
    }

    public void resetBoard(Pane[][] cells){
        GameLogic.clearBoard(cells);
        resetTurns();
        placeBeginningDisks();
        PlayerRole nextPlayerRole = PlayerUtils.getCurrentPlayerRole(playerTurn);
        gameLogic.showValidMoves(nextPlayerRole, cells);

    }

    public void placeBeginningDisks(){
        int halfSize = GameConstants.BOARD_SIZE / 2;

        DiskUtils.createStarterDisk(cells, halfSize - 1, halfSize - 1, GameConstants.PLAYER2_COLOR);
        DiskUtils.createStarterDisk(cells, halfSize - 1, halfSize, GameConstants.PLAYER1_COLOR);
        DiskUtils.createStarterDisk(cells, halfSize, halfSize - 1, GameConstants.PLAYER1_COLOR);
        DiskUtils.createStarterDisk(cells, halfSize, halfSize, GameConstants.PLAYER2_COLOR);
    }

    private boolean checkEndGame() {
        //System.out.println("Checking here");
        //System.out.println("Valid moves" + getValidMoves());
        return getValidMoves() == 0;
    }
}
