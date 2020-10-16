package com.sda8.financetracker.datastorage;

import com.sda8.financetracker.trackercore.Tracker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Storage contains static methods used to save and restore Tracker objects which form the core of the FinTrack app.
 * When correctly implemented the class will generate a data file to save program state and restore back to in on
 * load. Some checks are put in place to check for corruption and readability.
 * The savedDate field can be modified to change the file name.
 */
public class Storage {
    private static final String savedData = "data";

    /**
     * This method checks that a datafile is usable by confirming if a file exists, and whether it is readable.
     * A boolean value of true is returned when usable.
     * @return true if usable, false if not.
     */
    public static boolean checkFile() {
        boolean fileIsUsable = true;
        Path file = Paths.get(savedData);
        if (!Files.exists(file)) fileIsUsable = false;
        else if (!Files.isRegularFile(file)) fileIsUsable = false;
        else if (!Files.isReadable(file)) fileIsUsable = false;
        return fileIsUsable;
    }

    /**
     * This methods takes a Tracker object, serializes it and streams it into a data file. If the
     * file does not exist, a new file is created. Some exception handling is done.
     * @param trackerToSave the tracker object to be saved to a file.
     */
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

    /**
     * This method attempts to restore a Tracker object from a data file using ObjectInputStreams.
     * If a file is unreadable or unavailable a new Tracker object is returned instead.
     * Some exception handling takes place.
     * @return a Tracker object from a data file or a new Tracker object if file does not exist.
     */
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
