module com.yago.banco {
    requires javafx.controls;
    requires javafx.fxml;

    opens main to javafx.fxml;
    opens controllers to javafx.fxml;

    exports main;
    exports controllers;
}