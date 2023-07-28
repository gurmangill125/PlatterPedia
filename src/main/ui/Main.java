package ui;

import javax.swing.*;

// Represents the main method of the application which runs the App
public class Main {
    public static void main(String[] args) {
        SplashScreen splash = new SplashScreen("data/resources/Splashscreen.png", 5);

        SwingUtilities.invokeLater(() -> {
            GuiApp guiApp = new GuiApp();
            guiApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            guiApp.setVisible(true);
        });
    }
}
