package hr.algebra.reversi2.boards;
public final class GameBoardFactory {
    private static GameBoardReversi _gameBoardReversi;
    public GameBoardFactory() {
    }

    //lazy singleton
    public static GameBoardReversi getReversi() throws Exception {
        if (_gameBoardReversi == null) {
            _gameBoardReversi = new GameBoardReversi();
        }
        return _gameBoardReversi;
    }
}
