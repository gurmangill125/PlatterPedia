package ui;

import javax.swing.*;
import java.awt.*;

/**
 * SplashScreen is a subclass of JWindow that displays a splash screen with an image for a specified amount of time.
 * The splash screen is used to display a graphical image or animation during an application's startup.
 */
public class SplashScreen extends JWindow {

    // REQUIRES: filename should be a valid image file path; waitTime should be a valid integer.
    // MODIFIES: this
    // EFFECTS: Creates a new SplashScreen with specified filename and wait time.
    public SplashScreen(String filename, int waitTime) {
        ImageIcon myImage = new ImageIcon(filename);
        // Resize ImageIcon to fit specified dimensions
        Image img = myImage.getImage();
        Image resizedImage = img.getScaledInstance(700, 600, java.awt.Image.SCALE_SMOOTH);
        myImage = new ImageIcon(resizedImage);

        JLabel imageLabel = new JLabel(myImage);
        getContentPane().add(imageLabel, BorderLayout.CENTER);

        setSize(700, 600); // Set the size of the splash screen
        setLocationRelativeTo(null);
        setVisible(true);

        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setVisible(false);
    }
}
