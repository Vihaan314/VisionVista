package com.visionvista.components;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;

public class OutlineText extends JComponent{
    private String text;
    private Color textColor;
    private Color outlineColor;
    private Font font;

    public OutlineText(String text, Color textColor, Color outlineColor, Font font) {
        this.text = text;
        this.textColor = textColor;
        this.outlineColor = outlineColor;
        this.font = font;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        FontMetrics fm = g2d.getFontMetrics(font);
        Rectangle2D bounds = fm.getStringBounds(text, g2d);

        int x = (int) ((getWidth() - bounds.getWidth()) / 2);
        int y = (int) ((getHeight() + bounds.getHeight()) / 2);

        g2d.setFont(font);

        // Draw the outline
        g2d.setColor(outlineColor);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    g2d.drawString(text, x + i, y + j);
                }
            }
        }

        // Draw the main text
        g2d.setColor(textColor);
        g2d.drawString(text, x, y);
    }
}
