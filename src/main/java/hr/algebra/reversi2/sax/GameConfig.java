package hr.algebra.reversi2.sax;

public class GameConfig {
    private BoardProperties boardProperties;
    private PlayerProperties playerProperties;
    private FileProperties fileProperties;

    public GameConfig() {
    }

    public BoardProperties getBoardProperties() {
        return boardProperties;
    }

    public void setBoardProperties(BoardProperties boardProperties) {
        this.boardProperties = boardProperties;
    }

    public PlayerProperties getPlayerProperties() {
        return playerProperties;
    }

    public void setPlayerProperties(PlayerProperties playerProperties) {
        this.playerProperties = playerProperties;
    }

    public FileProperties getFileProperties() {
        return fileProperties;
    }

    public void setFileProperties(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }
}
