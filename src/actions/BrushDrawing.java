package actions;

import utils.Dot;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class BrushDrawing implements Action {
    int instrumentSize;
    List<Dot> dots;
    boolean showBounds = false;

    public BrushDrawing(Dot initPos, int instrumentSize) {
        dots = new LinkedList<>();
        dots.add(initPos);
        this.instrumentSize = instrumentSize;
    }

    public void update(Dot pos) {
        dots.add(pos);
    }

    @Override
    public void apply(Graphics g) {
        if (dots.isEmpty()) return;

        g.setColor(Color.WHITE);
        ((Graphics2D) g).setStroke(new BasicStroke(instrumentSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        var iterator = dots.iterator();
        Dot prevDot = iterator.next();
        while (iterator.hasNext()){
            Dot currentDot = iterator.next();
            g.setColor(Color.WHITE);
            g.drawLine(prevDot.getX(), prevDot.getY(), currentDot.getX(), currentDot.getY());

            if (showBounds && !iterator.hasNext()) {
                g.setColor(Color.BLACK);
                g.fillOval(currentDot.getX() - instrumentSize / 2, currentDot.getY() - instrumentSize / 2, instrumentSize, instrumentSize);

                g.setColor(Color.WHITE);
                g.fillOval(currentDot.getX()  - instrumentSize / 2 + 1, currentDot.getY() - instrumentSize / 2 + 1, instrumentSize - 2, instrumentSize - 2);
            }

            prevDot = currentDot;
        }
    }

    public void setShowBounds(boolean showBounds) {
        this.showBounds = showBounds;
    }
}
