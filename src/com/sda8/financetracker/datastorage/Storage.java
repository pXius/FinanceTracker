package com.sda8.financetracker.datastorage;

import java.io.*;

public class Storage {
    private static final String savedData = "data.txt";

    public static void saveData(Object objectToSave) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(savedData));
        outputStream.writeObject(objectToSave);
        outputStream.close();
    }

    public static Object restoreData() throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(savedData));
        return inputStream.readObject();
    }
}