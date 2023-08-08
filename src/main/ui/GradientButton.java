package ui;

import javax.swing.*;
import java.awt.event.ActionListener;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JButton;

/**
 * GradientButton is a subclass of JButton that paints its background with a gradient color.
 * The gradient color is defined by two colors passed to the constructor.
 * It also takes an icon, action listener and tooltip text.
 */
public class GradientButton extends JButton {
    private Color color1;
    private Color color2;

    // EFFECTS: Creates a new GradientButton with specified icon, listener, tooltip, color1, and color2.
    public GradientButton(ImageIcon icon, ActionListener listener, String tooltip, Color color1, Color color2) {
        super(icon);
        this.color1 = color1;
        this.color2 = color2;
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setToolTipText(tooltip);
        addActionListener(listener);
    }

    // REQUIRES: A valid Graphics object.
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