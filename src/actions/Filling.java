package actions;

import utils.Dot;

import java.awt.*;
import java.util.LinkedList;
import java.util.Stack;

public class Filling implements Action {
    int instrumentSize;
    int[][] bitmap;
    int startColorRGB;
    Dot pos;
    Color color;
    LinkedList<Dot> points = new LinkedList<>();

    boolean applyCalled = false;

    public Filling(int[][] bitmap, Dot pos, Color color, int instrumentSize) {
        this.bitmap = bitmap;
        this.pos = pos;
        this.color = color;
        this.instrumentSize = instrumentSize;
    }

    private boolean isStopDot(int px, int py, int startColorRGB) {
        return px < 0 || px >= bitmap.length || py < 0 || py >= bitmap[0].length
                || startColorRGB != bitmap[px][py];
    }

    @Override
    public void apply(Graphics g) {
        if (pos == null) return;
        if (pos.getX() < 0 || pos.getX() >= bitmap.length ||
                pos.getY() < 0 || pos.getY() >= bitmap[0].length) return;
        if (!applyCalled) startColorRGB = bitmap[pos.getX()][pos.getY()];
        int fillColorRGB = color.getRGB();

        if (startColorRGB == fillColorRGB) return;

        g.setColor(color);
        ((Graphics2D) g).setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        if (applyCalled) {
            for (Dot point : points) {
                g.drawLine(point.getX(), point.getY(), point.getX(), point.getY());
            }
            return;
        }

        Stack<Dot> stack = new Stack<>();
        stack.push(new Dot(pos.getX(), pos.getY()));
        while (!stack.isEmpty()) {
            Dot p = stack.pop();
            points.add(p);
            int px = p.getX();
            int py = p.getY();

            bitmap[px][py] = fillColorRGB;
            g.drawLine(px, py, px, py);

            if (!isStopDot(px - 1, py, startColorRGB)) stack.push(new Dot(px - 1, py));
            if (!isStopDot(px + 1, py, startColorRGB)) stack.push(new Dot(px + 1, py));
            if (!isStopDot(px, py - 1, startColorRGB)) stack.push(new Dot(px, py - 1));
            if (!isStopDot(px, py + 1, startColorRGB)) stack.push(new Dot(px, py + 1));

        }

        applyCalled = true;
    }
}
