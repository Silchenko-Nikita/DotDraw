package states;

import actions.ActionFinishHandler;
import actions.BrushDrawing;
import ui.AfterRepaintAction;
import ui.GraphicsProvider;
import utils.Dot;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class BrushModeState implements State {
    private boolean mouseIn;
    private boolean dragging;
    GraphicsProvider graphicsProvider;
    MouseAdapter mouseAdapter;
    MouseMotionAdapter mouseMotionAdapter;

    BrushDrawing brushDrawing;
    ActionFinishHandler actionFinishHandler;

    public BrushModeState(GraphicsProvider graphicsProvider,
                          ActionFinishHandler actionFinishHandler) {
        this.graphicsProvider = graphicsProvider;
        this.actionFinishHandler = actionFinishHandler;

        mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseIn = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseIn = false;
                graphicsProvider.addAfterRepaintAction(null);
                graphicsProvider.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                brushDrawing = new BrushDrawing(new Dot(e.getX(), e.getY()));

                brushDrawing.setShowBounds(true);
                dragging = true;
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
                actionFinishHandler.setFinishedAction(brushDrawing);
                brushDrawing.setShowBounds(false);

                if (mouseIn) {
                    graphicsProvider.addAfterRepaintAction(new AfterRepaintAction() {
                        @Override
                        public void perform(Graphics g) {
                            g.setColor(Color.BLACK);
                            g.fillOval(e.getX() - 5, e.getY() - 5, 10, 10);

                            g.setColor(Color.WHITE);
                            g.fillOval(e.getX()  - 5 + 1, e.getY() - 5 + 1, 8, 8);
                        }
                    });
                } else {
                    graphicsProvider.addAfterRepaintAction(null);
                }

                graphicsProvider.repaint();
            }
        };

        mouseMotionAdapter = new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (mouseIn) {
                    graphicsProvider.addAfterRepaintAction(new AfterRepaintAction() {
                        @Override
                        public void perform(Graphics g) {
                            g.setColor(Color.BLACK);
                            g.fillOval(e.getX() - 5, e.getY() - 5, 10, 10);

                            g.setColor(Color.WHITE);
                            g.fillOval(e.getX()  - 5 + 1, e.getY() - 5 + 1, 8, 8);
                        }
                    });

                    graphicsProvider.repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    graphicsProvider.addAfterRepaintAction(new AfterRepaintAction() {
                        @Override
                        public void perform(Graphics g) {
                            brushDrawing.update(new Dot(e.getX(), e.getY()));
                            brushDrawing.apply(g);
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
        return Color.WHITE;
    }

    @Override
    public void setCurrentColor(Color currentColor) {
    }
}
