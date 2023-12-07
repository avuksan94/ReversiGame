package hr.algebra.reversi2.controller;

import hr.algebra.reversi2.GameApplication;
import hr.algebra.reversi2.Utils.*;
import hr.algebra.reversi2.boards.GameBoardFactory;
import hr.algebra.reversi2.boards.GameBoardReversi;
import hr.algebra.reversi2.constants.GameConstants;
import hr.algebra.reversi2.documentation.HtmlDocumentationGenerator;
import hr.algebra.reversi2.documentation.TxtDocumentationGenerator;
import hr.algebra.reversi2.enums.PlayerRole;
import hr.algebra.reversi2.messages.MessageState;
import hr.algebra.reversi2.state.GameState;
import hr.algebra.reversi2.state.SerializableColor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.rmi.RemoteException;

public class GameController {
    private static GameBoardReversi _reversiGameBoard;
    @FXML
    private BorderPane bpMainGame;
    @FXML
    private Label lbDisplayP1Score;
    @FXML
    private Label lbDisplayP2Score;
    @FXML
    private Button btnSendMessage;
    @FXML
    private TextArea taSendMessage;
    @FXML
    private TextArea taAllMessages;
    @FXML
    public void initialize() throws Exception {
        initGameBoard();
        updateScoreLabels();
        MessageUtils.startChatMessagesRefreshThread(taAllMessages);
        setupSendMessageButton();
    }

    private void initGameBoard() throws Exception {
        _reversiGameBoard = GameBoardFactory.getReversi();
        _reversiGameBoard.getBoard().setPrefSize(GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE,GameConstants.BOARD_SIZE * GameConstants.CELL_SIZE);

        _reversiGameBoard.getBoard().setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        _reversiGameBoard.getBoard().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        bpMainGame.setCenter(_reversiGameBoard.getBoard());
    }

    private void updateScoreLabels() {
        lbDisplayP1Score.textProperty().bind(_reversiGameBoard.player1ScoreProperty().asString("%d"));
        lbDisplayP2Score.textProperty().bind(_reversiGameBoard.player2ScoreProperty().asString("%d"));
    }

