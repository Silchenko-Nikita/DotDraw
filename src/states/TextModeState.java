package states;

import actions.ActionFinishHandler;
import actions.Filling;
import actions.TextPlacing;
import ui.AfterRepaintAction;
import ui.CanvasPanel;
import ui.GraphicsProvider;
import utils.Dot;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class TextModeState implements State {
    private int instrumentSize = 1;
    private boolean pressed;
    private Color currentColor;
    private int fontSize;

    GraphicsProvider graphicsProvider;
    MouseAdapter mouseAdapter;
    MouseMotionAdapter mouseMotionAdapter;
    public KeyAdapter keyAdapter;

    public TextPlacing textPlacing;
    ActionFinishHandler actionFinishHandler;

    public TextModeState(CanvasPanel graphicsProvider,
                         ActionFinishHandler actionFinishHandler,
                         Color color) {
        this.graphicsProvider = graphicsProvider;
        this.actionFinishHandler = actionFinishHandler;
        currentColor = color;

        mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!pressed) return;

                graphicsProvider.requestFocusInWindow();

                if (textPlacing != null) {
                    textPlacing.setBeingActiveState(false);
                    graphicsProvider.setFinishedAction(textPlacing);

                    graphicsProvider.addAfterRepaintAction(new AfterRepaintAction() {
                        @Override
                        public void perform(Graphics g) {
                            textPlacing.apply(g);

                            textPlacing = new TextPlacing(new Dot(e.getX(), e.getY()), currentColor, instrumentSize, fontSize);
                            textPlacing.setBeingActiveState(true);

                            graphicsProvider.addAfterRepaintAction(new AfterRepaintAction() {
                                @Override
                                public void perform(Graphics g) {
                                    textPlacing.apply(g);
                                }
                            });

                            graphicsProvider.repaint();
                        }
                    });
                } else {
                    textPlacing = new TextPlacing(new Dot(e.getX(), e.getY()), currentColor, instrumentSize, fontSize);
                    textPlacing.setBeingActiveState(true);

                    graphicsProvider.addAfterRepaintAction(new AfterRepaintAction() {
                        @Override
                        public void perform(Graphics g) {
                            textPlacing.apply(g);
                        }
                    });
                }

                graphicsProvider.repaint();
            }
        };

        keyAdapter = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if (keyCode == KeyEvent.VK_ENTER) {
                    textPlacing.setBeingActiveState(false);
                    graphicsProvider.setFinishedAction(textPlacing);

                    graphicsProvider.addAfterRepaintAction(new AfterRepaintAction() {
                        @Override
                        public void perform(Graphics g) {
                            textPlacing.apply(g);
                            graphicsProvider.addAfterRepaintAction(null);
                        }
                    });

                    graphicsProvider.repaint();
                }
            }

            private boolean isReadableUnicodeChar(char c) {
                return Character.isDefined(c) &&
                        (Character.getType(c) != Character.CONTROL) &&
                        (Character.getType(c) != Character.FORMAT) &&
                        (Character.getType(c) != Character.PRIVATE_USE) &&
                        (Character.getType(c) != Character.SURROGATE);
            }

            @Override
            public void keyTyped(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if (keyCode == KeyEvent.VK_ENTER) {
                    return;
                }

                char keyChar = e.getKeyChar();
                if (isReadableUnicodeChar(keyChar)) {
                    textPlacing.update(keyChar);
                }


                graphicsProvider.addAfterRepaintAction(new AfterRepaintAction() {
                    @Override
                    public void perform(Graphics g) {
                        textPlacing.apply(g);
                    }
                });

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
        if(textPlacing == null) return;

        textPlacing.setColor(currentColor);
    }

    @Override
    public int getInstrumentSize() {
        return instrumentSize;
    }

    @Override
    public void setInstrumentSize(int val) {
        instrumentSize = val;
        if(textPlacing == null) return;

        textPlacing.setInstrumentSize(val);
    }

    public void setFontSize(int val) {
        fontSize = val;
    }
}
