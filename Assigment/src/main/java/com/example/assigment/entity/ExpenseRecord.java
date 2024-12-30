package com.example.assigment.entity;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class ExpenseRecord {
    private final DoubleProperty amount;
    private final StringProperty reason;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty type;

    public ExpenseRecord(double amount, String reason, LocalDate date, String type) {
        this.amount = new SimpleDoubleProperty(amount);
        this.reason = new SimpleStringProperty(reason);
        this.date = new SimpleObjectProperty<>(date);
        this.type = new SimpleStringProperty(type);
    }

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public String getReason() {
        return reason.get();
    }

    public StringProperty reasonProperty() {
        return reason;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }
}
