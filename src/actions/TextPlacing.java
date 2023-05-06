package actions;

import utils.Dot;

import java.awt.*;

public class TextPlacing implements Action {
    int instrumentSize;
    int fontSize;
    Dot pos;
    String text;
    Color color;

    boolean isActive = false;

    public TextPlacing(Dot pos, Color color, int instrumentSize, int fontSize) {
        this.pos = pos;
        this.color = color;
        this.instrumentSize = instrumentSize;
        this.fontSize = fontSize;

        text = "";
    }

    @Override
    public void apply(Graphics g) {
        g.setColor(color);

        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont((float) (fontSize + instrumentSize));
        g.setFont(newFont);

        ((Graphics2D) g).setStroke(new BasicStroke(instrumentSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawString(text, pos.getX(), pos.getY() + newFont.getSize());

        if (isActive) {
            g.setColor(Color.BLACK);
            ((Graphics2D) g).setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                    0, new float[]{2}, 0));

            FontMetrics fontMetrics = g.getFontMetrics(newFont);
            int stringWidth = fontMetrics.stringWidth(text);
            int stringHeight = fontMetrics.getHeight();
            g.drawRect(pos.getX() - 2, pos.getY(),
                    stringWidth + 4, stringHeight);
        }
    }


    public void setBeingActiveState(boolean val) {
        isActive = val;
    }

    public void update(char ch) {
        this.text += ch;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setInstrumentSize(int val) {
        instrumentSize = val;
    }
}
