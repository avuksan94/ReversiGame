package hr.algebra.reversi2.Utils;


import hr.algebra.reversi2.enums.PlayerRole;

public class PlayerUtils {
    public static PlayerRole getCurrentPlayerRole(int playerTurn) {
        return (playerTurn % 2 == 0) ? PlayerRole.Player1 : PlayerRole.Player2;
    }
}