    public void setupSendMessageButton() {
        btnSendMessage.setOnAction(event -> {
            try {
                StringBuilder sbMessage = new StringBuilder();
                String playerSendingMsg = GameApplication.player.name();
                String message = taSendMessage.getText();

                sbMessage.append(playerSendingMsg);
                sbMessage.append(": ");
                sbMessage.append(message);

                if (!message.isEmpty()) {
                    MessageState messageState = new MessageState();
                    messageState.addMessage(sbMessage.toString());
                    GameApplication.remoteChatService.updateChat(messageState);
                    taSendMessage.clear(); // Clear the text area after sending the mssage
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void startGame(){
        _reversiGameBoard.resetBoard(_reversiGameBoard.getCells());
        _reversiGameBoard.placeBeginningDisks();
        _reversiGameBoard.setPlayer1Score(0);
        _reversiGameBoard.setPlayer2Score(0);
        GameState gameStateToSend = GameStateUtils.createGameState(_reversiGameBoard);
        NetworkingUtils.sendGameState(gameStateToSend);
    }

    public void saveGame() {
        FileUtils.saveGame(_reversiGameBoard);
        FileUtils.saveChat(taAllMessages);
        DialogUtils.displaySaveSuccessMessage();
    }

    public void loadGame() {
        MessageUtils.setChatLoading(true);

        loading();
        chatLoading();

        MessageUtils.setChatLoading(false);
        GameState loadedGameState = GameStateUtils.createGameState(_reversiGameBoard);
        NetworkingUtils.sendGameState(loadedGameState);
        DialogUtils.displayLoadSuccessMessage();
    }

    private void loading() {
        _reversiGameBoard.setLoadingGame(true);

        _reversiGameBoard.clearBoardForLoad();
        GameState recoveredGameState = FileUtils.loadGame();

        int playerTurn = recoveredGameState.getPlayerTurn();
        _reversiGameBoard.setPlayerTurn(playerTurn);

        int player1Score = recoveredGameState.getPlayer1Score();
        int player2Score = recoveredGameState.getPlayer2Score();

        _reversiGameBoard.setPlayer1Score(player1Score);
        _reversiGameBoard.setPlayer2Score(player2Score);

        int validMoves = recoveredGameState.getValidMoves();
        _reversiGameBoard.setValidMoves(validMoves);

        SerializableColor[][] serializedDiskColors = recoveredGameState.getDiskColors();
        Color[][] diskColors = new Color[GameConstants.BOARD_SIZE][GameConstants.BOARD_SIZE];

        for(int i = 0; i < GameConstants.BOARD_SIZE; i++) {
            for(int j = 0; j < GameConstants.BOARD_SIZE; j++) {
                if (serializedDiskColors[i][j] != null) {
                    diskColors[i][j] = serializedDiskColors[i][j].toColor();
                    DiskUtils.createDisk(_reversiGameBoard.getCells(), i, j, diskColors[i][j]);
                } //problem with load when null
            }
        }
        _reversiGameBoard.setLoadingGame(false);

        if (GameApplication.player.name().equals(GameConstants.PLAYER1_NAME)){
            managePanes(DiskUtils.getFirstHighlighColor(_reversiGameBoard.getCells()) == GameConstants.PLAYER1_POSSIBLE_MOVE);
        }
        else if (GameApplication.player.name().equals(GameConstants.PLAYER2_NAME)){
            managePanes(DiskUtils.getFirstHighlighColor(_reversiGameBoard.getCells()) == GameConstants.PLAYER2_POSSIBLE_MOVE);
        }
    }

    private void chatLoading(){
        MessageState recoveredMessageState = FileUtils.loadChat();
        taAllMessages.clear();
        taAllMessages.setText(TextUtils.getStringFromList(recoveredMessageState.getAllMessages()));

        if (GameApplication.remoteChatService != null) {
            try {
                GameApplication.remoteChatService.updateChat(recoveredMessageState);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveAsHtml(){
        Stage stage = (Stage) bpMainGame.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save HTML Documentation");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("HTML Files", "*.html")
        );

        File selectedFile = fileChooser.showSaveDialog(stage);

        if (selectedFile != null) {
            HtmlDocumentationGenerator htmlGen = new HtmlDocumentationGenerator();
            String htmlContent = htmlGen.generateHtmlContent();
            htmlGen.writeHtmlToFile(htmlContent, selectedFile.toPath());
        }
    }

    public void saveAsTxt(){
        Stage stage = (Stage) bpMainGame.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Txt Documentation");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("plain txt", "*.txt")
        );

        File selectedFile = fileChooser.showSaveDialog(stage);
        TxtDocumentationGenerator textGen =  new TxtDocumentationGenerator();

        if (selectedFile != null) {
            textGen.generateTxtDocumentation(selectedFile.toPath());
        }
    }

    public static void refreshGameBoard(GameState gameState) {
        restoreGameState(gameState);
        _reversiGameBoard.enablePanes(true);
    }

    public static void managePanes(boolean enable) {
        _reversiGameBoard.enablePanes(enable);
    }

    private static void restoreGameState(GameState gameState) {
        _reversiGameBoard.clearBoardForLoad();
        _reversiGameBoard.setPlayerTurn(gameState.getPlayerTurn());
        _reversiGameBoard.setPlayer1Score(gameState.getPlayer1Score());
        _reversiGameBoard.setPlayer2Score(gameState.getPlayer2Score());
        _reversiGameBoard.setValidMoves(gameState.getValidMoves());

        SerializableColor[][] serializedDiskColors = gameState.getDiskColors();
        Color[][] diskColors = new Color[GameConstants.BOARD_SIZE][GameConstants.BOARD_SIZE];

        for(int i = 0; i < GameConstants.BOARD_SIZE; i++) {
            for(int j = 0; j < GameConstants.BOARD_SIZE; j++) {
                if (serializedDiskColors[i][j] != null) {
                    diskColors[i][j] = serializedDiskColors[i][j].toColor();
                    DiskUtils.createDisk(_reversiGameBoard.getCells(), i, j, diskColors[i][j]);
                }
            }
        }

        if (_reversiGameBoard.getValidMoves() == 0){
            PlayerRole winner = PlayerUtils.getCurrentPlayerRole(_reversiGameBoard.getPlayerTurn());
            DialogUtils.displayWinner(winner.toString());
            clearAfterVictory(_reversiGameBoard.getCells());
        }
    }

    private static void clearAfterVictory(Pane[][] cells){
        _reversiGameBoard.resetBoard(cells);
    }
}