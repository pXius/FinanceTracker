package com.sda8.financetracker;

import com.sda8.financetracker.app.TrackerApp;

/**
 * This class serves as an entry point to the tracker app and nothing else.
 * A TrackerApp instance in instantiated and it's run() method is called.
 */
public class Main {

    public static void main(String[] args) {
        TrackerApp app = new TrackerApp();
        app.run();
    }
}
