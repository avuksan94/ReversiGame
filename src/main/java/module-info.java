module hr.algebra.reversi2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.rmi;
    requires java.naming;


    opens hr.algebra.reversi2 to javafx.fxml;
    exports hr.algebra.reversi2.messages to java.rmi;
    exports hr.algebra.reversi2;
    exports hr.algebra.reversi2.controller;
    opens hr.algebra.reversi2.controller to javafx.fxml;
}