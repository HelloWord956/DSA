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

public class MainController {

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
    private TableColumn<ExpenseRecord, String> typeTableColumn;

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

    private final ObservableList<ExpenseRecord> expensesList = FXCollections.observableArrayList();
    private String currentType = "Collected";

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
        typeTableColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

        tableView.setItems(expensesList);

        searchReasonInput.textProperty().addListener((observable, oldValue, newValue) -> onSearchReason(newValue));

        barChart.setTitle("Monthly Expense Overview");
        xAxis.setLabel("Month");
        yAxis.setLabel("Amount");
    }

    public void onCollectedButtonClick() {
        clearInputs();
        currentType = "Collected";
        amountMoneyInput.setPromptText("Enter amount of money collected");
        reasonInput.setPromptText("Enter reason for money collected");
    }

    public void onSpentButtonClick() {
        clearInputs();
        currentType = "Spent";
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

        ExpenseRecord newRecord = new ExpenseRecord(amount, reasonText, date, currentType);

        expensesList.add(newRecord);

        updateBarChart();

        tableView.setItems(expensesList);

        tableView.refresh();

        clearInputs();
    }

    private void updateBarChart() {
        barChart.getData().clear();

        Map<String, Double> collectedMoneyByMonth = new HashMap<>();
        Map<String, Double> spentMoneyByMonth = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        for (ExpenseRecord record : expensesList) {
            String month = record.getDate().format(formatter);

            if (record.getType().equals("Collected")) {
                collectedMoneyByMonth.put(month, collectedMoneyByMonth.getOrDefault(month, 0.0) + record.getAmount());
            } else {
                spentMoneyByMonth.put(month, spentMoneyByMonth.getOrDefault(month, 0.0) + record.getAmount());
            }
        }

        XYChart.Series<String, Number> collectedSeries = new XYChart.Series<>();
        collectedSeries.setName("Money Collected");

        XYChart.Series<String, Number> spentSeries = new XYChart.Series<>();
        spentSeries.setName("Money Spent");

        for (Map.Entry<String, Double> entry : collectedMoneyByMonth.entrySet()) {
            collectedSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        for (Map.Entry<String, Double> entry : spentMoneyByMonth.entrySet()) {
            spentSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().add(collectedSeries);
        barChart.getData().add(spentSeries);

        for (XYChart.Data<String, Number> data : collectedSeries.getData()) {
            data.getNode().setStyle("-fx-bar-fill: orange;");
        }

        for (XYChart.Data<String, Number> data : spentSeries.getData()) {
            data.getNode().setStyle("-fx-bar-fill: deepskyblue;");
        }

        ObservableList<String> months = FXCollections.observableArrayList(
                "2024-01", "2024-02", "2024-03", "2024-04", "2024-05", "2024-06", "2024-07", "2024-08", "2024-09", "2024-10", "2024-11", "2024-12"
        );
        xAxis.setCategories(months);

        xAxis.setTickLabelRotation(45);
        xAxis.setTickLength(10);
        xAxis.setTickMarkVisible(true);
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
    public void onSearchReason(String searchQuery) {
        searchQuery = searchQuery.toLowerCase();

        ObservableList<ExpenseRecord> filteredList = FXCollections.observableArrayList();

        if (searchQuery.isEmpty()) {
            filteredList.addAll(expensesList);
        } else {
            for (ExpenseRecord record : expensesList) {
                if (record.getReason().toLowerCase().contains(searchQuery)) {
                    filteredList.add(record);
                }
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
        for (int i = 1; i < expensesList.size(); i++) {
            ExpenseRecord key = expensesList.get(i);
            int j = i - 1;
            while (j >= 0 && expensesList.get(j).getAmount() > key.getAmount()) {
                expensesList.set(j + 1, expensesList.get(j));
                j--;
            }
            expensesList.set(j + 1, key);
        }
    }
}
