package hr.algebra.reversi2.logic;

import hr.algebra.reversi2.Utils.DiskUtils;
import hr.algebra.reversi2.enums.PlayerRole;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MovesLogic {

     /*
        -1 -1   Top-Left
        -1  0   Top-Center
        -1  1   Top-Right
        0 -1   Center-Left
        0  1   Center-Right
        1 -1   Bottom-Left
        1  0   Bottom-Center
        1  1   Bottom-Right
     */

    private final static int[] x = { -1, -1, -1, 0, 0, 1, 1, 1 };
    private final static int[] y = { -1, 0, 1, -1, 1, -1, 0, 1 };

    private static List<Point> travelGrid(int row, int col, int directionX, int directionY, PlayerRole currentPlayer, Pane[][] cells) {
        List<Point> points = new ArrayList<>();
        PlayerRole opponent = (currentPlayer == PlayerRole.Player1) ? PlayerRole.Player2 : PlayerRole.Player1;
        int r = row + directionX;
        int c = col + directionY;

        while (r >= 0 && r < cells.length && c >= 0 && c < cells[0].length) {
            Pane cell = cells[r][c];
            if (DiskUtils.hasPlayerDisc(cell, opponent)) {
                points.add(new Point(r, c));
            } else if (DiskUtils.hasPlayerDisc(cell, currentPlayer)) {
                return points;
            } else {
                break;
            }

            r += directionX;
            c += directionY;
        }

        return new ArrayList<>();
    }

    public static boolean moveIsValid(int row, int col, PlayerRole currentPlayer, Pane[][] cells) {
        for (int i = 0; i < 8; i++) {
            List<Point> points = travelGrid(row, col, x[i], y[i], currentPlayer, cells);
            if (!points.isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public static List<Point> getSandwichedDisks(int row, int col, PlayerRole currentPlayer, Pane[][] cells) {
        List<Point> sandwiched = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            sandwiched.addAll(travelGrid(row, col, x[i], y[i], currentPlayer, cells));
        }

        return sandwiched;
    }
}
