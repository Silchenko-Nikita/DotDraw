package actions;

import utils.Dot;

import java.awt.*;

public class OvalDrawing implements Action {
    int instrumentSize;
    Dot startDot;
    Dot endDot;
    Color color;

    public OvalDrawing(Dot initPos, Color color, int instrumentSize) {
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
        ((Graphics2D) g).setStroke(new BasicStroke(instrumentSize, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));

        int width = endDot.getX() - startDot.getX();
        int height = endDot.getY() - startDot.getY();

        g.drawOval(startDot.getX() + (width >= 0 ? 0 : width),
                   startDot.getY() + (height >= 0 ? 0 : height),
                   width >= 0 ? width : -width,
                   height >= 0 ? height : -height);
    }
}
