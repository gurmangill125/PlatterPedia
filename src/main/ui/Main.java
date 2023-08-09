package ui;

import javax.swing.*;
import model.Event;
import model.EventLog;

// Represents the main method of the application which runs the App
public class Main {
    public static void main(String[] args) {
        SplashScreen splash = new SplashScreen("data/resources/splashscreen.gif", 5000);

        SwingUtilities.invokeLater(() -> {
            GuiApp guiApp = new GuiApp();
            guiApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            guiApp.setVisible(true);
        });

        // Add a shutdown hook to print the events log when the application is closing
        Runtime.getRuntime().addShutdownHook(new Thread(Main::printEventLog));
    }

    // Print all the logged events to the console
    private static void printEventLog() {
        System.out.println("Logged events:");
        EventLog eventLog = EventLog.getInstance();
        for (Event event : eventLog) {
            System.out.println(event);
        }
    }
}