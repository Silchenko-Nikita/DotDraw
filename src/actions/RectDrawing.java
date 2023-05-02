package actions;

import utils.Dot;

import java.awt.*;

public class RectDrawing implements Action {
    Dot startDot;
    Dot endDot;
    Color color;

    public RectDrawing(Dot initPos, Color color) {
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

        int width = endDot.getX() - startDot.getX();
        int height = endDot.getY() - startDot.getY();

        g.drawRect(startDot.getX() + (width >= 0 ? 0 : width),
                   startDot.getY() + (height >= 0 ? 0 : height),
                   width >= 0 ? width : -width,
                   height >= 0 ? height : -height);
    }
}
