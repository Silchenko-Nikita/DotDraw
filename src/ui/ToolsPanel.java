package ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.ValAction.INSTRUMENT_SIZE;
public class ToolsPanel extends JToolBar implements ChangeListener, ActionListener {
    JSpinner spinner;
    ValActionHandler actionHandler;

    private JButton fillMenuItem;
    private JButton clearMenuItem;
    private JButton pencilMenuItem;
    private JButton brushMenuItem;
    private JButton lineMenuItem;
    private JButton rectMenuItem;
    private JButton filledRectMenuItem;
    private JButton ovalMenuItem;
    private JButton filledOvalMenuItem;
    private JButton colorMenuItem;
    private JButton undoMenuItem, redoMenuItem;
    MenuActionHandler actionHandlerM;

    public ToolsPanel(ValActionHandler actionHandler, MenuActionHandler actionHandlerM) {
        this.actionHandler = actionHandler;
        this.actionHandlerM = actionHandlerM;

        //spinner setup
        JLabel label = new JLabel("Size:");
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 99, 1);
        spinner = new JSpinner(model);
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        JFormattedTextField textField = editor.getTextField();
        DefaultFormatter formatter = (DefaultFormatter) textField.getFormatter();
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        spinner.getModel().addChangeListener(this);
        spinner.setMaximumSize(spinner.getPreferredSize());

        //tool buttons setup
        undoMenuItem = new JButton("", new ImageIcon("src/ui/icons/undo.png"));
        undoMenuItem.addActionListener(this);
        redoMenuItem = new JButton("", new ImageIcon("src/ui/icons/redo.png"));
        redoMenuItem.addActionListener(this);
        //
        fillMenuItem = new JButton("", new ImageIcon("src/ui/icons/fill.png"));
        fillMenuItem.addActionListener(this);
        pencilMenuItem = new JButton("", new ImageIcon("src/ui/icons/pencil.png"));
        pencilMenuItem.addActionListener(this);
        brushMenuItem = new JButton("", new ImageIcon("src/ui/icons/eraser.png"));
        brushMenuItem.addActionListener(this);
        lineMenuItem = new JButton("", new ImageIcon("src/ui/icons/line.png"));
        lineMenuItem.addActionListener(this);
        rectMenuItem = new JButton("", new ImageIcon("src/ui/icons/rect.png"));
        rectMenuItem.addActionListener(this);
        filledRectMenuItem = new JButton("", new ImageIcon("src/ui/icons/rect_fill.png"));
        filledRectMenuItem.addActionListener(this);
        ovalMenuItem = new JButton("", new ImageIcon("src/ui/icons/oval.png"));
        ovalMenuItem.addActionListener(this);
        filledOvalMenuItem = new JButton("", new ImageIcon("src/ui/icons/oval_fill.png"));
        filledOvalMenuItem.addActionListener(this);
        clearMenuItem = new JButton("", new ImageIcon("src/ui/icons/clear.png"));
        clearMenuItem.addActionListener(this);

        //setup tool bar layout
        colorMenuItem = new JButton("");
        colorMenuItem.setName("colourchange");
        colorMenuItem.setBackground(Color.BLACK);
        colorMenuItem.setOpaque(true);
        colorMenuItem.addActionListener(this);

        add(undoMenuItem);
        add(redoMenuItem);
        addSeparator();
        add(fillMenuItem);
        add(pencilMenuItem);
        add(brushMenuItem);
        add(lineMenuItem);
        add(rectMenuItem);
        add(filledRectMenuItem);
        add(ovalMenuItem);
        add(filledOvalMenuItem);
        addSeparator();
        add(clearMenuItem);
        addSeparator();
        add(label);
        add(spinner);
        addSeparator();
        add(colorMenuItem);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int value = (int) spinner.getValue();
        this.actionHandler.handleValAction(INSTRUMENT_SIZE, value);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == undoMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.UNDO);
        } else if (source == redoMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.REDO);
        } else if (source == clearMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CLEAR_CANVAS);
        } else if (source == fillMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_FILL);
        } else if (source == pencilMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_PENCIL);
        } else if (source == brushMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_BRUSH);
        } else if (source == lineMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_LINE);
        }  else if (source == rectMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_RECT);
        }  else if (source == filledRectMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_FILLED_RECT);
        }  else if (source == ovalMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_OVAL);
        }  else if (source == filledOvalMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_FILLED_OVAL);
        } else if (source == colorMenuItem) {
            actionHandlerM.handleMenuAction(MenuAction.CHOOSE_COLOR);
        }
    }
}
