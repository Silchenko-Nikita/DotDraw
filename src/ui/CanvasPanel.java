package ui;

import actions.Action;
import actions.ActionFinishHandler;
import states.*;
import utils.Dot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

enum DrawingMode {
    FILL,
    PENCIL,
    BRUSH,
    LINE,
    RECT,
    FILLED_RECT,
    OVAL,
    FILLED_OVAL
}

public class CanvasPanel extends JPanel implements GraphicsProvider, ActionFinishHandler {
    static RegularDrawingModeState regularDrawingModeState;
    static FillModeState fillModeState;
    static BrushModeState brushModeState;
    static LineModeState lineModeState;
    static RectModeState rectModeState;
    static FilledRectModeState filledRectModeState;
    static OvalModeState ovalModeState;
    static FilledOvalModeState filledOvalModeState;

    static ArrayList<State> states;

    private Stack<BufferedImage> images;
    private Stack<BufferedImage> undoneImages;
    private Stack<Action> actions;
    private Stack<Action> undoneActions;


    private State currentState;
    private AfterRepaintAction afterRepaintAction = null;

    CanvasPanel() {
        {
            regularDrawingModeState = new RegularDrawingModeState(this, this, Color.BLACK);
            fillModeState = new FillModeState(this, this, Color.BLACK);
            brushModeState = new BrushModeState(this, this);
            lineModeState = new LineModeState(this, this, Color.BLACK);
            rectModeState = new RectModeState(this, this, Color.BLACK);
            filledRectModeState = new FilledRectModeState(this, this, Color.BLACK);
            ovalModeState = new OvalModeState(this, this, Color.BLACK);
            filledOvalModeState = new FilledOvalModeState(this, this, Color.BLACK);

            states = new ArrayList<>();
            states.add(regularDrawingModeState);
            states.add(fillModeState);
            states.add(brushModeState);
            states.add(lineModeState);
            states.add(rectModeState);
            states.add(filledRectModeState);
            states.add(ovalModeState);
            states.add(filledOvalModeState);
        }

        images = new Stack<>();
        undoneImages = new Stack<>();
        actions = new Stack<>();
        undoneActions = new Stack<>();
        setBackground(Color.WHITE);

        applyState(regularDrawingModeState);
        currentState.setCurrentColor(Color.BLACK);
        repaint();
    }

    public Color getCurrentColor() {
        return currentState.getCurrentColor();
    }

    public void setCurrentColor(Color currentColor) {
        for (State state: states) {
            state.setCurrentColor(currentColor);
        }
    }

    public int getInstrumentSize() {
        return currentState.getInstrumentSize();
    }

    public void setInstrumentSize(int val) {
        for (State state: states) {
            state.setInstrumentSize(val);
        }
    }

    public void switchDrawingMode(DrawingMode drawingMode) {
        switch (drawingMode) {
            case PENCIL -> {
                applyState(regularDrawingModeState);
            }
            case FILL -> {
                applyState(fillModeState);
            }
            case BRUSH -> {
                applyState(brushModeState);
            }
            case LINE -> {
                applyState(lineModeState);
            }
            case RECT -> {
                applyState(rectModeState);
            }
            case FILLED_RECT -> {
                applyState(filledRectModeState);
            }
            case OVAL -> {
                applyState(ovalModeState);
            }
            case FILLED_OVAL -> {
                applyState(filledOvalModeState);
            }
        }
    }

    private void applyState(State state) {
        if (currentState != null) {
            removeMouseListener(currentState.getMouseListener());
            removeMouseMotionListener(currentState.getMouseMotionListener());
        }

        this.currentState = state;

        addMouseListener(currentState.getMouseListener());
        addMouseMotionListener(currentState.getMouseMotionListener());
    }

    @Override
    public void setFinishedAction(Action finishedAction) {
        actions.push(finishedAction);

        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        printAll(g2d);

        finishedAction.apply(g2d);

        g2d.dispose();
        images.push(image);

        afterRepaintAction = null;

        undoneActions.clear();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!images.isEmpty()) {
            g.drawImage(images.peek(), 0, 0, null);
        }

        if (afterRepaintAction != null) {
            afterRepaintAction.perform(g);
        }
    }

    public void undo() {
        if (actions.isEmpty()) return;
        if (images.isEmpty()) return;

        undoneActions.push(actions.pop());
        undoneImages.push(images.pop());

        repaint();
    }

    public void redo() {
        if (undoneActions.isEmpty()) return;
        if (undoneImages.isEmpty()) return;

        Action lastUndoneAction = undoneActions.pop();
        actions.push(lastUndoneAction);

        BufferedImage lastUndoneImage = undoneImages.pop();
        images.push(lastUndoneImage);

        repaint();
    }

    public void clear() {
        actions.clear();
        undoneActions.clear();
        images.clear();
        undoneImages.clear();
        repaint();
    }

    public void saveToFile(String fileName) {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        printAll(g2d);
        g2d.dispose();

        try {
            File outputFile = new File(fileName);
            ImageIO.write(image, "png", outputFile);
            System.out.println("Panel saved to " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAfterRepaintAction(AfterRepaintAction action) {
        this.afterRepaintAction = action;
    }
}
