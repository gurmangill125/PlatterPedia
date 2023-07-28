package ui;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {
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
