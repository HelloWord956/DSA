module com.example.assigment {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.assigment.controller to javafx.fxml;
    opens com.example.assigment.application to javafx.fxml;
    opens com.example.assigment.entity to javafx.fxml;
    opens com.example.assigment.view to javafx.fxml; // Dòng này để mở gói view cho FXML

    exports com.example.assigment;
    exports com.example.assigment.application;
    exports com.example.assigment.controller;
    exports com.example.assigment.entity;
    exports com.example.assigment.view; // Xuất gói view nếu bạn cần
}
