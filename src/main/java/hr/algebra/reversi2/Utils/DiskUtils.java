package hr.algebra.reversi2.Utils;


import hr.algebra.reversi2.constants.GameConstants;
import hr.algebra.reversi2.dom.GameMoveWriter;
import hr.algebra.reversi2.enums.PlayerRole;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.io.File;
import java.util.List;

public class DiskUtils {
    public static void createDisk(Pane[][] cells, int row, int col, Color color){
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
        GameMoveWriter gameMoveWriter = GameMoveWriter.getInstance();

        String projectRoot = System.getProperty("user.dir");
        String relativePath = "files";
        String xmlPath = projectRoot + File.separator + relativePath + File.separator + GameConstants.GAME_MOVES_FILE_NAME;

        gameMoveWriter.writeGameMove(xmlPath, currentPlayer, sandwichedDisks);
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

    public static Color getFirstHighlighColor(Pane[][] cells) {
        for (Pane[] row : cells) {
            for (Pane cell : row) {
                for (Node child : cell.getChildren()) {
                    if (child instanceof Circle) {
                        Circle disk = (Circle) child;
                        if (disk.getFill().equals(GameConstants.PLAYER1_POSSIBLE_MOVE) ||
                                disk.getFill().equals(GameConstants.PLAYER2_POSSIBLE_MOVE)) {
                            return (Color) disk.getFill();
                        }
                    }
                }
            }
        }
        return null;
    }
}
