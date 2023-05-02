package states;

import actions.ActionFinishHandler;
import actions.LineDrawing;
import actions.RegularDrawing;
import ui.AfterRepaintAction;
import ui.GraphicsProvider;
import utils.Dot;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class LineModeState implements State {
    private boolean dragging;
    private Color currentColor;

    GraphicsProvider graphicsProvider;
    MouseAdapter mouseAdapter;
    MouseMotionAdapter mouseMotionAdapter;

    LineDrawing lineDrawing;
    ActionFinishHandler actionFinishHandler;

    public LineModeState(GraphicsProvider graphicsProvider,
                         ActionFinishHandler actionFinishHandler,
                         Color color) {
        this.graphicsProvider = graphicsProvider;
        this.actionFinishHandler = actionFinishHandler;
        currentColor = color;

        mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lineDrawing = new LineDrawing(new Dot(e.getX(), e.getY()), currentColor);
                dragging = true;
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
                actionFinishHandler.setFinishedAction(lineDrawing);
            }
        };

        mouseMotionAdapter = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    graphicsProvider.addAfterRepaintAction(new AfterRepaintAction() {
                        @Override
                        public void perform(Graphics g) {
                            lineDrawing.update(new Dot(e.getX(), e.getY()));
                            lineDrawing.apply(g);
                        }
                    });

                    graphicsProvider.repaint();
                }
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
}
