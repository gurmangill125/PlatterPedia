package ui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JLabel;


// GradientLabel is a subclass of JLabel that paints its background with a gradient color.
// The gradient color is defined by two colors passed to the constructor.
public class GradientLabel extends JLabel {
    private Color color1;
    private Color color2;

    // EFFECTS: Creates a new GradientLabel with the specified text, color1, and color2.
    public GradientLabel(String text, Color color1, Color color2) {
        super(text);
        this.color1 = color1;
        this.color2 = color2;
        setOpaque(false);
    }

    // MODIFIES: this
    // EFFECTS: Paints the component with a gradient color.
    // CITATION: Referenced code from the following Stack Overflow post when designing this method:
    //           https://stackoverflow.com/questions/27641641/creating-a-jlabel-with-a-gradient
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(new GradientPaint(new Point(0, 0), color1, new Point(getWidth(), getHeight()), color2));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();

        super.paintComponent(g);
    }
}
