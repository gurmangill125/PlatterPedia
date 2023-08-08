package ui;

import javax.swing.*;
import java.awt.*;

// SplashScreen is a subclass of JWindow that displays a splash screen with an image for a specified amount of time.
// The splash screen is used to display a graphical image or animation during an application's startup.
// CITATION: Some components were based on the following YouTube video from "Programming. OM":
//           https://www.youtube.com/watch?v=xt7Ofpfh69w
public class SplashScreen extends JWindow {

    // REQUIRES: filename should be a valid image file path; waitTime should be a valid integer.
    // MODIFIES: this
    // EFFECTS: Creates a new SplashScreen with specified filename and wait time.
    public SplashScreen(String filename, int waitTime) {
        ImageIcon myImage = new ImageIcon(filename);
        JLabel imageLabel = new JLabel(myImage);
        getContentPane().add(imageLabel, BorderLayout.CENTER);

        setSize(myImage.getIconWidth(), myImage.getIconHeight());
        setLocationRelativeTo(null);
        setVisible(true);
        repaint();


        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setVisible(false);
    }
}
