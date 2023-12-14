package hr.algebra.reversi2.sax;

public class PlayerProperties {
    private String player1Color;
    private String player2Color;
    private String player1PossibleMove;
    private String player2PossibleMove;
    private String player1Name;
    private String player2Name;

    public PlayerProperties() {
    }

    public PlayerProperties(String player1Color, String player2Color, String player1PossibleMove,
                            String player2PossibleMove, String player1Name, String player2Name) {
        this.player1Color = player1Color;
        this.player2Color = player2Color;
        this.player1PossibleMove = player1PossibleMove;
        this.player2PossibleMove = player2PossibleMove;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public String getPlayer1Color() {
        return player1Color;
    }

    public void setPlayer1Color(String player1Color) {
        this.player1Color = player1Color;
    }

    public String getPlayer2Color() {
        return player2Color;
    }

    public void setPlayer2Color(String player2Color) {
        this.player2Color = player2Color;
    }

    public String getPlayer1PossibleMove() {
        return player1PossibleMove;
    }

    public void setPlayer1PossibleMove(String player1PossibleMove) {
        this.player1PossibleMove = player1PossibleMove;
    }

    public String getPlayer2PossibleMove() {
        return player2PossibleMove;
    }

    public void setPlayer2PossibleMove(String player2PossibleMove) {
        this.player2PossibleMove = player2PossibleMove;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }
}
