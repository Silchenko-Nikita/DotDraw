package ui;

import java.awt.*;

public interface GraphicsProvider {
    Graphics getGraphics();
    void repaint();
    void addAfterRepaintAction(AfterRepaintAction action);

    void printAll(Graphics g2d);
    int getWidth();
    int getHeight();
}
