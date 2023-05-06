package actions;

import ui.CanvasPanel;
import ui.GraphicsProvider;
import utils.Dot;

import java.awt.*;
import java.util.LinkedList;
import java.util.Stack;

public class ColorPick implements Action {
    int instrumentSize;
    int[][] bitmap;
    int startColorRGB;
    Dot pos;
    Color color;
    CanvasPanel canvas;

    public ColorPick(int[][] bitmap, Dot pos, Color color, int instrumentSize, CanvasPanel canvas) {
        this.bitmap = bitmap;
        this.pos = pos;
        this.color = color;
        this.instrumentSize = instrumentSize;

        this.canvas = canvas;
    }

    @Override
    public void apply(Graphics g) {
        canvas.setCurrentColorFromPicker(new Color(bitmap[pos.getX()][pos.getY()]));
    }
}
