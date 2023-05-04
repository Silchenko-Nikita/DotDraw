package actions;

import ui.GraphicsProvider;
import utils.Dot;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class RegularDrawing implements Action {
    int instrumentSize;
    List<Dot> dots;
    Color color;

    public RegularDrawing(Dot initPos, Color color, int instrumentSize) {
        dots = new LinkedList<>();
        dots.add(initPos);
        this.color = color;
        this.instrumentSize = instrumentSize;
    }

    public void update(Dot pos) {
        dots.add(pos);
    }

    @Override
    public void apply(Graphics g) {
        if (dots.isEmpty()) return;

        g.setColor(color);
        ((Graphics2D) g).setStroke(new BasicStroke(instrumentSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        var iterator = dots.iterator();
        Dot prevDot = iterator.next();
        while (iterator.hasNext()){
            Dot currentDot = iterator.next();
            g.drawLine(prevDot.getX(), prevDot.getY(), currentDot.getX(), currentDot.getY());
            prevDot = currentDot;
        }
    }
}
