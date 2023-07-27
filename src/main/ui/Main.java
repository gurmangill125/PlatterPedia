package ui;

import javax.swing.*;

// Represents the main method of the application which runs the App
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GuiApp guiApp = new GuiApp();
            guiApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            guiApp.setVisible(true);
        });
    }
}
