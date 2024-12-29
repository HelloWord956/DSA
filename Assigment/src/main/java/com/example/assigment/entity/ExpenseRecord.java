package com.example.assigment.entity;

import java.time.LocalDate;

public class ExpenseRecord {
    private final javafx.beans.property.DoubleProperty amount;
    private final javafx.beans.property.StringProperty reason;
    private final javafx.beans.property.ObjectProperty<LocalDate> date;

    public ExpenseRecord(double amount, String reason, LocalDate date) {
        this.amount = new javafx.beans.property.SimpleDoubleProperty(amount);
        this.reason = new javafx.beans.property.SimpleStringProperty(reason);
        this.date = new javafx.beans.property.SimpleObjectProperty<>(date);
    }

    public double getAmount() {
        return amount.get();
    }

    public javafx.beans.property.DoubleProperty amountProperty() {
        return amount;
    }

    public String getReason() {
        return reason.get();
    }

    public javafx.beans.property.StringProperty reasonProperty() {
        return reason;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public javafx.beans.property.ObjectProperty<LocalDate> dateProperty() {
        return date;
    }
}
