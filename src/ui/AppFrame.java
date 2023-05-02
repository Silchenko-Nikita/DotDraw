package ui;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame implements MenuActionHandler {

    private MenuBar menuBar;

    private CanvasPanel canvas;

    public AppFrame() {
        super("Dot Draw");

        menuBar = new MenuBar(this);
        setJMenuBar(menuBar);

        canvas = new CanvasPanel();
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
            case CHOOSE_FILLED_OVAL -> {
                canvas.switchDrawingMode(DrawingMode.FILLED_OVAL);
            }
            case CHOOSE_COLOR -> {
                canvas.setCurrentColor(JColorChooser.showDialog(null,
                        "Choose a color", canvas.getCurrentColor()));
            }
        }
    }
}
