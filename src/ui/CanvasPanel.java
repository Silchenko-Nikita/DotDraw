package ui;

import actions.Action;
import actions.ActionFinishHandler;
import states.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Stack;

enum DrawingMode {
    FILL,
    PENCIL,
    BRUSH,
    LINE,
    RECT,
    FILLED_RECT,
    FILLED_OVAL
}

public class CanvasPanel extends JPanel implements GraphicsProvider, ActionFinishHandler {
    static RegularDrawingModeState regularDrawingModeState;
    static FillModeState fillModeState;
    static BrushModeState brushModeState;
    static LineModeState lineModeState;
    static RectModeState rectModeState;
    static FilledRectModeState filledRectModeState;
    static FilledOvalModeState filledOvalModeState;

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
            filledOvalModeState = new FilledOvalModeState(this, this, Color.BLACK);
        }

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
        currentState.setCurrentColor(currentColor);
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
        addAfterRepaintAction(null);
        undoneActions.clear();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Action action: actions) {
            action.apply(g);
        }

        if (afterRepaintAction != null) {
            afterRepaintAction.perform(g);
        }
    }

    public void undo() {
        if (actions.isEmpty()) return;

        undoneActions.push(actions.pop());

        repaint();
    }

    public void redo() {
        if (undoneActions.isEmpty()) return;

        Action lastUndoneAction = undoneActions.pop();
        actions.push(lastUndoneAction);

        repaint();
    }

    public void clear() {
        actions.clear();
        undoneActions.clear();
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

        int[][] bitmap = new int[image.getWidth()][image.getHeight()];
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                bitmap[x][y] = image.getRGB(x, y);
            }
        }
    }

    @Override
    public void addAfterRepaintAction(AfterRepaintAction action) {
        this.afterRepaintAction = action;
    }
}
