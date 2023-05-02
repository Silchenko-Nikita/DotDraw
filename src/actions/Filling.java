package actions;

import ui.CanvasPanel;
import ui.GraphicsProvider;
import utils.Dot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.Stack;

public class Filling implements Action {
    int[][] bitmap;
    Dot pos;
    Color color;

    public Filling(int[][] bitmap, Dot pos, Color color) {
        this.bitmap = bitmap;
        this.pos = pos;
        this.color = color;
    }

    @Override
    public void apply(Graphics g) {
        if (pos == null) return;

        int[][] bitmap1 = new int[bitmap.length][bitmap[0].length];

        for (int i = 0; i < bitmap.length; i++) {
            System.arraycopy(bitmap[i], 0, bitmap1[i], 0, bitmap1[i].length);
        }

        g.setColor(color);

        int startColorRGB = bitmap1[pos.getX()][pos.getY()];

        Stack<Point> stack = new Stack<>();
        stack.push(new Point(pos.getX(), pos.getY()));

        while (!stack.isEmpty()) {
            Point p = stack.pop();
            int px = p.x;
            int py = p.y;

            if (px < 0 || px >= bitmap1.length || py < 0 || py >= bitmap1[px].length || startColorRGB != bitmap[px][py]
                    || color.getRGB() == bitmap1[px][py]) continue;

            bitmap1[px][py] = color.getRGB();
            g.drawLine(px, py, px, py);

            stack.push(new Point(px - 1, py));
            stack.push(new Point(px + 1, py));
            stack.push(new Point(px, py - 1));
            stack.push(new Point(px, py + 1));
        }
    }
}
