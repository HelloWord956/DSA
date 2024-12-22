package com.example.homwork1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField pointField;
    @FXML private TextField dateOfBirthField;
    @FXML private TableView<Student> tableView;
    @FXML private TableColumn<Student, String> firstNameCol;
    @FXML private TableColumn<Student, String> lastNameCol;
    @FXML private TableColumn<Student, Double> pointCol;
    @FXML private TableColumn<Student, LocalDate> dobCol;

    private List<Student> studentList = new ArrayList<>();
    private SortStrategy sortStrategy;

    @FXML
    public void initialize() {
        ObservableList<Student> observableStudentList = FXCollections.observableArrayList(studentList);

        firstNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        lastNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        pointCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPoint()).asObject());
        dobCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDateOfBirth()));

        tableView.setItems(observableStudentList);
    }

    @FXML
    private void saveStudent() {
        try {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            double point = Double.parseDouble(pointField.getText());
            LocalDate dob = LocalDate.parse(dateOfBirthField.getText(), DateTimeFormatter.ISO_DATE);

            Student student = new Student(firstName, lastName, point, dob);
            studentList.add(student);

            tableView.getItems().add(student);

            firstNameField.clear();
            lastNameField.clear();
            pointField.clear();
            dateOfBirthField.clear();
        } catch (Exception e) {
            showError("Invalid input. Please check your data.");
        }
    }

    @FXML
    private void sortStudents() {
        if (sortStrategy != null) {
            sortStrategy.sort(studentList);
            tableView.getItems().clear();
            tableView.getItems().addAll(studentList);
        } else {
            showError("No sort strategy selected.");
        }
    }


    @FXML
    private void bubbleSort() {
        sortStrategy = new BubbleSort();
        sortStudents();
    }

    @FXML
    private void insertionSort() {
        sortStrategy = new InsertionSort();
        sortStudents();
    }

    @FXML
    private void selectionSort() {
        sortStrategy = new SelectionSort();
        sortStudents();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
