package com.example.assigment.controller;

import com.example.assigment.entity.ExpenseRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class HelloController {

    @FXML
    private Button collectedButton;
    @FXML
    private Button spentButton;
    @FXML
    private TextField amountMoneyInput;
    @FXML
    private TextField reasonInput;
    @FXML
    private DatePicker dateTimeInput;
    @FXML
    private Button saveButton;

    @FXML
    private TableColumn<ExpenseRecord, Double> amountTableColumn;
    @FXML
    private TableColumn<ExpenseRecord, String> reasonTableColumn;
    @FXML
    private TableColumn<ExpenseRecord, LocalDate> dateTimeTableColumn;

    @FXML
    private TextField searchReasonInput;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TableView<ExpenseRecord> tableView;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    private ObservableList<ExpenseRecord> expensesList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Sort by Date", "Sort by Amount"
        );
        comboBox.setItems(options);
        comboBox.setValue("Sort by Date");
        comboBox.setOnAction(event -> onSortChange());

        amountTableColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        reasonTableColumn.setCellValueFactory(cellData -> cellData.getValue().reasonProperty());
        dateTimeTableColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        tableView.setItems(expensesList);

        barChart.setTitle("Monthly Expense Overview");
        xAxis.setLabel("Month");
        yAxis.setLabel("Amount");
    }

    public void onCollectedButtonClick() {
        clearInputs();
        amountMoneyInput.setPromptText("Enter amount of money collected");
        reasonInput.setPromptText("Enter reason for money collected");
    }

    public void onSpentButtonClick() {
        clearInputs();
        amountMoneyInput.setPromptText("Enter amount of money spent");
        reasonInput.setPromptText("Enter reason for money spent");
    }

    public void onSaveButtonClick() {
        String amountText = amountMoneyInput.getText();
        String reasonText = reasonInput.getText();
        LocalDate date = dateTimeInput.getValue();

        if (amountText.isEmpty() || reasonText.isEmpty() || date == null) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        double amount = Double.parseDouble(amountText);
        ExpenseRecord newRecord = new ExpenseRecord(amount, reasonText, date);
        expensesList.add(newRecord);

        updateBarChart();
        clearInputs();
    }

    private void updateBarChart() {
        barChart.getData().clear();

        Map<String, Double> collectedMoneyByMonth = new HashMap<>();
        Map<String, Double> spentMoneyByMonth = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM"); // Tháng định dạng "YYYY-MM"

        for (ExpenseRecord record : expensesList) {
            String month = record.getDate().format(formatter);
            if (record.getAmount() > 0) {
                collectedMoneyByMonth.put(month, collectedMoneyByMonth.getOrDefault(month, 0.0) + record.getAmount());
            } else {
                spentMoneyByMonth.put(month, spentMoneyByMonth.getOrDefault(month, 0.0) + record.getAmount());
            }
        }

        XYChart.Series<String, Number> collectedSeries = new XYChart.Series<>();
        collectedSeries.setName("Money Collected");
        XYChart.Series<String, Number> spentSeries = new XYChart.Series<>();
        spentSeries.setName("Money Spent");

        for (String month : collectedMoneyByMonth.keySet()) {
            collectedSeries.getData().add(new XYChart.Data<>(month, collectedMoneyByMonth.get(month)));
        }

        for (String month : spentMoneyByMonth.keySet()) {
            spentSeries.getData().add(new XYChart.Data<>(month, spentMoneyByMonth.get(month)));
        }

        barChart.getData().add(collectedSeries);
        barChart.getData().add(spentSeries);

        for (XYChart.Data<String, Number> data : collectedSeries.getData()) {
            data.getNode().setStyle("-fx-bar-fill: orange;");
        }

        for (XYChart.Data<String, Number> data : spentSeries.getData()) {
            data.getNode().setStyle("-fx-bar-fill: deepskyblue;");
        }
    }

    private void clearInputs() {
        amountMoneyInput.clear();
        reasonInput.clear();
        dateTimeInput.setValue(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void onSearchReason() {
        String searchQuery = searchReasonInput.getText().toLowerCase();

        ObservableList<ExpenseRecord> filteredList = FXCollections.observableArrayList();
        for (ExpenseRecord record : expensesList) {
            if (record.getReason().toLowerCase().contains(searchQuery)) {
                filteredList.add(record);
            }
        }
        tableView.setItems(filteredList);
    }

    @FXML
    public void onSortChange() {
        String selectedOption = comboBox.getValue();
        if (selectedOption.equals("Sort by Date")) {
            bubbleSortByDate();
        } else if (selectedOption.equals("Sort by Amount")) {
            insertionSortByAmount();
        }
        tableView.setItems(expensesList);
    }

    private void bubbleSortByDate() {
        int n = expensesList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (expensesList.get(j).getDate().isAfter(expensesList.get(j + 1).getDate())) {
                    ExpenseRecord temp = expensesList.get(j);
                    expensesList.set(j, expensesList.get(j + 1));
                    expensesList.set(j + 1, temp);
                }
            }
        }
    }

    private void insertionSortByAmount() {
        int n = expensesList.size();
        for (int i = 1; i < n; i++) {
            ExpenseRecord current = expensesList.get(i);
            int j = i - 1;

            while (j >= 0 && expensesList.get(j).getAmount() > current.getAmount()) {
                expensesList.set(j + 1, expensesList.get(j));
                j = j - 1;
            }

            expensesList.set(j + 1, current);
        }
    }
}
