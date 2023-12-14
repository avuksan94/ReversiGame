package hr.algebra.reversi2.sax;

public class FileProperties {
    private String fileName;
    private String chatFileName;
    private String movesFileName;

    public FileProperties() {
    }

    public FileProperties(String fileName, String chatFileName, String movesFileName) {
        this.fileName = fileName;
        this.chatFileName = chatFileName;
        this.movesFileName = movesFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getChatFileName() {
        return chatFileName;
    }

    public void setChatFileName(String chatFileName) {
        this.chatFileName = chatFileName;
    }

    public String getMovesFileName() {
        return movesFileName;
    }

    public void setMovesFileName(String movesFileName) {
        this.movesFileName = movesFileName;
    }
}
