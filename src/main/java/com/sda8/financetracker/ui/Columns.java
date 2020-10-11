package com.sda8.financetracker.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Columns {

    List<List<String>> lines = new ArrayList<>();
    List<Integer> maxLengths = new ArrayList<>();
    int numColumns = -1;

    public void addLine(String... line) {
        if (numColumns == -1) {
            numColumns = line.length;
            for (int column = 0; column < numColumns; column++) {
                maxLengths.add(0);
            }
        }
        if (numColumns != line.length) {
            throw new IllegalArgumentException();
        }
        for (int column = 0; column < numColumns; column++) {
            int length = Math
                    .max(
                            maxLengths.get(column),
                            line[column].length()
                    );
            maxLengths.set(column, length);
        }
        lines.add(Arrays.asList(line));
    }

    public void print() {
        System.out.println(toString());
    }

    public String toString() {
        StringBuilder column = new StringBuilder();
        for (List<String> line : lines) {
            for (int i = 0; i < numColumns; i++) {
                column.append(pad(line.get(i), maxLengths.get(i) + 1));
            }
            column.append(System.lineSeparator());
        }
        return column.toString();
    }

    private String pad(String word, int newLength) {
        StringBuilder spacesBetweenColumns = new StringBuilder(word);
        while (spacesBetweenColumns.length() < newLength) {
            spacesBetweenColumns.append(" ");
        }
        word = spacesBetweenColumns.toString();
        return word;
    }
}