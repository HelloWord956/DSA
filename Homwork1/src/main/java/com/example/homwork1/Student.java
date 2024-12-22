package com.example.homwork1;

import java.time.LocalDate;

public class Student {
    private String firstName;
    private String lastName;
    private double point;
    private LocalDate dateOfBirth;

    public Student(String firstName, String lastName, double point, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.point = point;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}