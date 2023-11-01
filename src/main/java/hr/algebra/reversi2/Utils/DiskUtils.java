package hr.algebra.reversi2.Utils;


import hr.algebra.reversi2.constants.GameConstants;
import hr.algebra.reversi2.enums.PlayerRole;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.util.List;

public class DiskUtils {
    public static void createStarterDisk(Pane[][] cells, int row, int col, Color color){
        Circle disk = new Circle(GameConstants.DISK_SIZE);
        disk.setFill(color);

        Pane cell = cells[row][col];
        disk.centerXProperty().bind(cell.widthProperty().divide(2));
        disk.centerYProperty().bind(cell.heightProperty().divide(2));

        cell.getChildren().add(disk);
    }

    public static boolean hasPlayerDisc(Pane cell, PlayerRole player) {
        Color desiredColor = (player == PlayerRole.Player1) ? GameConstants.PLAYER1_COLOR : GameConstants.PLAYER2_COLOR;
        for (Node child : cell.getChildren()) {
            if (child instanceof Circle && ((Circle) child).getFill().equals(desiredColor)) {
                return true;
            }
        }
        return false;
    }

    public static int countPlayerDisks(PlayerRole player, Pane[][] cells) {
        int counter = 0;

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (hasPlayerDisc(cells[i][j], player)) {
                    counter++;
                }
            }
        }

        return counter;
    }

    public static void flipDisks(List<Point> sandwichedDisks, Pane[][] cells, PlayerRole currentPlayer) {
        Color flipColor = (currentPlayer == PlayerRole.Player1) ? GameConstants.PLAYER1_COLOR : GameConstants.PLAYER2_COLOR;
        for (Point p : sandwichedDisks) {
            Pane cell = cells[p.x][p.y];
            for (Node child : cell.getChildren()) {
                if (child instanceof Circle) {
                    ((Circle) child).setFill(flipColor);
                }
            }
        }
        System.out.println("Sandwiched discs: " + sandwichedDisks);
    }

    public static Color getDiskColor(Pane cell) {
        for (Node child : cell.getChildren()) {
            if (child instanceof Circle) {
                Circle disk = (Circle) child;
                if (disk.getFill() instanceof Color) {
                    return (Color) disk.getFill();
                }
            }
        }
        return null; // no disc found
    }

}
