package states;

import actions.Action;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

public interface State {
    Color getCurrentColor();
    void setCurrentColor(Color color);

    MouseAdapter getMouseListener();
    MouseMotionAdapter getMouseMotionListener();
}