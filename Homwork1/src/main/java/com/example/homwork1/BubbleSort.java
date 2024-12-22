package com.example.homwork1;

import java.util.List;

public class BubbleSort extends SortStrategy {
    @Override
    public void sort(List<Student> students) {
        int n = students.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (students.get(j).getPoint() > students.get(j + 1).getPoint()) {
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }
    }
}
