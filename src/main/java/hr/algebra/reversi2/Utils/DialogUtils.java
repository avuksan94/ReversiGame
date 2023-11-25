package hr.algebra.reversi2.Utils;

import javafx.scene.control.Alert;

public class DialogUtils {
    private static void showInformationDialog(String title, String header, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void displayWinner(String name) {
        showInformationDialog("There is a winner!", "The winner is " + name,
                "The winner is " + name + "!!!!!");
    }

    public static void displayDraw() {
        showInformationDialog("There is a draw!", "No one wins!",
                "Please try again!");
    }

    public static void displaySaveSuccessMessage() {
        showInformationDialog("Game saved!", "Game save succeeded!",
                "You have saved your game!");
    }

    public static void displayLoadSuccessMessage() {
        showInformationDialog("Game loaded!", "Game load succeeded!",
                "You have loaded your game!");
    }

    public static void displayPlayerSurrender() {
        showInformationDialog("Surrender initiated!", "Player has surrendered their turn!",
                "You have surrendered your turn to the other player!");
    }

}
