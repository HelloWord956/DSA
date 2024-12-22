module com.example.homwork1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.homwork1 to javafx.fxml;
    exports com.example.homwork1;
}