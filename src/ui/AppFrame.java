package ui;

import javax.swing.*;
import java.awt.*;

interface MenuActionHandler {
    void handleMenuAction(MenuAction menuAction);
}

interface ValActionHandler {
    void handleValAction(ValAction type, Object val);
}

enum ValAction {
    INSTRUMENT_SIZE
}

enum MenuAction {
    SAVE_TO_FILE,
    UNDO,
    REDO,
    EXIT,
    CLEAR_CANVAS,
    CHOOSE_FILL,
    CHOOSE_PENCIL,
    CHOOSE_BRUSH,
    CHOOSE_LINE,
    CHOOSE_RECT,
    CHOOSE_FILLED_RECT,
    CHOOSE_OVAL,
    CHOOSE_FILLED_OVAL,
    CHOOSE_COLOR
}

public class AppFrame extends JFrame implements MenuActionHandler, ValActionHandler {

    private MenuBar menuBar;
    private ToolsPanel toolsPanel;
    private CanvasPanel canvas;

    public AppFrame() {
        super("Dot Draw");

        menuBar = new MenuBar(this);
        setJMenuBar(menuBar);

        toolsPanel = new ToolsPanel(this, this);
        canvas = new CanvasPanel();
        canvas.setDoubleBuffered(true);

        getContentPane().add(toolsPanel, BorderLayout.NORTH);
        getContentPane().add(canvas, BorderLayout.CENTER);

        setSize(640, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void handleMenuAction(MenuAction menuAction) {
        switch (menuAction) {
            case SAVE_TO_FILE -> {
                canvas.saveToFile("./file.png");
            }
            case UNDO -> {
                canvas.undo();
            }
            case REDO -> {
                canvas.redo();
            }
            case EXIT -> {
                System.exit(0);
            }
            case CLEAR_CANVAS -> {
                canvas.clear();
            }
            case CHOOSE_FILL -> {
                canvas.switchDrawingMode(DrawingMode.FILL);
            }
            case CHOOSE_PENCIL -> {
                canvas.switchDrawingMode(DrawingMode.PENCIL);
            }
            case CHOOSE_BRUSH -> {
                canvas.switchDrawingMode(DrawingMode.BRUSH);
            }
            case CHOOSE_LINE -> {
                canvas.switchDrawingMode(DrawingMode.LINE);
            }
            case CHOOSE_RECT -> {
                canvas.switchDrawingMode(DrawingMode.RECT);
            }
            case CHOOSE_FILLED_RECT -> {
                canvas.switchDrawingMode(DrawingMode.FILLED_RECT);
            }
            case CHOOSE_OVAL -> {
                canvas.switchDrawingMode(DrawingMode.OVAL);
            }
            case CHOOSE_FILLED_OVAL -> {
                canvas.switchDrawingMode(DrawingMode.FILLED_OVAL);
            }
            case CHOOSE_COLOR -> {
                Color colour = JColorChooser.showDialog(null, "Choose a color", canvas.getCurrentColor());
                canvas.setCurrentColor(colour);
                Component[] components = toolsPanel.getComponents();
                for (int i = 0; i < components.length; i++) {
                    var name = components[i].getName();
                    if (name != null && name.equals("colourchange")) {
                        components[i].setBackground(colour);
                    }
                }
            }
        }
    }

    @Override
    public void handleValAction(ValAction type, Object val) {
        switch (type) {
            case INSTRUMENT_SIZE -> {
                canvas.setInstrumentSize((Integer)val);
            }
        }
    }
}
