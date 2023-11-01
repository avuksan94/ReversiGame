module hr.algebra.reversi2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens hr.algebra.reversi2 to javafx.fxml;
    exports hr.algebra.reversi2;
    exports hr.algebra.reversi2.controller;
    opens hr.algebra.reversi2.controller to javafx.fxml;
}