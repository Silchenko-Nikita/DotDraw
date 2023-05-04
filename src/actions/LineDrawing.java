package actions;

import utils.Dot;

import java.awt.*;
import java.util.LinkedList;

public class LineDrawing implements Action {
    int instrumentSize;
    Dot startDot;
    Dot endDot;
    Color color;


    public LineDrawing(Dot initPos, Color color, int instrumentSize) {
        startDot = initPos;
        this.color = color;
        this.instrumentSize = instrumentSize;
    }

    public void update(Dot pos) {
        endDot = pos;
    }

    @Override
    public void apply(Graphics g) {
        if (startDot == null || endDot == null) return;

        g.setColor(color);
        ((Graphics2D) g).setStroke(new BasicStroke(instrumentSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        g.drawLine(startDot.getX(), startDot.getY(), endDot.getX(), endDot.getY());
    }
}
