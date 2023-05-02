package actions;

import utils.Dot;

import java.awt.*;
import java.util.LinkedList;

public class LineDrawing implements Action {
    Dot startDot;
    Dot endDot;
    Color color;


    public LineDrawing(Dot initPos, Color color) {
        startDot = initPos;
        this.color = color;
    }

    public void update(Dot pos) {
        endDot = pos;
    }

    @Override
    public void apply(Graphics g) {
        if (startDot == null || endDot == null) return;

        g.setColor(color);
        ((Graphics2D) g).setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        g.drawLine(startDot.getX(), startDot.getY(), endDot.getX(), endDot.getY());
    }
}
