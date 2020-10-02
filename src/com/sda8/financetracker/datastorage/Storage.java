package com.sda8.financetracker.datastorage;

import java.io.*;

public class Storage {
    private static final String savedData = "data.txt";

    public static void saveData(Object objectToSave) {
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(savedData));
            outputStream.writeObject(objectToSave);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Object restoreData() {
        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(savedData));
            return inputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
