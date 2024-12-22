package com.example.homwork1;

import java.util.List;

public class InsertionSort extends SortStrategy {
    @Override
    public void sort(List<Student> students) {
        int n = students.size();
        for (int i = 1; i < n; i++) {
            Student key = students.get(i);
            int j = i - 1;
            while (j >= 0 && students.get(j).getPoint() < key.getPoint()) {
                students.set(j + 1, students.get(j));
                j--;
            }
            students.set(j + 1, key);
        }
    }
}
