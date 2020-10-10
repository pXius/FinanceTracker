package com.sda8.financetracker.datastorage;

import com.sda8.financetracker.trackercore.Tracker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Storage {
    private static final String savedData = "data.txt";

    public static boolean checkFile() {
        boolean fileIsUsable = true;
        Path file = Paths.get(savedData);
        if (!Files.exists(file)) fileIsUsable = false;
        else if (!Files.isRegularFile(file)) fileIsUsable = false;
        else if (!Files.isReadable(file)) fileIsUsable = false;
        return fileIsUsable;
    }

    public static void saveData(Tracker trackerToSave) {
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(savedData));
            outputStream.writeObject(trackerToSave);
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

    public static Tracker restoreData() {
        ObjectInputStream inputStream = null;
        Tracker restoredTracker = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(savedData));
            restoredTracker = (Tracker) inputStream.readObject();
        } catch (EOFException e) {
            restoredTracker = new Tracker();
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
        if (restoredTracker != null) return restoredTracker;
        return new Tracker();
    }
}
