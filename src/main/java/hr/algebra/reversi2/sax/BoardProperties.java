package hr.algebra.reversi2.sax;

public class BoardProperties {
    private int boardSize;
    private int diskSize;
    private int cellSize;

    public BoardProperties() {
    }

    public BoardProperties(int boardSize, int diskSize, int cellSize) {
        this.boardSize = boardSize;
        this.diskSize = diskSize;
        this.cellSize = cellSize;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(int diskSize) {
        this.diskSize = diskSize;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }
}
