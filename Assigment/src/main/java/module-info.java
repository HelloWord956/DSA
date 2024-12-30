module com.example.assigment {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.assigment.controller to javafx.fxml;
    exports com.example.assigment.application;
}
