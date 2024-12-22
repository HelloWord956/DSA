package com.example.homwork1;

import java.util.List;

public class SelectionSort extends SortStrategy {
    @Override
    public void sort(List<Student> students) {
        int n = students.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (students.get(j).getPoint() < students.get(minIndex).getPoint()) {
                    minIndex = j;
                }
            }
            Student temp = students.get(minIndex);
            students.set(minIndex, students.get(i));
            students.set(i, temp);
        }
    }
}
