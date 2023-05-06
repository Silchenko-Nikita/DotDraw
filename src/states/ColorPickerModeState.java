package states;

import actions.ActionFinishHandler;
import actions.ColorPick;
import actions.Filling;
import ui.CanvasPanel;
import ui.GraphicsProvider;
import utils.Dot;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class ColorPickerModeState implements State {
    private int instrumentSize = 1;
    private boolean pressed;
    private Color currentColor;

    GraphicsProvider graphicsProvider;
    MouseAdapter mouseAdapter;
    MouseMotionAdapter mouseMotionAdapter;

    ColorPick colorPick;
    ActionFinishHandler actionFinishHandler;

    public ColorPickerModeState(CanvasPanel graphicsProvider,
                                ActionFinishHandler actionFinishHandler) {
        this.graphicsProvider = graphicsProvider;
        this.actionFinishHandler = actionFinishHandler;
        currentColor = Color.BLACK;

        mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!pressed) return;

                BufferedImage image = new BufferedImage(graphicsProvider.getWidth(),
                                                        graphicsProvider.getHeight(),
                                                        BufferedImage.TYPE_INT_ARGB);

                Graphics2D g2d = image.createGraphics();
                graphicsProvider.printAll(g2d);
                g2d.dispose();

                int[][] bitmap = new int[image.getWidth()][image.getHeight()];
                for (int x = 0; x < image.getWidth(); x++) {
                    for (int y = 0; y < image.getHeight(); y++) {
                        bitmap[x][y] = image.getRGB(x, y);
                    }
                }

                colorPick = new ColorPick(bitmap, new Dot(e.getX(), e.getY()), currentColor, instrumentSize, graphicsProvider);
                actionFinishHandler.setFinishedAction(colorPick);
                graphicsProvider.repaint();
            }
        };
    }

    @Override
    public MouseAdapter getMouseListener() {
        return mouseAdapter;
    }

    @Override
    public MouseMotionAdapter getMouseMotionListener() {
        return mouseMotionAdapter;
    }

    @Override
    public Color getCurrentColor() {
        return currentColor;
    }

    @Override
    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }

    @Override
    public int getInstrumentSize() {
        return instrumentSize;
    }

    @Override
    public void setInstrumentSize(int val) {
        instrumentSize = val;
    }
}
