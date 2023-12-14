package hr.algebra.reversi2.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//https://medium.com/swlh/java-singleton-pattern-and-synchronization-32665cbf6ad7  ->
/*
What will happen if two threads call getInstance() method at the same time?
In that case the second thread is going to create a new instance of Singleton class and
override the existing instance which has been created by the first thread.
In order to improve that we are going to implement a synchronization block.
*/

public class ConfigHandler extends DefaultHandler {
    private static ConfigHandler instance;

    private ConfigHandler() {}

    public static synchronized ConfigHandler getInstance() {
        if (instance == null) {
            instance = new ConfigHandler();
        }
        return instance;
    }

    private StringBuilder elementValue;
    private GameConfig config;
    private String currentSection;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        elementValue = new StringBuilder();
        if (qName.equalsIgnoreCase("GameConfig")) {
            config = new GameConfig();
        } else if (qName.equalsIgnoreCase("BoardProperties") ||
                qName.equalsIgnoreCase("PlayerProperties") ||
                qName.equalsIgnoreCase("FileProperties")) {
            currentSection = qName;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        elementValue.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (currentSection) {
            case "BoardProperties" -> handleBoardProperties(qName);
            case "PlayerProperties" -> handlePlayerProperties(qName);
            case "FileProperties" -> handleFileProperties(qName);
        }
    }

    private void handleBoardProperties(String qName) {
        if (config.getBoardProperties() == null) {
            config.setBoardProperties(new BoardProperties());
        }

        switch (qName) {
            case "BOARD_SIZE" -> config.getBoardProperties().setBoardSize(Integer.parseInt(elementValue.toString()));
            case "DISK_SIZE" -> config.getBoardProperties().setDiskSize(Integer.parseInt(elementValue.toString()));
            case "CELL_SIZE" -> config.getBoardProperties().setCellSize(Integer.parseInt(elementValue.toString()));
        }
    }

    private void handlePlayerProperties(String qName) {
        if (config.getPlayerProperties() == null) {
            config.setPlayerProperties(new PlayerProperties());
        }

        switch (qName) {
            case "PLAYER1_COLOR" -> config.getPlayerProperties().setPlayer1Color(elementValue.toString());
            case "PLAYER2_COLOR" -> config.getPlayerProperties().setPlayer2Color(elementValue.toString());
            case "PLAYER1_POSSIBLE_MOVE" -> config.getPlayerProperties().setPlayer1PossibleMove(elementValue.toString());
            case "PLAYER2_POSSIBLE_MOVE" -> config.getPlayerProperties().setPlayer2PossibleMove(elementValue.toString());
            case "PLAYER1_NAME" -> config.getPlayerProperties().setPlayer1Name(elementValue.toString());
            case "PLAYER2_NAME" -> config.getPlayerProperties().setPlayer2Name(elementValue.toString());
        }
    }

    private void handleFileProperties(String qName) {
        if (config.getFileProperties() == null) {
            config.setFileProperties(new FileProperties());
        }

        switch (qName) {
            case "FILE_NAME" -> config.getFileProperties().setFileName(elementValue.toString());
            case "CHAT_FILE_NAME" -> config.getFileProperties().setChatFileName(elementValue.toString());
            case "GAME_MOVES_FILE_NAME" -> config.getFileProperties().setMovesFileName(elementValue.toString());
        }
    }

    public GameConfig getConfig() {
        return config;
    }
}
