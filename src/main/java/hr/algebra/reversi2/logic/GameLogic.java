package hr.algebra.reversi2.logic;

import hr.algebra.reversi2.Utils.DiskUtils;
import hr.algebra.reversi2.Utils.PlayerUtils;
import hr.algebra.reversi2.constants.GameConstants;
import hr.algebra.reversi2.enums.PlayerRole;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    public GameLogic() {
    }

    //i know its bad practice to have more than 2-3 arguments in methods(SOLID...)
    public boolean handleCellClick(int row, int col, int playerTurn, Pane[][] cells){
        Pane cell = cells[row][col];

        if (hasBeenClicked(cell)) {
            return false;
        }

        PlayerRole role = PlayerUtils.getCurrentPlayerRole(playerTurn);

        List<Point> sandwichedDisks = MovesLogic.getSandwichedDisks(row, col, role, cells);
        if (sandwichedDisks.isEmpty()) {
            return false;  //No valid move
        }

        Circle disk = new Circle(GameConstants.DISK_SIZE);
        switch (role) {
            case Player1 -> disk.setFill(GameConstants.PLAYER1_COLOR);
            case Player2 -> disk.setFill(GameConstants.PLAYER2_COLOR);
        }
        disk.centerXProperty().bind(cell.widthProperty().divide(2));
        disk.centerYProperty().bind(cell.heightProperty().divide(2));
        cells[row][col].getChildren().add(disk);

        //Flip the sandwiched disks
        DiskUtils.flipDisks(sandwichedDisks, cells, role);
        //System.out.println(showValidMoves(PlayerUtils.getCurrentPlayerRole(playerTurn), cells));
        return true;
    }

    //this is the problem that players can already click panes that have been filled
    public boolean hasBeenClicked(Pane cell) {
        for (Node child : cell.getChildren()) {
            if (child instanceof Circle && (((Circle) child).getFill().equals(GameConstants.PLAYER1_COLOR) ||
                    ((Circle) child).getFill().equals(GameConstants.PLAYER2_COLOR))) {
                return true;
            }
        }
        return false;
    }

    public java.util.List<Point> findAllValidMoves(PlayerRole currentPlayer, Pane[][] cells) {
        List<Point> validMoves = new ArrayList<>();

        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                if (!hasBeenClicked(cells[row][col]) && MovesLogic.moveIsValid(row, col, currentPlayer, cells)) {
                    validMoves.add(new Point(row, col));
                }
            }
        }
        return validMoves;
    }

    public void highlightValidMove(PlayerRole currentPlayer,Pane cell) {
        Circle possiblePlacement = new Circle(GameConstants.DISK_SIZE);
        if (currentPlayer.equals(PlayerRole.Player1))
        {
            possiblePlacement.setFill(GameConstants.PLAYER1_POSSIBLE_MOVE);
            //possiblePlacement.setOpacity(0.5);
        } else {
            possiblePlacement.setFill(GameConstants.PLAYER2_POSSIBLE_MOVE);
            //possiblePlacement.setOpacity(0.5);
        }

        possiblePlacement.centerXProperty().bind(cell.widthProperty().divide(2));
        possiblePlacement.centerYProperty().bind(cell.heightProperty().divide(2));
        cell.getChildren().add(possiblePlacement);
    }

    public int showValidMoves(PlayerRole currentPlayer, Pane[][] cells) {
        int validMovesCounter = 0;
        List<Point> validMoves = findAllValidMoves(currentPlayer, cells);
        for (Point move : validMoves) {
            highlightValidMove(currentPlayer,cells[move.x][move.y]);
            validMovesCounter++;
        }
        return validMovesCounter;
    }

    public void clearHighlights(Pane[][] cells) {
        int clearedCount = 0;
        for (Pane[] cell : cells) {
            for (int col = 0; col < cell.length; col++) {
                List<Node> toRemove = cell[col].getChildren().stream()
                        .filter(child -> child instanceof Circle &&
                                (((Circle) child).getFill().equals(GameConstants.PLAYER1_POSSIBLE_MOVE) ||
                                        ((Circle) child).getFill().equals(GameConstants.PLAYER2_POSSIBLE_MOVE)))

                        .toList();

                clearedCount += toRemove.size();
                cell[col].getChildren().removeAll(toRemove);
            }
        }
    }

    public void clearSpecificHighlights(Pane[][] cells,Color color) {
        int clearedCount = 0;
        for (Pane[] cell : cells) {
            for (int col = 0; col < cell.length; col++) {
                List<Node> toRemove = cell[col].getChildren().stream()
                        .filter(child -> child instanceof Circle &&
                                (((Circle) child).getFill().equals(color)))
                        .toList();

                clearedCount += toRemove.size();
                cell[col].getChildren().removeAll(toRemove);
            }
        }
    }


    public static void clearBoard(Pane[][] cells) {
        for (Pane[] cell : cells) {
            for (Pane pane : cell) {
                pane.getChildren().removeIf(child -> child instanceof Circle);
            }
        }
    }
}
