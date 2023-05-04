package states;

import actions.ActionFinishHandler;
import actions.FilledRectDrawing;
import actions.RectDrawing;
import ui.AfterRepaintAction;
import ui.GraphicsProvider;
import utils.Dot;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class FilledRectModeState implements State {
    private int instrumentSize = 1;
    private boolean dragging;
    private Color currentColor;

    GraphicsProvider graphicsProvider;
    MouseAdapter mouseAdapter;
    MouseMotionAdapter mouseMotionAdapter;

    FilledRectDrawing rectDrawing;
    ActionFinishHandler actionFinishHandler;

    public FilledRectModeState(GraphicsProvider graphicsProvider,
                               ActionFinishHandler actionFinishHandler,
                               Color color) {
        this.graphicsProvider = graphicsProvider;
        this.actionFinishHandler = actionFinishHandler;
        currentColor = color;

        mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                rectDrawing = new FilledRectDrawing(new Dot(e.getX(), e.getY()), currentColor, instrumentSize);
                dragging = true;
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
                actionFinishHandler.setFinishedAction(rectDrawing);
            }
        };

        mouseMotionAdapter = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    graphicsProvider.addAfterRepaintAction(new AfterRepaintAction() {
                        @Override
                        public void perform(Graphics g) {
                            rectDrawing.update(new Dot(e.getX(), e.getY()));
                            rectDrawing.apply(g);
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

    @Override
    public int getInstrumentSize() {
        return instrumentSize;
    }

    @Override
    public void setInstrumentSize(int val) {
        instrumentSize = val;
    }
}
