package com.sda8.financetracker.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Columns class generates tables by evenly spacing passed strings in a row format.
 * Each following row needs to have an equal amount of string variables as the initial row.
 * Spacing will be auto generated to expand and contract column widths as required.
 * Once all rows have been appended, a toString method will print the rows with evenly
 * spaced columns.
 */
public class UiColumns {
    List<List<String>> lines = new ArrayList<>();
    List<Integer> maxLengths = new ArrayList<>();
    int numColumns = -1;

    /**
     * Creates a single row by appending multiple strings together. The row needs to have
     * the same number of string elements as previous rows to avoid formatting issues.
     * @param line multiple String elements to be placed in a row.
     */
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
            int length = Math.max(maxLengths.get(column), line[column].length());
            maxLengths.set(column, length);
        }
        lines.add(Arrays.asList(line));
    }

    /**
     * Prints all rows with evenly spaced columns.
     */
    public void print() {
        System.out.println(toString());
    }

    /**
     * Spaces each column to the required width based on the widest row item of all the row
     * items sharing the same index location. After spacing the rows are appended to a string builder
     * and the complete table is returned in String format.
     * @return completed table in string format after padding is applied.
     */
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

    /**
     * Ads spacing to a string variable until required padding is reached.
     * @param word original word without padding.
     * @param newLength required length of word including padding.
     * @return the word with padding.
     */
    private String pad(String word, int newLength) {
        StringBuilder spacesBetweenColumns = new StringBuilder(word);
        while (spacesBetweenColumns.length() < newLength) {
            spacesBetweenColumns.append(" ");
        }
        word = spacesBetweenColumns.toString();
        return word;
    }
}